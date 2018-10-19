package cz.metacentrum.perunsearch.persistence.models.entities.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunRichEntity;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(value = "id")
public class GroupResource extends PerunRichEntity {

	private Integer groupId;
	private Integer resourceId;

	public GroupResource(Integer groupId, Integer resourceId, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(null, attributes, foreignId);
		this.groupId = groupId;
		this.resourceId = resourceId;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public Integer getResourceId() {
		return resourceId;
	}

	public void setResourceId(Integer resourceId) {
		this.resourceId = resourceId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof GroupResource)) {
			return false;
		}

		GroupResource them = (GroupResource) o;

		return Objects.equals(this.groupId, them.groupId)
				&& Objects.equals(this.resourceId, them.resourceId);
	}
}
