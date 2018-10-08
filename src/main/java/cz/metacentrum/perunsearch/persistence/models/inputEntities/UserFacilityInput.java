package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.FACILITY;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER_FACILITY;

public class UserFacilityInput extends InputEntity {

	private static final PerunEntityType TYPE = USER_FACILITY;
	private static final String ENTITY_ATTRS_TABLE = "user_facility_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(USER, FACILITY);

	public UserFacilityInput(boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
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
		return "";
	}

	@Override
	public String getEntityIdForAttrs() {
		return "";
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
			case USER:
				return getQueryForUser(isSimple);
			case FACILITY:
				return getQueryForFacility(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		String select = "ent.user_id, ent.facility_id";

		return InputUtils.getQueryForRelation(isSimple, select, null, ATTR_NAMES_TABLE);
	}

	private String getQueryForUser(boolean isSimple) {
		String select = "ent.user_id, ent.facility_id, ent.user_id AS foreign_id";
		String join = "JOIN users u ON u.id = ent.user_id";

		return InputUtils.getQueryForRelation(isSimple, select, join, ATTR_NAMES_TABLE);
	}

	private String getQueryForFacility(boolean isSimple) {
		String select = "ent.user_id, ent.facility_id, ent.facility_id AS foreign_id";
		String join = "JOIN facilities f ON f.id = ent.facility_id";

		return InputUtils.getQueryForRelation(isSimple, select, join, ATTR_NAMES_TABLE);
	}
}
