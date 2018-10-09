package cz.metacentrum.perunsearch.persistence.enums;

public enum PerunEntityType {

	EXT_SOURCE,
	FACILITY,
	GROUP,
	HOST,
	MEMBER,
	RESOURCE,
	SERVICE,
	USER_EXT_SOURCE,
	USER,
	VO,
	GROUP_RESOURCE,
	MEMBER_GROUP,
	MEMBER_RESOURCE,
	USER_FACILITY;

	public static boolean isRelation(PerunEntityType entityType) {
		return entityType == GROUP_RESOURCE ||
				entityType == MEMBER_GROUP ||
				entityType == MEMBER_RESOURCE ||
				entityType == USER_FACILITY;
	}
}
