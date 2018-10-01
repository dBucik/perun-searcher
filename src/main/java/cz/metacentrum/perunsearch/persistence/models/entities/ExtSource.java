package cz.metacentrum.perunsearch.persistence.models.entities;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONObject;

import java.util.Map;

public class ExtSource extends PerunEntity {

	private String name;
	private String type;

	public ExtSource(Long id, String name, String type, Map<String, PerunAttribute> attributes) {
		super(id, attributes);
		this.name = name;
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public JSONObject toJson() {
		return null;
	}
}
