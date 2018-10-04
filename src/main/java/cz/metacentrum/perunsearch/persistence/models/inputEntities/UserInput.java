package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.FACILITY;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER_EXT_SOURCE;

public class UserInput extends InputEntity {

	private static final PerunEntityType TYPE = USER;
	private static final String ENTITY_ID_FIELD = "id";
	private static final String ENTITY_TABLE = "users";
	private static final String ENTITY_ATTRS_TABLE = "user_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(FACILITY, MEMBER, USER_EXT_SOURCE);

	public UserInput(boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
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
			case FACILITY:
				return getQueryForFacility(isSimple);
			case USER_EXT_SOURCE:
				return getQueryForUserExtSource(isSimple);
			case MEMBER:
				return getQueryForMember(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return InputUtils.getQuery(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForFacility(boolean isSimple) {
		String select = "ufav.facility_id AS foreign_id";
		String join = "JOIN user_facility_attr_values ufav ON ufav.user_id = " + ENTITY_TABLE + '.' + ENTITY_ID_FIELD;

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForUserExtSource(boolean isSimple) {
		String select = "ues.id AS foreign_id";
		String join = "JOIN user_ext_sources ues ON ues.user_id = " + ENTITY_TABLE + '.' + ENTITY_ID_FIELD;

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForMember(boolean isSimple) {
		String select = "m.id AS foreign_id";
		String join = "JOIN members m ON m.user_id = " + ENTITY_TABLE + '.' + ENTITY_ID_FIELD;

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}
}
