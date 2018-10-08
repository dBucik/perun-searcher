package cz.metacentrum.perunsearch.persistence.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(value = {"foreignId"})
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

}
