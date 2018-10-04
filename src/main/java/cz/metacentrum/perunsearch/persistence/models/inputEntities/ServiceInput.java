package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.SERVICE;

public class ServiceInput extends InputEntity {

	private static final PerunEntityType TYPE = SERVICE;
	private static final String ENTITY_ID_FIELD = "id";
	private static final String ENTITY_TABLE = "services";
	private static final String ENTITY_ATTRS_TABLE = "service_required_attributes";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Collections.singletonList(RESOURCE);

	public ServiceInput(boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
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
			case RESOURCE:
				return getQueryForResource(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return InputUtils.getQuery(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "rs.resource_id AS foreign_id";
		String join = "JOIN resource_services rs ON rs.service_id = " + ENTITY_TABLE + '.' + ENTITY_ID_FIELD;

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}
}
