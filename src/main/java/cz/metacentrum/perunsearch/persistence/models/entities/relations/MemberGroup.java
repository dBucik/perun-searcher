package cz.metacentrum.perunsearch.persistence.models.entities.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunRichEntity;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(value = "id")
public class MemberGroup extends PerunRichEntity {

	private Integer memberId;
	private Integer groupId;

	public MemberGroup(Integer memberId, Integer groupId, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(null, attributes, foreignId);
		this.memberId = memberId;
		this.groupId = groupId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MemberGroup)) {
			return false;
		}

		MemberGroup them = (MemberGroup) o;

		return Objects.equals(this.memberId, them.memberId)
				&& Objects.equals(this.groupId, them.groupId);
	}
}
