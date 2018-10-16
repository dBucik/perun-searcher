package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.ExtSource;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class ExtSourceMapper implements RowMapper<ExtSource> {

	@Override
	public ExtSource mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		String name = MappersUtils.getString(entityJson,"name");
		String type = MappersUtils.getString(entityJson,"type");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet, PerunAttributeType.STRING_TYPE);
		Long foreignId = MappersUtils.getForeignId(resultSet);

		return new ExtSource(id, name, type, attributes, foreignId);
	}
}
