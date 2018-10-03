package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.Map;

public class Group extends PerunEntity {

	private String name;
	private String description;
	private Long voId;
	private Long parentGroupId;

	public Group(Long id, String name, String description, Long voId, Long parentGroupId, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
		this.name = name;
		this.description = description;
		this.voId = voId;
		this.parentGroupId = parentGroupId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getVoId() {
		return voId;
	}

	public void setVoId(Long voId) {
		this.voId = voId;
	}

	public Long getParentGroupId() {
		return parentGroupId;
	}

	public void setParentGroupId(Long parentGroupId) {
		this.parentGroupId = parentGroupId;
	}

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("name", name);
		json.put("description", description);
		json.put("vo_id", voId);
		json.put("parent_group_id", parentGroupId);
		json.put("attributes", this.attributesToJson());

		return json;
	}
}
