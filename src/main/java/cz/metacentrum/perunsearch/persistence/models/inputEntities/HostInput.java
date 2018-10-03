package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;

import java.util.Collections;
import java.util.List;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.FACILITY;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.HOST;

public class HostInput extends InputEntity {

	private static final PerunEntityType TYPE = HOST;
	private static final String ENTITY_ID_FIELD = "id";
	private static final String ENTITY_TABLE = "hosts";
	private static final String ENTITY_ATTRS_TABLE = "host_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Collections.singletonList(FACILITY);

	public HostInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return InputUtils.getQuery(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForFacility(boolean isSimple) {
		String select = "ent.facility_id AS foreign_id";

		return InputUtils.getQuery(isSimple, select, null, ENTITY_TABLE);
	}
}
