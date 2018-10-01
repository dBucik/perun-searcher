package cz.metacentrum.perunsearch.persistence.models;

import cz.metacentrum.perunsearch.persistence.enums.InputAttributeType;
import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Attribute obtained as input from user in query.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class InputAttribute {

	private String friendlyName;
	private String name;
	private InputAttributeType type;
	private boolean isCore;
	private Object value;

	public InputAttribute(String friendlyName, String name, String type, Object value, boolean isCore) throws AttributeTypeException {
		this.friendlyName = friendlyName;
		this.name = name;
		this.type = InputAttributeType.fromString(type);
		this.value = value;
		this.isCore = isCore;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
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

	public void setType(String type) throws AttributeTypeException {
		this.type = InputAttributeType.fromString(type);
	}

	public void setValue(Object value) {
		this.value = value;
	}

	public Object getValue() {
		return value;
	}

	public boolean isCore() {
		return isCore;
	}

	public void setIsCore(boolean core) {
		isCore = core;
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
		for (Map.Entry<String, Object> entry: obj.toMap().entrySet()) {
			value.put(entry.getKey(), (String) entry.getValue());
		}

		return value;
	}
}
