package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

public class Vo extends PerunEntity {

	private String name;
	private String shortName;

	public Vo(Long id, String name, String shortName, Map<String, PerunAttribute> attributes) {
		super(id, attributes);
		this.name = name;
		this.shortName = shortName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Override
	public JSONObject toJson() {
		return null;
	}
}
