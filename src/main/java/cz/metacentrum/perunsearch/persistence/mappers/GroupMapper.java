package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.Group;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class GroupMapper implements RowMapper<Group> {

	@Override
	public Group mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		String name = MappersUtils.getString(entityJson,"name");
		String dsc = MappersUtils.getString(entityJson,"dsc");
		Long voId = MappersUtils.getLong(entityJson, "vo_id");
		Long parentGroupId = MappersUtils.getLong(entityJson, "parent_group_id");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Long foreignId = MappersUtils.getForeignId(resultSet);

		return new Group(id, name, dsc, voId, parentGroupId, attributes, foreignId);
	}
}
