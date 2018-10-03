package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public abstract class PerunEntity {

	private Long id;
	private Map<String, PerunAttribute> attributes = new HashMap<>();
	private Long foreignId;

	public PerunEntity(Long id, Map<String, PerunAttribute> attributes, Long foreignId) {
		this.id = id;
		this.attributes.putAll(attributes);
		this.foreignId = foreignId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Map<String, PerunAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, PerunAttribute> attributes) {
		this.attributes.putAll(attributes);
	}

	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
		this.foreignId = foreignId;
	}

	public abstract JSONObject toJson();

	public JSONArray attributesToJson() {
		JSONArray arr = new JSONArray();
		for (PerunAttribute a: attributes.values()) {
			arr.put(a.toJson());
		}

		return arr;
	}
}
