package cz.metacentrum.perunsearch.persistence.models;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public abstract class PerunEntity {

	private Long id;
	private List<PerunAttribute> attributes = new ArrayList<>();

	public PerunEntity(Long id, List<PerunAttribute> attributes) {
		this.id = id;
		this.attributes.addAll(attributes);
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}


	/*
	public abstract JSONObject toJson();

	public abstract String toJsonString();
	*/
}
