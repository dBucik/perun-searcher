package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class UserExtSource extends PerunEntity {

	private Long userId;
	private String loginExt;
	private Long extSourceId;
	private int loa;
	private long lastAccess;

	public UserExtSource(Long id, Long userId, String loginExt, Long extSourceId,
						 int loa, long lastAccess, Map<String, PerunAttribute> attributes) {
		super(id, attributes);
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

	public int getLoa() {
		return loa;
	}

	public void setLoa(int loa) {
		this.loa = loa;
	}

	public long getLastAccess() {
		return lastAccess;
	}

	public void setLastAccess(long lastAccess) {
		this.lastAccess = lastAccess;
	}

	@Override
	public JSONObject toJson() {
		return null;
	}
}
