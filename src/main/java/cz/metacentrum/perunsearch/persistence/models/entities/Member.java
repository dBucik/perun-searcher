package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.Map;

public class Member extends PerunEntity {

	private Long userId;
	private Long voId;
	private boolean sponsored;

	public Member(Long id, Long userId, Long voId, boolean sponsored, Map<String, PerunAttribute> attributes) {
		super(id, attributes);
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

	public boolean isSponsored() {
		return sponsored;
	}

	public void setSponsored(boolean sponsored) {
		this.sponsored = sponsored;
	}

	@Override
	public JSONObject toJson() {
		return null;
	}
}
