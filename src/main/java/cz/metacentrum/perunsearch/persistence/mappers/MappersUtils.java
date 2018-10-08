package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONArray;
import org.json.JSONObject;
import org.postgresql.util.PSQLException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

public class MappersUtils {

	public static Long getForeignId(ResultSet resultSet) throws SQLException {
		Long foreignId = null;
		try {
			foreignId = resultSet.getLong("foreign_id");
		} catch (PSQLException e) {
			//this is fine, no foreign id fetched
			//TODO
		}

		return foreignId;
	}

	public static Map<String, PerunAttribute> getAttributes(ResultSet resultSet) throws SQLException {
		Map<String, PerunAttribute> attributes = new HashMap<>();
		try {
			JSONArray attributesJson = new JSONArray(resultSet.getString("attributes"));
			attributes = mapAttributes(attributesJson);
		} catch (PSQLException e) {
			//this is fine, no attributes were fetched
		} catch (AttributeTypeException e) {
			throw new RuntimeException("Error while parsing attributes", e);
			//TODO
		}

		return attributes;
	}

	public static String getString(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getString(key) : null;
	}

	public static Boolean getBoolean(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getBoolean(key) : null;
	}

	public static Long getLong(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getLong(key) : null;
	}

	public static Integer getInt(JSONObject json, String key) {
		return (json.has(key) && json.get(key) != JSONObject.NULL) ?
				json.getInt(key) : null;
	}

	public static Long getTimestampMillis(JSONObject json, String key) {
		if (json.has(key) && json.get(key) != JSONObject.NULL) {
			LocalDateTime time = LocalDateTime.parse(json.getString(key));
			return time.toEpochSecond(ZoneOffset.UTC);
		}
		return null;
	}

	private static Map<String, PerunAttribute> mapAttributes(JSONArray json) throws AttributeTypeException {
		Map<String, PerunAttribute> result = new HashMap<>();
		for (int i = 0; i < json.length(); i++) {
			JSONObject attribute = json.getJSONObject(i);
			String name = attribute.getString("name");
			String type = attribute.getString("type");
			String value = attribute.getString("value");

			result.put(name, new PerunAttribute(name, type, value));
		}

		return result;
	}
}
