package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.Map;

public class Resource extends PerunEntity {

	private Long facilityId;
	private String name;
	private String description;
	private Long voId;

	public Resource(Long id, Long facilityId, String name, String description, Long voId, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
		this.facilityId = facilityId;
		this.name = name;
		this.description = description;
		this.voId = voId;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
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

	@Override
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", this.getId());
		json.put("facility_id", this.facilityId);
		json.put("name", this.name);
		json.put("description", this.description);
		json.put("vo_id", this.voId);
		json.put("attributes", this.attributesToJson());

		return json;
	}
}
