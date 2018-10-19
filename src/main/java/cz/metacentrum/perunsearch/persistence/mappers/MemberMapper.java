package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Member;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class MemberMapper implements RowMapper<Member> {

	@Override
	public Member mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Integer id = entityJson.getInt("id");
		Integer userId = MappersUtils.getInteger(entityJson, "user_id");
		Integer voId = MappersUtils.getInteger(entityJson, "vo_id");
		Boolean sponsored = MappersUtils.getBoolean(entityJson, "sponsored");

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Integer foreignId = MappersUtils.getForeignId(resultSet);

		return new Member(id, userId, voId, sponsored, attributes, foreignId);
	}
}
