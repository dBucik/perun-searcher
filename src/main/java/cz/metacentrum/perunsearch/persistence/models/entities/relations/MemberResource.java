package cz.metacentrum.perunsearch.persistence.models.entities.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunRichEntity;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(value = "id")
public class MemberResource extends PerunRichEntity {

	private Long memberId;
	private Long resourceId;

	public MemberResource(Long memberId, Long resourceId, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(null, attributes, foreignId);
		this.memberId = memberId;
		this.resourceId = resourceId;
	}

	public Long getMemberId() {
		return memberId;
	}

	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof MemberResource)) {
			return false;
		}

		MemberResource them = (MemberResource) o;

		return Objects.equals(this.memberId, them.memberId)
				&& Objects.equals(this.resourceId, them.resourceId);
	}
}
