package cz.metacentrum.perunsearch.persistence.models.entities.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunRichEntity;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(value = "id")
public class MemberGroup extends PerunRichEntity {

	private Long memberId;
	private Long groupId;

	public MemberGroup(Long memberId, Long groupId, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(null, attributes, foreignId);
		this.memberId = memberId;
		this.groupId = groupId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
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
