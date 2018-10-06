package cz.metacentrum.perunsearch.persistence.models;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Query {

	private PerunEntityType entityType;
	private String queryString;
	private MapSqlParameterSource parameters = new MapSqlParameterSource();

	private int paramCounter = 0;
	private List<Query> innerQueries = new ArrayList<>();
	private List<InputAttribute> inputAttributes;

	private boolean hasWhere;

	public PerunEntityType getEntityType() {
		return entityType;
	}

	public void setEntityType(PerunEntityType entityType) {
		this.entityType = entityType;
	}

	public String getQueryString() {
		return queryString;
	}

	public void setQueryString(String queryString) {
		this.queryString = queryString;
	}

	public List<Query> getInnerQueries() {
		return innerQueries;
	}

	public void setInnerQueries(List<Query> innerQueries) {
		this.innerQueries = innerQueries;
	}

	public List<InputAttribute> getInputAttributes() {
		return inputAttributes;
	}

	public void setInputAttributes(List<InputAttribute> inputAttributes) {
		this.inputAttributes = inputAttributes;
	}

	public String nextParam(Object value) {
		paramCounter++;
		parameters.addValue(String.valueOf(paramCounter), value);
		return ':' + String.valueOf(paramCounter);
	}

	public void setIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return;
		}

		String query = "ent.id IN " + nextParam(ids.toArray(new Long[] {}));
		if (! hasWhere) {
			query = " WHERE " + query;
		}

		queryString += query;
	}

	public void setHasWhere(boolean hasWhere) {
		this.hasWhere = hasWhere;
	}

	public MapSqlParameterSource getParameters() {
		return parameters;
	}
}
