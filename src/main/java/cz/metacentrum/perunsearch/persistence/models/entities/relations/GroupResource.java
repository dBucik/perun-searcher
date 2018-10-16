package cz.metacentrum.perunsearch.persistence.models.entities.relations;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Group;

import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(value = "id")
public class GroupResource extends PerunEntity {

	private Long groupId;
	private Long resourceId;

	public GroupResource(Long groupId, Long resourceId, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(null, attributes, foreignId);
		this.groupId = groupId;
		this.resourceId = resourceId;
	}

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
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
