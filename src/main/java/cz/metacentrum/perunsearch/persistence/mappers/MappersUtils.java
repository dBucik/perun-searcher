package cz.metacentrum.perunsearch.persistence.mappers;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MappersUtils {

	public static Map<String, PerunAttribute> mapAttributes(JSONArray json) throws AttributeTypeException {
		Map<String, PerunAttribute> result = new HashMap<>();
		for (int i = 0; i < json.length(); i++) {
			JSONObject attribute = json.getJSONObject(i);
			Long id = attribute.getLong("id");
			String namespace = attribute.getString("namespace");
			String friendlyName = attribute.getString("friendlyName");
			String type = attribute.getString("type");
			String value = attribute.getString("value");

			result.put(friendlyName, new PerunAttribute(id, namespace, friendlyName, type, value));
		}

		return result;
	}
}
