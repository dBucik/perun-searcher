package cz.metacentrum.perunsearch.persistence.models;

import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Attribute stored in Perun.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class PerunAttribute {

	private Long id;
	private String namespace;
	private String friendlyName;
	private PerunAttributeType type;
	private String value;

	public PerunAttribute(Long id, String namespace, String friendlyName, String type, String value) throws AttributeTypeException {
		this.id = id;
		this.namespace = namespace;
		this.friendlyName = friendlyName;
		this.type = PerunAttributeType.fromString(type);
		this.value = value;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getFriendlyName() {
		return friendlyName;
	}

	public void setFriendlyName(String friendlyName) {
		this.friendlyName = friendlyName;
	}

	public PerunAttributeType getType() {
		return type;
	}

	public void setType(PerunAttributeType type) {
		this.type = type;
	}

	public void setType(String type) throws AttributeTypeException {
		this.type = PerunAttributeType.fromString(type);
	}

	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * Return value as String.
	 * @return String or NULL if type does not match.
	 */
	public String valueAsString() {
		return (type == PerunAttributeType.STRING
				|| type == PerunAttributeType.LARGE_STRING) ? this.value : null;
	}

	/**
	 * Return value as Integer.
	 * @return Integer or NULL if type does not match.
	 */
	public Integer valueAsInt() {
		return (type == PerunAttributeType.INTEGER) ? Integer.parseInt(value) : null;
	}

	/**
	 * Return value as Boolean.
	 * @return Boolean or NULL if type does not match.
	 */
	public Boolean valueAsBoolean() {
		return (type == PerunAttributeType.BOOLEAN) ? Boolean.parseBoolean(value) : null;
	}

	/**
	 * Return value as List of Strings.
	 * @return List of Strings or NULL if type does not match.
	 */
	public List<String> valueAsList() {
		if (type != PerunAttributeType.ARRAY && type != PerunAttributeType.LARGE_ARRAY_LIST) {
			return null;
		}

		String[] parts = value.split(";");
		return Arrays.asList(parts);
	}

	/**
	 * Return value as Map.
	 * @return Map (key = String, value = String) or NULL if type does not match.
	 */
	public Map<String, String> valueAsMap() {
		if (type != PerunAttributeType.MAP) {
			return null;
		}

		String[] parts = value.split(";");
		Map<String, String> valuesMap = new LinkedHashMap<>();
		for (String part: parts) {
			String[] subParts = part.split("=");
			valuesMap.put(subParts[0], subParts[1]);
		}

		return valuesMap;
	}

	/**
	 * Convert attribute to JSON representation.
	 * @return attribute in JSON format.
	 */
	public JSONObject toJson() {
		JSONObject json = new JSONObject();
		json.put("id", this.id);
		json.put("friendlyName", this.friendlyName);
		json.put("namespace", this.namespace);
		json.put("type", this.type.toString());
		json.put("value", getTrueValue());

		return json;
	}

	private Object getTrueValue() {
		switch (this.type) {
			case STRING:
			case LARGE_STRING:
				return valueAsString();
			case INTEGER: return valueAsInt();
			case BOOLEAN: return valueAsBoolean();
			case ARRAY:
			case LARGE_ARRAY_LIST:
				return valueAsList();
			case MAP: return valueAsMap();
			default: return null;
		}
	}

}
