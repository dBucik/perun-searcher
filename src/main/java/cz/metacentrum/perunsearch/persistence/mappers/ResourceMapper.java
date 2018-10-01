package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.mappers.MappersUtils.mapAttributes;

public class ResourceMapper implements RowMapper<Resource> {

	@Override
	public Resource mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		Long facilityId = entityJson.getLong("facility_id");
		String name = entityJson.getString("name");
		String dsc = entityJson.getString("dsc");
		Long voId = entityJson.getLong("vo_id");

		JSONArray attributesJson = new JSONArray(resultSet.getString("attributes"));
		Map<String, PerunAttribute> attributes;
		try {
			attributes = mapAttributes(attributesJson);
		} catch (AttributeTypeException e) {
			throw new RuntimeException("Error while parsing attributes", e);
			//TODO
		}

		return new Resource(id, facilityId, name, dsc, voId, attributes);
	}
}
