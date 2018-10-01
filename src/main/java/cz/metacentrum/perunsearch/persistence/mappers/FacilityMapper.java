package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.Facility;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.mappers.MappersUtils.mapAttributes;

public class FacilityMapper implements RowMapper<Facility> {

	@Override
	public Facility mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		String name = entityJson.getString("name");
		String dsc = entityJson.getString("dsc");

		JSONArray attributesJson = new JSONArray(resultSet.getString("attributes"));
		Map<String, PerunAttribute> attributes;
		try {
			attributes = mapAttributes(attributesJson);
		} catch (AttributeTypeException e) {
			throw new RuntimeException("Error while parsing attributes", e);
			//TODO
		}

		return new Facility(id, name, dsc, attributes);
	}
}
