package cz.metacentrum.perunsearch.persistence.models.entities.basic;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunRichEntity;

import java.util.Map;

public class Resource extends PerunRichEntity {

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
}
