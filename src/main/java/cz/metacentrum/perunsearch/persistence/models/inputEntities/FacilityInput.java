package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.FACILITY;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.HOST;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER;

public class FacilityInput extends InputEntity {

	private static final PerunEntityType TYPE = FACILITY;
	private static final String ENTITY_ID_FIELD = "facility_id";
	private static final String ENTITY_TABLE = "facilities";
	private static final String ENTITY_ATTRS_TABLE = "facility_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(USER, RESOURCE, HOST);

	public FacilityInput(boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
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
	public String getEntityIdForAttrs() {
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
			case USER:
				return getQueryForUser(isSimple);
			case RESOURCE:
				return getQueryForResource(isSimple);
			case HOST:
				return getQueryForHost(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return InputUtils.getQuery(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForUser(boolean isSimple) {
		String select = "ufav.user_id AS foreign_id";
		String join = "JOIN user_facility_attr_values ufav ON ufav.facility_id = ent.id";

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "r.id AS foreign_id";
		String join = "JOIN resources r ON r.facility_id = ent.id";

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForHost(boolean isSimple) {
		String select = "h.id AS foreign_id";
		String join = "JOIN hosts h ON h.facility_id = ent.id";

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}
}
