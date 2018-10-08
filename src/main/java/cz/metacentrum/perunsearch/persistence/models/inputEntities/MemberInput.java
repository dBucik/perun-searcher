package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.VO;

public class MemberInput extends InputEntity {

	public static final PerunEntityType TYPE = MEMBER;
	private static final String ENTITY_ID_FIELD = "member_id";
	private static final String ENTITY_TABLE = "members";
	private static final String ENTITY_ATTRS_TABLE = "member_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(VO, USER, RESOURCE, GROUP);

	public MemberInput(boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
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
			case VO:
				return getQueryForVo(isSimple);
			case USER:
				return getQueryForUser(isSimple);
			case RESOURCE:
				return getQueryForResource(isSimple);
			case GROUP:
				return getQueryForGroup(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		return InputUtils.getQuery(isSimple, null, null, ENTITY_TABLE);
	}

	private String getQueryForVo(boolean isSimple) {
		String select = "ent.vo_id AS foreign_id";

		return InputUtils.getQuery(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForUser(boolean isSimple) {
		String select = "ent.user_id AS foreign_id";

		return InputUtils.getQuery(isSimple, select, null, ENTITY_TABLE);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "mrav.resource_id AS foreign_id";
		String join = "JOIN member_resource_attr_values mrav ON mrav.member_id = ent.id";

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}

	private String getQueryForGroup(boolean isSimple) {
		String select = "gm.group_id AS foreign_id";
		String join = "JOIN groups_members gm ON gm.member_id = ent.id";

		return InputUtils.getQuery(isSimple, select, join, ENTITY_TABLE);
	}
}
