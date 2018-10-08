package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER_RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.RESOURCE;

public class MemberResourceInput extends InputEntity {

	private static final PerunEntityType TYPE = MEMBER_RESOURCE;
	private static final String ENTITY_ATTRS_TABLE = "member_resource_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(MEMBER, RESOURCE);

	public MemberResourceInput(boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
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
			case MEMBER:
				return getQueryForMember(isSimple);
			case RESOURCE:
				return getQueryForResource(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		String select = "ent.group_id, ent.resource_id";

		return InputUtils.getQueryForRelation(isSimple, select, null, ATTR_NAMES_TABLE);
	}

	private String getQueryForMember(boolean isSimple) {
		String select = "ent.group_id, ent.member_id, ent.member_id AS foreign_id";
		String join = "JOIN members m ON m.id = ent.member_id";

		return InputUtils.getQueryForRelation(isSimple, select, join, ATTR_NAMES_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "ent.resource_id, ent.memberId_id, ent.resource_id AS foreign_id";
		String join = "JOIN resources r ON r.id = ent.resource_id";

		return InputUtils.getQueryForRelation(isSimple, select, join, ATTR_NAMES_TABLE);
	}
}
