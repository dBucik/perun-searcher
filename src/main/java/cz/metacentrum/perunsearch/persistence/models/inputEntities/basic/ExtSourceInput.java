package cz.metacentrum.perunsearch.persistence.models.inputEntities.basic;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.Query;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.BasicInputEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.EXT_SOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER_EXT_SOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.VO;

public class ExtSourceInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = EXT_SOURCE;
	private static final String ENTITY_ID_FIELD = "ext_sources_id";
	private static final String ENTITY_TABLE = "ext_sources";
	private static final String ENTITY_ATTRS_TABLE = "ext_source_attributes";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(VO, GROUP, USER_EXT_SOURCE);

	public ExtSourceInput(boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
						  List<String> attrNames, List<InputEntity> innerInputs) throws IllegalRelationException {
		super(TYPE, isTopLevel, core, attributes, attrNames, innerInputs);
	}

	@Override
	public boolean isAllowedInnerInput(PerunEntityType entityType) {
		if (entityType == null) {
			return false;
		}

		return ALLOWED_INNER_INPUTS.contains(entityType);
	}

	@Override
	public String getEntityTable() {
		return ENTITY_TABLE;
	}

	@Override
	public String getEntityIdInAttrValuesTable() {
		return ENTITY_ID_FIELD;
	}

	@Override
	public String getAttrValuesTable() {
		return ENTITY_ATTRS_TABLE;
	}

	@Override
	public String getAttrNamesTable() {
		return null;
	}

	@Override
	public String buildSelectFrom(PerunEntityType sourceType, boolean isSimple) {
		if (sourceType == null) {
			return getDefaultQuery(isSimple);
		}

		switch (sourceType) {
			case VO:
				return getQueryForVo(isSimple);
			case GROUP:
				return getQueryForGroup(isSimple);
			case USER_EXT_SOURCE:
				return getQueryForUserExtSource(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	@Override
	protected String buildAttributesQuery(Query query, List<String> attrNames) {
		String entityId = this.getEntityIdInAttrValuesTable();
		String attrValuesTable = this.getAttrValuesTable();

		StringBuilder queryString = new StringBuilder();
		String where = buildAttributesWhere(query, attrNames);

		queryString.append("SELECT ").append(entityId).append(" AS entity_id, json_agg(json_build_object(")
				.append("'name', attr_name, 'value', attr_value,)) AS data")
				.append(" FROM ").append(attrValuesTable).append(" av");
		if (!Objects.equals(where, NO_VALUE)) {
			queryString.append(where).append(' ');
		}
		queryString.append("GROUP BY ").append(entityId);

		return queryString.toString();
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForVo(boolean isSimple) {
		String select = "ves.vo_id AS foreign_id";
		String join = "JOIN vo_ext_sources ves ON ves.ext_source_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForGroup(boolean isSimple) {
		String select = "ges.group_id AS foreign_id";
		String join = "JOIN group_ext_sources ges ON ges.ext_source_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForUserExtSource(boolean isSimple) {
		String select = "ues.id AS foreign_id";
		String join = "JOIN user_ext_sources ues ON ues.ext_sources_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

}
