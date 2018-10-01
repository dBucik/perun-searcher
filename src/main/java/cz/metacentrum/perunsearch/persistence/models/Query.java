package cz.metacentrum.perunsearch.persistence.models;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Query {

	private PerunEntityType entityType;
	private String queryString;
	private MapSqlParameterSource parameters = new MapSqlParameterSource();

	private int paramCounter = 1;
	private Map<PerunEntityType,Query> innerQueries = new HashMap<>();
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

	public Map<PerunEntityType, Query> getInnerQueries() {
		return innerQueries;
	}

	public void setInnerQueries(Map<PerunEntityType, Query> innerQueries) {
		this.innerQueries = innerQueries;
	}

	public List<InputAttribute> getInputAttributes() {
		return inputAttributes;
	}

	public void setInputAttributes(List<InputAttribute> inputAttributes) {
		this.inputAttributes = inputAttributes;
	}

	public String nextParamName() {
		return ':' + String.valueOf(paramCounter);
	}

	public void addParameter(Object value) {
		parameters.addValue(String.valueOf(paramCounter), value);
	}

	public void setIds(Set<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return;
		}

		String query = "ent.id IN " + nextParamName();
		addParameter(ids.toArray(new Long[] {}));
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
