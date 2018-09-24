package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.PerunEntity;

import java.util.List;

public class Resource extends PerunEntity {

	private Long facilityId;
	private String name;
	private String description;

	public Resource(Long id, List<PerunAttribute> attributes, Long facilityId, String name, String description) {
		super(id, attributes);
		this.facilityId = facilityId;
		this.name = name;
		this.description = description;
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
}
