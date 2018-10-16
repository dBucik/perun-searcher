package cz.metacentrum.perunsearch.persistence.models.entities.basic;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunRichEntity;

import java.util.Map;

public class Host extends PerunRichEntity {

	private String hostname;
	private Long facilityId;
	private String description;

	public Host(Long id, String hostname, Long facilityId, String description, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
		this.hostname = hostname;
		this.facilityId = facilityId;
		this.description = description;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long facilityId) {
		this.facilityId = facilityId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
