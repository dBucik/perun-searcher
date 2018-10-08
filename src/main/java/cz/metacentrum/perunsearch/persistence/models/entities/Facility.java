package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;

import java.util.Map;

public class Facility extends PerunEntity {

	private String name;
	private String description;

	public Facility(Long id, String name, String description, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
		this.name = name;
		this.description = description;
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
}
