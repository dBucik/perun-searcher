package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.Member;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MemberMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		Long userId = MappersUtils.getLong(entityJson, "user_id");
		Long voId = MappersUtils.getLong(entityJson, "vo_id");
		Boolean sponsored = MappersUtils.getBoolean(entityJson, "sponsored");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Long foreignId = MappersUtils.getForeignId(resultSet);

		return new Member(id, userId, voId, sponsored, attributes, foreignId);
	}
}
