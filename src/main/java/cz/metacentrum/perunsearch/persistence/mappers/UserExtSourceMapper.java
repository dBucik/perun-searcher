package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.UserExtSource;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.mappers.MappersUtils.mapAttributes;

public class UserExtSourceMapper implements RowMapper<UserExtSource> {

	@Override
	public UserExtSource mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		Long userId = entityJson.getLong("user_id");
		String loginExt = entityJson.getString("login_ext");
		Long extSourceId = entityJson.getLong("ext_sources_id");
		int loa = entityJson.getInt("loa");
		LocalDateTime lastAccess = LocalDateTime.parse(entityJson.getString("last_access"));

		JSONArray attributesJson = new JSONArray(resultSet.getString("attributes"));
		Map<String, PerunAttribute> attributes;
		try {
			attributes = mapAttributes(attributesJson);
		} catch (AttributeTypeException e) {
			throw new RuntimeException("Error while parsing attributes", e);
			//TODO
		}

		return new UserExtSource(id, userId, loginExt, extSourceId, loa,
				lastAccess.toEpochSecond(ZoneOffset.UTC), attributes);
	}
}
