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

public abstract class RelationInputEntity extends InputEntity {

	public RelationInputEntity(PerunEntityType entityType, boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes, List<String> attrNames, List<InputEntity> innerInputs) throws IllegalRelationException {
		super(entityType, isTopLevel, core, attributes, attrNames, innerInputs);
	}

	public abstract String buildSelectFrom(PerunEntityType sourceType, boolean isSimple);

	public abstract String getPrimaryKey();

	public abstract String getSecondaryKey();

	protected abstract String getRelationTable();

	@Override
	public Query initQuery() {
		Query query = new Query();
		query.setEntityType(this.getEntityType());
		query.setPrimaryKey(this.getPrimaryKey());
		query.setSecondaryKey(this.getSecondaryKey());

		return query;
	}

	public String getSelectFrom(boolean isSimple, String select, String join) {
		String relationTable = this.getRelationTable();
		String attrNamesTable = this.getAttrNamesTable();
		StringBuilder queryString = new StringBuilder();

		queryString.append("SELECT ").append(select);
		if (!isSimple) {
			queryString.append(", json_agg(json_build_object(")
					.append("'name', attr_name, 'value', COALESCE(attr_value, attr_value_text), 'type', type)) AS attributes");
		}
		queryString.append(" FROM ").append(relationTable).append(" rel");
		if (!isSimple) {
			queryString.append(" LEFT JOIN ").append(attrNamesTable).append(" an")
					.append(" ON rel.attr_id = an.id");
		}
		if (join != null) {
			queryString.append(' ').append(join);
		}

		return queryString.toString();
	}

	public Query toQuery(PerunEntityType sourceType) throws IncorrectCoreAttributeTypeException {
		boolean isSimple = PerunEntityType.isSimpleEntity(this.getEntityType()) && this.isSimpleQuery();
		Query query = this.initQuery();

		String selectFrom = this.buildSelectFrom(sourceType, isSimple);
		List<String> names = mergeNames(this.getAttrNames(), this.getAttributes());
		String where = buildWhere(query, this.getCore(), names);

		StringBuilder queryString = new StringBuilder();
		List<Query> innerQueries = new ArrayList<>();

		queryString.append(selectFrom);
		if (! Objects.equals(where, NO_VALUE)) {
			query.setHasWhere(true);
			queryString.append(' ').append(where);
		}

		query.setQueryString(queryString.toString());
		for (InputEntity e : this.getInnerInputs()) {
			innerQueries.add(e.toQuery(this.getEntityType()));
		}
		query.setInnerQueries(innerQueries);
		query.setInputAttributes(this.getAttributes());

		return query;
	}

	private String buildWhere(Query query, List<InputAttribute> core, List<String> attrNames) throws IncorrectCoreAttributeTypeException {
		if ((core == null || core.isEmpty()) && (attrNames == null || attrNames.isEmpty())) {
			return NO_VALUE;
		}

		StringJoiner where = new StringJoiner(" AND ");

		if (core != null) {
			for (InputAttribute attr: core) {
				String operator = resolveMatchOperator(attr.isLikeMatch(), attr.getValue());
				String part = "";

				switch (operator) {
					case NULL_MATCH:
						part = "rel." + attr.getName() + operator;
						break;
					case LIKE_MATCH:
						part = "rel." + attr.getName() + ":VARCHAR" + operator + query.nextParam('%' + attr.stringValue() + '%');
						break;
					case EXACT_MATCH:
						part = "rel." + attr.getName() + operator + query.nextParam(attr.getValue());
						break;
				}

				where.add(part);
			}
		}

		if (attrNames != null) {
			StringJoiner attributes = new StringJoiner(" OR ");
			for (String name: attrNames) {
				attributes.add("(attr_name = " + query.nextParam(name) + ')');
			}

			if (! attrNames.isEmpty()) {
				where.add(attributes.toString());
			}

		}

		return "WHERE " + where.toString();
	}
}
