package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.entities.basic.Service;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceMapper implements RowMapper<Service> {

	@Override
	public Service mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		String name = MappersUtils.getString(entityJson,"name");
		String description = MappersUtils.getString(entityJson,"description");
		Integer delay = MappersUtils.getInt(entityJson, "delay");
		Integer recurrence = MappersUtils.getInt(entityJson, "recurrence");
		Boolean enabled = "1".equals(MappersUtils.getString(entityJson, "enabled"));
		String script = MappersUtils.getString(entityJson,"script");

		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new Service(id, name, description, delay, recurrence, enabled, script, foreignId);
	}
}
