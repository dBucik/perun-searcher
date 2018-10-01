package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.User;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static cz.metacentrum.perunsearch.persistence.mappers.MappersUtils.mapAttributes;

public class UserMapper implements RowMapper<User> {

	@Override
	public User mapRow(ResultSet resultSet, int i) throws SQLException {
		JSONObject entityJson = new JSONObject(resultSet.getString("entity"));

		Long id = entityJson.getLong("id");
		String firstName = entityJson.getString("first_name");
		String middleName = entityJson.getString("middle_name");
		String lastName = entityJson.getString("last_name");
		String titleBefore = entityJson.getString("title_before");
		String titleAfter = entityJson.getString("title_after");
		boolean service = "t".equals(entityJson.getString("service_acc"));
		boolean sponsored = "t".equals(entityJson.getString("sponsored_acc"));

		JSONArray attributesJson = new JSONArray(resultSet.getString("attributes"));
		Map<String, PerunAttribute> attributes;
		try {
			attributes = mapAttributes(attributesJson);
		} catch (AttributeTypeException e) {
			throw new RuntimeException("Error while parsing attributes", e);
			//TODO
		}

		return new User(id, firstName, middleName, lastName, titleBefore, titleAfter,
				service, sponsored, attributes);
	}
}
