package cz.metacentrum.perunsearch.persistence.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@JsonIgnoreProperties(value = {"foreignId"})
public abstract class PerunEntity {

	private Long id;
	private Map<String, PerunAttribute> attributes = new HashMap<>();
	@JsonIgnore
	private Long foreignId;

	public PerunEntity(Long id, Map<String, PerunAttribute> attributes, Long foreignId) {
		this.id = id;
		if (attributes != null) {
			this.attributes.putAll(attributes);
		}
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

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PerunEntity)) {
			return false;
		} else if (! this.getClass().equals(o.getClass())) {
			return false;
		}

		PerunEntity them = (PerunEntity) o;

		return Objects.equals(this.id, them.id);
	}
}
