package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.PerunEntity;

import java.util.List;

public class Member extends PerunEntity {

	private Long userId;
	private Long voId;

	public Member(Long id, List<PerunAttribute> attributes, Long userId, Long voId) {
		super(id, attributes);
		this.userId = userId;
		this.voId = voId;
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
}
