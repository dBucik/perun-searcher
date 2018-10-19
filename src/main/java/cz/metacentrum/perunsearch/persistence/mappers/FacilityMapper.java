package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Facility;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class FacilityMapper implements RowMapper<Facility> {

	@Override
	public Facility mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		String name = MappersUtils.getString(entityJson,"name");
		String dsc = MappersUtils.getString(entityJson,"dsc");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new Facility(id, name, dsc, attributes, foreignId);
	}
}
