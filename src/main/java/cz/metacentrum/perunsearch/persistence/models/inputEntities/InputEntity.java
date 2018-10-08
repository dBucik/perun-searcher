package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.Query;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public abstract class InputEntity {

	private static final String NO_VALUE = null;
	private static final String EXACT_MATCH = " = ";
	private static final String LIKE_MATCH = " LIKE ";
	private static final String NULL_MATCH = " IS NULL ";

	private PerunEntityType entityType;
	private boolean isTopLevel;
	private Map<String, Object> core = new HashMap<>();
	private List<InputAttribute> attributes = new ArrayList<>();
	private List<String> attrNames = new ArrayList<>();
	private List<InputEntity> innerInputs = new ArrayList<>();

	public InputEntity(PerunEntityType entityType, boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
					   List<String> attrNames, List<InputEntity> innerInputs) throws IllegalRelationException {
		this.entityType = entityType;
		this.isTopLevel = isTopLevel;
		this.core.putAll(core);
		this.attributes.addAll(attributes);
		this.attrNames.addAll(attrNames);

		for (InputEntity e: innerInputs) {
			if (!isAllowedInnerInput(e.getEntityType())) {
				throw new IllegalRelationException(e.getEntityType() +
						" does not have a relationship with " + this.entityType);
			}

			this.innerInputs.add(e);
		}
	}

	public abstract boolean isAllowedInnerInput(PerunEntityType entityType);

	public abstract String getEntityTable();

	public abstract String getEntityIdForAttrs();

	public abstract String getEntityAttrsTable();

	public abstract String getAttrsTable();

	public boolean isTopLevel() {
		return isTopLevel;
	}

	public abstract String getSelectFrom(PerunEntityType sourceType, boolean isSimple);

	public Map<String, Object> getCore() {
		return core;
	}

	public List<InputAttribute> getAttributes() {
		return attributes;
	}

	public List<String> getAttrNames() {
		return attrNames;
	}

	public List<InputEntity> getInnerInputs() {
		return innerInputs;
	}

	public PerunEntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(PerunEntityType entityType) {
		this.entityType = entityType;
	}

	public Query toQuery(PerunEntityType sourceType) throws IncorrectCoreAttributeTypeException {
		boolean isSimple = this.isSimpleQuery();
		String entityId = this.getEntityIdForAttrs();
		String attrValuesTable = this.getEntityAttrsTable();
		String attrsTable = this.getAttrsTable();

		Query query = new Query();
		query.setEntityType(this.entityType);
		StringBuilder queryString = new StringBuilder();
		String selectFrom = this.getSelectFrom(sourceType, isSimple);
		String where = outerWhere(query, this.getCore());
		List<Query> innerQueries = new ArrayList<>();

		queryString.append(selectFrom);
		if (! isSimple) {
			List<String> names = mergeNames(attrNames, attributes);
			String attributesQuery = buildAttributesQuery(query, names, entityId, attrValuesTable, attrsTable);
			queryString.append(" LEFT JOIN (").append(attributesQuery)
					.append(") AS attributes ON ent.id = attributes.").append(getEntityIdForAttrs());
		}

		if (! Objects.equals(where, NO_VALUE)) {
			query.setHasWhere(true);
			queryString.append(' ').append(where);
		}

		query.setQueryString(queryString.toString());
		for (InputEntity e : innerInputs) {
			innerQueries.add(e.toQuery(this.entityType));
		}
		query.setInnerQueries(innerQueries);
		query.setInputAttributes(attributes);

		return query;
	}

	private boolean isSimpleQuery() {
		return (this.attrNames.isEmpty() && this.attributes.isEmpty());
	}

	private List<String> mergeNames(List<String> attrNames, List<InputAttribute> attributes) {
		//fetch all attributes
		if (attrNames.size() == 1) {
			String param = attrNames.get(0);
			if ("ALL".equals(param.toUpperCase())) {
				return Collections.emptyList();
			}
		}

		Set<String> names = new HashSet<>(attrNames);
		for (InputAttribute a : attributes) {
			names.add(a.getName());
		}

		return new ArrayList<>(names);
	}

	private String outerWhere(Query query, Map<String, Object> core) throws IncorrectCoreAttributeTypeException {
		if (core == null || core.isEmpty()) {
			return NO_VALUE;
		}

		StringJoiner attrs = new StringJoiner(" AND ");
		for (Map.Entry<String, Object> a: core.entrySet()) {
			String operator = resolveMatchOperator(a.getValue());
			String part = "ent." + a.getKey() + operator;
			if (! operator.equals(NULL_MATCH)) {
				if (operator.equals(LIKE_MATCH)) {
					part += query.nextParam('%' + (String) a.getValue() + '%');
				} else {
					part += query.nextParam( a.getValue());
				}
			}
			attrs.add(part);
		}

		return "WHERE " + attrs.toString();
	}

	private String resolveMatchOperator(Object o) throws IncorrectCoreAttributeTypeException {
		if (o == null) {
			return NULL_MATCH;
		} else if ((o instanceof String) || (o instanceof Long) || (o instanceof Boolean) || (o instanceof Timestamp)) {
			return EXACT_MATCH;
		} else {
			throw new IncorrectCoreAttributeTypeException("Unsupported core attribute type found for input");
		}
	}

	private String buildAttributesQuery(Query query, List<String> attrNames, String entityId, String attrValuesTable,
										String attrNamesTable) {
		StringBuilder queryString = new StringBuilder();
		String where = buildAttributesWhere(query, attrNames);
		queryString.append("SELECT ").append(entityId).append(", json_agg(json_build_object(")
				.append("'name', attr_name, 'value', COALESCE(attr_value, attr_value_text), 'type', type))")
				.append(" AS data ");
		queryString.append("FROM ").append(attrValuesTable).append(" av JOIN ").append(attrNamesTable)
				.append(" an ON av.attr_id = an.id ");
		if (!Objects.equals(where, NO_VALUE)) {
			queryString.append(where).append(' ');
		}
		queryString.append("GROUP BY ").append(entityId);

		return queryString.toString();
	}

	private String buildAttributesWhere(Query query, List<String> attrNames) {
		if (attrNames == null || attrNames.isEmpty()) {
			return NO_VALUE;
		}

		StringJoiner where = new StringJoiner(" OR ");
		for (String name: attrNames) {
			where.add("(attr_name = " + query.nextParam(name) + ')');
		}

		return "WHERE " + where.toString();
	}
}
