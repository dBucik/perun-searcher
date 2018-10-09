package cz.metacentrum.perunsearch.persistence.models.entities.basic;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import org.json.JSONObject;

import java.util.Map;

public class UserExtSource extends PerunEntity {

	private Long userId;
	private String loginExt;
	private Long extSourceId;
	private Integer loa;
	private Long lastAccess;

	public UserExtSource(Long id, Long userId, String loginExt, Long extSourceId,
						 Integer loa, Long lastAccess, Map<String, PerunAttribute> attributes, Long foreignId) {
		super(id, attributes, foreignId);
		this.userId = userId;
		this.loginExt = loginExt;
		this.extSourceId = extSourceId;
		this.loa = loa;
		this.lastAccess = lastAccess;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getLoginExt() {
		return loginExt;
	}

	public void setLoginExt(String loginExt) {
		this.loginExt = loginExt;
	}

	public Long getExtSourceId() {
		return extSourceId;
	}

	public void setExtSourceId(Long extSourceId) {
		this.extSourceId = extSourceId;
	}

	public Integer getLoa() {
		return loa;
	}

	public void setLoa(Integer loa) {
		this.loa = loa;
	}

	public Long getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(Long lastAccess) {
		this.lastAccess = lastAccess;
	}
}
