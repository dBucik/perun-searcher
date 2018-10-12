package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.Query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.StringJoiner;

public abstract class BasicInputEntity extends InputEntity {

	public BasicInputEntity(PerunEntityType entityType, boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes, List<String> attrNames, List<InputEntity> innerInputs) throws IllegalRelationException {
		super(entityType, isTopLevel, core, attributes, attrNames, innerInputs);
	}

	public abstract String getEntityTable();

	public abstract String getEntityIdInAttrValuesTable();

	public abstract String getAttrValuesTable();

	@Override
	public Query initQuery() {
		Query query = new Query();
		query.setEntityType(this.getEntityType());

		return query;
	}

	@Override
	public Query toQuery(PerunEntityType sourceType) throws IncorrectCoreAttributeTypeException {
		boolean isSimple = this.isSimpleQuery();
		Query query = this.initQuery();

		String selectFrom = this.buildSelectFrom(sourceType, isSimple);
		String where = outerWhere(query, this.getCore());

		StringBuilder queryString = new StringBuilder();
		List<Query> innerQueries = new ArrayList<>();

		queryString.append(selectFrom);
		if (! isSimple) {
			List<String> names = mergeNames(this.getAttrNames(), this.getAttributes());
			String attributesQuery = buildAttributesQuery(query, names);
			queryString.append(" LEFT JOIN (").append(attributesQuery)
					.append(") AS attributes ON ent.id = attributes.entity_id");
		}

		if (! Objects.equals(where, NO_VALUE)) {
			queryString.append(' ').append(where);
			query.setHasWhere(true);
		}

		query.setQueryString(queryString.toString());
		for (InputEntity e : this.getInnerInputs()) {
			innerQueries.add(e.toQuery(this.getEntityType()));
		}
		query.setInnerQueries(innerQueries);
		query.setInputAttributes(this.getAttributes());

		return query;
	}

	public String getSelectFrom(boolean isSimple, String select, String join, String entityTable) {
		StringBuilder queryString = new StringBuilder();
		queryString.append("SELECT to_json(ent) AS entity");
		if (select != null) {
			queryString.append(", ").append(select);
		}

		if (! isSimple) {
			queryString.append(", attributes.data AS attributes");
		}
		queryString.append(" FROM ").append(entityTable).append(" ent");
		if (join != null) {
			queryString.append(' ').append(join);
		}

		return queryString.toString();
	}

	protected String buildAttributesQuery(Query query, List<String> attrNames) {
		String entityId = this.getEntityIdInAttrValuesTable();
		String attrValuesTable = this.getAttrValuesTable();
		String attrNamesTable = this.getAttrNamesTable();

		StringBuilder queryString = new StringBuilder();
		String where = buildAttributesWhere(query, attrNames);

		queryString.append("SELECT ").append(entityId).append(" AS entity_id, json_agg(json_build_object(")
				.append("'name', attr_name, 'value', COALESCE(attr_value, attr_value_text), 'type', type)) AS data")
				.append(" FROM ").append(attrValuesTable).append(" av JOIN ").append(attrNamesTable).append(" an")
				.append(" ON av.attr_id = an.id ");
		if (!Objects.equals(where, NO_VALUE)) {
			queryString.append(where).append(' ');
		}
		queryString.append("GROUP BY ").append(entityId);

		return queryString.toString();
	}

	protected String buildAttributesWhere(Query query, List<String> attrNames) {
		if (attrNames == null || attrNames.isEmpty()) {
			return NO_VALUE;
		}

		StringJoiner where = new StringJoiner(" OR ");
		for (String name: attrNames) {
			where.add("(attr_name = " + query.nextParam(name) + ')');
		}

		return "WHERE " + where.toString();
	}

	private String outerWhere(Query query, Map<String, Object> core) throws IncorrectCoreAttributeTypeException {
		if (core == null || core.isEmpty()) {
			return NO_VALUE;
		}

		StringJoiner where = new StringJoiner(" AND ");
		for (Map.Entry<String, Object> a: core.entrySet()) {
			String operator = resolveMatchOperator(a.getValue());
			String part = "ent." + a.getKey() + operator;
			if (! operator.equals(NULL_MATCH)) {
				//TODO: add also LIKE MATCH
				part += query.nextParam( a.getValue());
			}
			where.add(part);
		}

		return "WHERE " + where.toString();
	}
}
