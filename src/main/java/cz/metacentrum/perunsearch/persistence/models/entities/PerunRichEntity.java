package cz.metacentrum.perunsearch.persistence.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;

import java.util.HashMap;
import java.util.Map;

@JsonIgnoreProperties(value = {"foreignId"})
public abstract class PerunRichEntity extends PerunEntity {

	private Map<String, PerunAttribute> attributes = new HashMap<>();
	@JsonIgnore
	protected PerunRichEntity(Integer id, Map<String, PerunAttribute> attributes, Integer foreignId) {
		super(id, foreignId);
		if (attributes != null) {
			this.attributes.putAll(attributes);
		}
	}

	public Map<String, PerunAttribute> getAttributes() {
		return attributes;
	}

	public void setAttributes(Map<String, PerunAttribute> attributes) {
		this.attributes.putAll(attributes);
	}

}
