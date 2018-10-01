package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.Group;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.mappers.MappersUtils.mapAttributes;

public class GroupMapper implements RowMapper<Group> {

	@Override
	public Group mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		String name = entityJson.getString("name");
		String dsc = entityJson.getString("dsc");
		Long voId = entityJson.getLong("vo_id");
		Long parentGroupId = entityJson.optLong("parent_group_id", -1);
		if (parentGroupId == -1) {
			parentGroupId = null;
		}

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

		return new Group(id, name, dsc, voId, parentGroupId, attributes);
	}
}
