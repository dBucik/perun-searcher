package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.PerunEntity;

import java.util.List;

public class Group extends PerunEntity {

	private String name;
	private String description;
	private Long voId;

	public Group(Long id, List<PerunAttribute> attributes, String name, String description, Long voId) {
		super(id, attributes);
		this.name = name;
		this.description = description;
		this.voId = voId;
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
}
