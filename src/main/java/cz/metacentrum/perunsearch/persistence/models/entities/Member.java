package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.Map;

public class Member extends PerunEntity {

	private Long userId;
	private Long voId;
	private Boolean sponsored;

	public Member(Long id, Long userId, Long voId, Boolean sponsored, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
		this.userId = userId;
		this.voId = voId;
		this.sponsored = sponsored;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getVoId() {
		return voId;
	}

	public void setVoId(Long voId) {
		this.voId = voId;
	}

	public Boolean isSponsored() {
		return sponsored;
	}

	public void setSponsored(Boolean sponsored) {
		this.sponsored = sponsored;
	}
}
