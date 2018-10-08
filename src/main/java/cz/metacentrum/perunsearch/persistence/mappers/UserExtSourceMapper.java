package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.UserExtSource;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Long foreignId = MappersUtils.getForeignId(resultSet);

		return new UserExtSource(id, userId, loginExt, extSourceId, loa, lastAccess, attributes, foreignId);
	}
}
