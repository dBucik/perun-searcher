package cz.metacentrum.perunsearch.service;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.ExtSourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.FacilityInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.GroupInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.HostInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputUtils;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.MemberInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.ResourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.ServiceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.UserExtSourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.UserInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.VoInput;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonToInputParser {

	//TODO: add input validation

	private static final String CORE_KEY = "CORE";
	private static final String ATTRS_KEY = "ATTRIBUTES";

	public static InputEntity parseInput(String inputString) throws InputParseException, IllegalRelationException {
		JSONObject json = new JSONObject(inputString);
		return parseInputEntity(json, true);
	}

	private static InputEntity parseInputEntity(JSONObject json, boolean isTopLevel) throws InputParseException, IllegalRelationException {
		if (! json.has("entityName") || json.isNull("entityName")) {
			throw new InputParseException("Input does not have specified entity");
		}

		String entity = json.getString("entityName");
		JSONArray attributesJsonArray = json.optJSONArray("attributes");
		JSONArray attrsNamesJsonArray = json.optJSONArray("attributeNames");
		JSONArray entitiesJsonArray = json.optJSONArray("relations");

		Map<String, List<InputAttribute>> attributes = parseAttributes(attributesJsonArray);
		List<String> attrsNames = parseAttrsNames(attrsNamesJsonArray);
		List<InputEntity> entities = parseInnerInputs(entitiesJsonArray);

		switch (entity.toUpperCase()) {
			case "EXT_SOURCE":
				return new ExtSourceInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "FACILITY":
				return new FacilityInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "GROUP":
				return new GroupInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "HOST":
				return new HostInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "MEMBER":
				return new MemberInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "RESOURCE":
				return new ResourceInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "SERVICE":
				return new ServiceInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "USER_EXT_SOURCE":
				return new UserExtSourceInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "USER":
				return new UserInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
			case "VO":
				return new VoInput(isTopLevel, attributes.get(CORE_KEY),
						attributes.get(ATTRS_KEY), attrsNames, entities);
		}

		return null;
	}

	private static Map<String,List<InputAttribute>> parseAttributes(JSONArray attrsJsonArray) throws InputParseException {
		Map<String, List<InputAttribute>> map = new HashMap<>();
		if (attrsJsonArray == null) {
			return map;
		}

		List<InputAttribute> core = new ArrayList<>();
		List<InputAttribute> attributes = new ArrayList<>();

		for (int i = 0; i < attrsJsonArray.length(); i++) {
			JSONObject attributeJson = attrsJsonArray.getJSONObject(i);
			InputAttribute attribute = parseAttribute(attributeJson);
			if (attribute.isCore()) {
				core.add(attribute);
			} else {
				attributes.add(attribute);
			}
		}

		map.put(CORE_KEY, core);
		map.put(ATTRS_KEY, attributes);

		return map;
	}

	private static List<InputEntity> parseInnerInputs(JSONArray entitiesJsonArray) throws IllegalRelationException, InputParseException {
		List<InputEntity> entities = new ArrayList<>();
		if (entitiesJsonArray == null) {
			return entities;
		}

		for (int i = 0; i < entitiesJsonArray.length(); i++) {
			JSONObject entityJson = entitiesJsonArray.getJSONObject(i);
			entities.add(parseInputEntity(entityJson, false));
		}

		return entities;
	}

	private static List<String> parseAttrsNames(JSONArray attrsNamesJsonArray) {
		List<String> attrsNames = new ArrayList<>();
		if (attrsNamesJsonArray == null) {
			return attrsNames;
		}
		for (int i = 0; i < attrsNamesJsonArray.length(); i++) {
			attrsNames.add(attrsNamesJsonArray.getString(i));
		}

		return attrsNames;
	}

	private static InputAttribute parseAttribute(JSONObject attributeJson) throws InputParseException {
		String name = attributeJson.getString("name");
		boolean isCore = name.contains(":core:");
		String friendlyName;

		if (isCore) {
			friendlyName = InputUtils.translateCoreAttribute(name);
		} else {
			friendlyName = extractFriendlyName(name);
		}

		Object value = attributeJson.get("value");

		try {
			return new InputAttribute(friendlyName, name, value, isCore);
		} catch (AttributeTypeException e) {
			throw new InputParseException("Input attribute cannot be parsed", e);
		}
	}

	private static String extractFriendlyName(String name) {
		String[] parts = name.split(":");
		int last = parts.length - 1;

		return parts[last];
	}

}
