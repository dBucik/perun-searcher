package cz.metacentrum.perunsearch.persistence.models.inputEntities.basic;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.BasicInputEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.EXT_SOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.VO;

public class VoInput extends BasicInputEntity {

	private static final PerunEntityType TYPE = VO;
	private static final String ENTITY_ID_FIELD = "vo_id";
	private static final String ENTITY_TABLE = "vos";
	private static final String ENTITY_ATTRS_TABLE = "vo_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(RESOURCE, EXT_SOURCE, GROUP, MEMBER);

	public VoInput(boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
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
		return ATTR_NAMES_TABLE;
	}

	@Override
	public String buildSelectFrom(PerunEntityType sourceType, boolean isSimple) {
		if (sourceType == null) {
			return getDefaultQuery(isSimple);
		}

		switch (sourceType) {
			case RESOURCE:
				return getQueryForResource(isSimple);
			case EXT_SOURCE:
				return getQueryForExtSource(isSimple);
			case MEMBER:
				return getQueryForMember(isSimple);
			case GROUP:
				return getQueryForGroup(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return this.getSelectFrom(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "r.id AS foreign_id";
		String join = "JOIN resources r ON r.vo_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForExtSource(boolean isSimple) {
		String select = "ves.ext_sources_id AS foreign_id";
		String join = "JOIN vo_ext_sources ves ON ves.vo_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForMember(boolean isSimple) {
		String select = "m.id AS foreign_id";
		String join = "JOIN members m ON m.vo_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForGroup(boolean isSimple) {
		String select = "g.id AS foreign_id";
		String join = "JOIN groups g ON g.vo_id = ent.id";

		return this.getSelectFrom(isSimple, select, join, ENTITY_TABLE);
	}
}
