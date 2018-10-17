package cz.metacentrum.perunsearch.persistence.models.inputEntities.relations;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.RelationInputEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER_RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.RESOURCE;

public class MemberResourceInput extends RelationInputEntity {

	private static final PerunEntityType TYPE = MEMBER_RESOURCE;
	private static final String RELATION_TABLE = "member_resource_attr_values";
	private static final String ATTR_NAMES_TABLE = "attr_names";
	private static final String PRIMARY_KEY = "member_id";
	private static final String SECONDARY_KEY = "resource_id";

	private static final List<PerunEntityType> ALLOWED_INNER_INPUTS = Arrays.asList(MEMBER, RESOURCE);

	public MemberResourceInput(boolean isTopLevel, List<InputAttribute> core, List<InputAttribute> attributes,
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
	public String getPrimaryKey() {
		return PRIMARY_KEY;
	}

	@Override
	public String getSecondaryKey() {
		return SECONDARY_KEY;
	}

	@Override
	protected String getRelationTable() {
		return RELATION_TABLE;
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
			case MEMBER:
				return getQueryForMember(isSimple);
			case RESOURCE:
				return getQueryForResource(isSimple);
			default: return null; //TODO: throw exception
		}
	}

	private String getDefaultQuery(boolean isSimple) {
		String select = "rel.member_id, rel.resource_id";

		return this.getSelectFrom(isSimple, select, null);
	}

	private String getQueryForMember(boolean isSimple) {
		String select = "rel.member_id, rel.resource_id, rel.member_id AS foreign_id";
		String join = "JOIN members m ON m.id = rel.member_id";

		return this.getSelectFrom(isSimple, select, join);
	}

	private String getQueryForResource(boolean isSimple) {
		String select = "rel.member_id, rel.resource_id, rel.resource_id AS foreign_id";
		String join = "JOIN resources r ON r.id = rel.resource_id";

		return this.getSelectFrom(isSimple, select, join);
	}
}
