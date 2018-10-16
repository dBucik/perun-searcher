package cz.metacentrum.perunsearch.persistence.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Objects;

@JsonIgnoreProperties(value = {"foreignId"})
public abstract class PerunEntity {

	private Long id;
	@JsonIgnore
	private Long foreignId;

	public PerunEntity(Long id, Long foreignId) {
		this.id = id;
		this.foreignId = foreignId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public Long getForeignId() {
		return foreignId;
	}

	public void setForeignId(Long foreignId) {
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
