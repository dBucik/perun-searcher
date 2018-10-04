package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.UserExtSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.mappers.MappersUtils.mapAttributes;

public class UserExtSourceMapper implements RowMapper<UserExtSource> {

	@Override
	public UserExtSource mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		Long userId = MappersUtils.getLong(entityJson, "user_id");
		String loginExt = MappersUtils.getString(entityJson,"login_ext");
		Long extSourceId = MappersUtils.getLong(entityJson, "ext_source_id");
		Integer loa = MappersUtils.getInt(entityJson, "loa");
		Long lastAccess = MappersUtils.getTimestampMillis(entityJson, "last_access");

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

		return new UserExtSource(id, userId, loginExt, extSourceId, loa, lastAccess, attributes, foreignId);
	}
}
