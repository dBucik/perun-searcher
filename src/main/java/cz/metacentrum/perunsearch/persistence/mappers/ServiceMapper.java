package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.Service;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.mappers.MappersUtils.mapAttributes;

public class ServiceMapper implements RowMapper<Service> {

	@Override
	public Service mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		String name = MappersUtils.getString(entityJson,"name");
		String description = MappersUtils.getString(entityJson,"description");
		Integer delay = MappersUtils.getInt(entityJson, "delay");
		Integer recurrence = MappersUtils.getInt(entityJson, "recurrence");
		String enabledStr = MappersUtils.getString(entityJson,"enabled");
		Boolean enabled = (enabledStr != null) ? "t".equals(enabledStr) : null;
		String script = MappersUtils.getString(entityJson,"script");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		try {
			JSONArray attributesJson = new JSONArray(resultSet.getString("attributes"));
			attributes = mapAttributes(attributesJson);
		} catch (PSQLException e) {
			//this is fine, no attributes were fetched;
		} catch (AttributeTypeException e) {
			throw new RuntimeException("Error while parsing attributes", e);
			//TODO
		}

		Long foreignId = null;
		try {
			foreignId = resultSet.getLong("foreign_id");
		} catch (PSQLException e) {
			//this is fine, no foreign id fetched
			//TODO
		}

		return new Service(id, name, description, delay, recurrence, enabled, script, attributes, foreignId);
	}
}
