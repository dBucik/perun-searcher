package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;

import java.util.Arrays;
import java.util.List;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.FACILITY;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.SERVICE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.VO;

public class ResourceInput extends InputEntity {

	private static final PerunEntityType TYPE = RESOURCE;
	private static final String ENTITY_ID_FIELD = "id";
	private static final String ENTITY_TABLE = "resources";
	private static final String ENTITY_ATTRS_TABLE = "resource_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(MEMBER, VO, SERVICE, FACILITY, GROUP);

	public ResourceInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
	public String getEntityId() {
		return ENTITY_ID_FIELD;
	}

	@Override
	public String getEntityAttrsTable() {
		return ENTITY_ATTRS_TABLE;
	}

	@Override
	public String getAttrsTable() {
		return ATTR_NAMES_TABLE;
	}

	@Override
	public String getSelectFrom(PerunEntityType sourceType, boolean isSimple) {
		if (sourceType == null) {
			return getDefaultQuery(isSimple);
		}

		switch (sourceType) {
			case MEMBER:
				return getQueryForMember(isSimple);
			case VO:
				return getQueryForVo(isSimple);
			case SERVICE:
				return getQueryForService(isSimple);
			case FACILITY:
				return getQueryForFacility(isSimple);
			case GROUP:
				return getQueryForGroup(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return InputUtils.getQuery(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForMember(boolean isSimple) {
		String select = "mrav.member_id AS foreign_id";
		String join = "JOIN member_resource_attr_values mrav ON mrav.resource_id = " + ENTITY_TABLE + '.' + ENTITY_ID_FIELD;

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForVo(boolean isSimple) {
		String select = "ent.vo_id AS foreign_id";

		return InputUtils.getQuery(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForService(boolean isSimple) {
		String select = "rs.service_id AS foreign_id";
		String join = "JOIN resource_services rs ON rs.resource_id = " + ENTITY_TABLE + '.' + ENTITY_ID_FIELD;

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForFacility(boolean isSimple) {
		String select = "ent.facility_id AS foreign_id";

		return InputUtils.getQuery(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForGroup(boolean isSimple) {
		String select = "gr.group_id AS foreign_id";
		String join = "JOIN group_resources gr ON gr.resource_id = " + ENTITY_TABLE + '.' + ENTITY_ID_FIELD;

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}
}
