package cz.metacentrum.perunsearch.persistence.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(value = {"foreignId"})
public abstract class PerunEntity {

	private Integer id;
	@JsonIgnore
	private Integer foreignId;

	protected PerunEntity(Integer id, Integer foreignId) {
		this.id = id;
		this.foreignId = foreignId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getId() {
		return id;
	}

	public Integer getForeignId() {
		return foreignId;
	}

	public void setForeignId(Integer foreignId) {
		this.foreignId = foreignId;
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof PerunEntity)) {
			return false;
		} else if (! this.getClass().equals(o.getClass())) {
			return false;
		}

		PerunEntity them = (PerunEntity) o;

		return Objects.equals(this.id, them.id);
	}
}
