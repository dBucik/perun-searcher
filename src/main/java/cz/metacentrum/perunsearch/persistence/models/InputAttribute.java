package cz.metacentrum.perunsearch.persistence.models;

import cz.metacentrum.perunsearch.persistence.enums.InputAttributeType;
import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Attribute obtained as input from user in query.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class InputAttribute {

	private String name;
	private InputAttributeType type;
	private boolean likeMatch;
	private Object value;

	public InputAttribute(String name, boolean likeMatch, Object value) throws AttributeTypeException {
		this.name = name;
		this.type = getType(value);
		this.likeMatch = likeMatch;
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public InputAttributeType getType() {
		return type;
	}

	public void setType(InputAttributeType type) {
		this.type = type;
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public boolean isLikeMatch() {
		return likeMatch;
	}

	public void setLikeMatch(boolean likeMatch) {
		this.likeMatch = likeMatch;
	}

	public String stringValue() {
		return value.toString();
	}

	public String valueAsString() {
		return (String) value;
	}

	public Integer valueAsInt() {
		return (Integer) value;
	}

	public Boolean valueAsBoolean() {
		return (Boolean) value;
	}

	public List<String> valueAsList() {
		JSONArray arr = (JSONArray) this.value;
		List<String> value = new ArrayList<>();
		for (int i = 0; i < arr.length(); i++) {
			value.add(arr.get(i).toString());
		}
		return value;
	}

	public Map<String, String> valueAsMap() {
		JSONObject obj = (JSONObject) this.value;
		Map<String, String> value = new LinkedHashMap<>();
		Iterator<String> keys = obj.keys();
		while (keys.hasNext()) {
			String key = keys.next();
			String val = obj.getString(key);
			value.put(key, val);
		}

		return value;
	}

	private InputAttributeType getType(Object value) throws AttributeTypeException {
		if (value instanceof String) {
			return InputAttributeType.STRING;
		} else if (value instanceof Number) {
			return InputAttributeType.INTEGER;
		} else if (value instanceof Boolean) {
			return InputAttributeType.BOOLEAN;
		} else if (value instanceof JSONArray) {
			return InputAttributeType.ARRAY;
		} else if (value instanceof JSONObject) {
			return InputAttributeType.MAP;
		} else if (value == JSONObject.NULL) {
			return InputAttributeType.NULL;
		} else if (value instanceof Timestamp) {
			return InputAttributeType.TIMESTAMP;
		}

		else throw new AttributeTypeException("Attribute cannot have type: " + type);
	}

	@Override
	public int hashCode() {
		int hash = 1;
		if (this.name != null) {
			hash *= 31 * this.name.hashCode();
		}

		if (this.type != null) {
			hash *= 31 * this.type.hashCode();
		}

		if (this.value != null) {
			hash *= 31 * this.value.hashCode();
		}

		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (! this.getClass().equals(o.getClass())) {
			return false;
		} else {
			InputAttribute them = (InputAttribute) o;
			return Objects.equals(this.name, them.name)
					&& Objects.equals(this.type, them.type)
					&& Objects.equals(this.value, them.value);
		}
	}
}
