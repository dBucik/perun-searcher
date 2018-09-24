package cz.metacentrum.perunsearch.persistence.models;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;

import java.util.ArrayList;
import java.util.List;

public class Query {

	private String select;
	private String from;
	private String where;
	private List<Query> innerQueries = new ArrayList<>();

	private MapSqlParameterSource parameters = new MapSqlParameterSource();
	private int paramCounter = 1;

	public String nextParamName() {
		return ':' + String.valueOf(paramCounter);
	}

	public void addParameter(Object value) {
		parameters.addValue(String.valueOf(paramCounter), value);
	}

}
