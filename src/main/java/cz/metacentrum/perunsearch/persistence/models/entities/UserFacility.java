package cz.metacentrum.perunsearch.persistence.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;

import java.util.Map;

@JsonIgnoreProperties(value = "id")
public class UserFacility extends PerunEntity {

	private Long userId;
	private Long facilityId;

	public UserFacility(Long id, Long userId, Long facilityId, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
		this.userId = userId;
		this.facilityId = facilityId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getFacilityId() {
		return facilityId;
	}

	public void setFacilityId(Long resourceId) {
		this.facilityId = resourceId;
	}
}
