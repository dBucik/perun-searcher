package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.User;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		//TODO: change to mappersUtils
		Long id = entityJson.getLong("id");
		String firstName = MappersUtils.getString(entityJson, "first_name");
		String middleName = MappersUtils.getString(entityJson, "middle_name");
		String lastName = MappersUtils.getString(entityJson, "last_name");
		String titleBefore = MappersUtils.getString(entityJson, "title_before");
		String titleAfter = MappersUtils.getString(entityJson, "title_after");
		Boolean service = "1".equals(MappersUtils.getString(entityJson, "service_acc"));
		Boolean sponsored = "1".equals(MappersUtils.getString(entityJson, "sponsored_acc"));

		Map<String, PerunAttribute> attributes = MappersUtils.getAttributes(resultSet);
		Long foreignId = MappersUtils.getForeignId(resultSet);

		return new User(id, firstName, middleName, lastName, titleBefore, titleAfter,
				service, sponsored, attributes, foreignId);
	}
}
