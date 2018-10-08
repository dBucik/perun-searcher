package cz.metacentrum.perunsearch.service;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.ExtSourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.FacilityInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.GroupInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.GroupResourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.HostInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.MemberGroupInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.MemberInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.MemberResourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.ResourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.ServiceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.UserExtSourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.UserFacilityInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.UserInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.VoInput;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonToInputParser {

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

		List<InputAttribute> attributes = parseAttributes(attributesJsonArray);
		List<String> attrsNames = parseAttrsNames(attrsNamesJsonArray);
		List<InputEntity> entities = parseInnerInputs(entitiesJsonArray);

		return resolveEntity(isTopLevel, json, entity, attributes, attrsNames, entities);
	}

	private static InputEntity resolveEntity(boolean isTopLevel, JSONObject json, String entity, List<InputAttribute> attributes,
											 List<String> attrsNames, List<InputEntity> entities) throws IllegalRelationException {
		switch (entity.toUpperCase()) {
			case "EXT_SOURCE": {
				Map<String, Object> core = mapCoreExtSource(json);
				return new ExtSourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "FACILITY": {
				Map<String, Object> core = mapCoreFacility(json);
				return new FacilityInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "GROUP": {
				Map<String, Object> core = mapCoreGroup(json);
				return new GroupInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "HOST": {
				Map<String, Object> core = mapCoreHost(json);
				return new HostInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER": {
				Map<String, Object> core = mapCoreMember(json);
				return new MemberInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "RESOURCE": {
				Map<String, Object> core = mapCoreResource(json);
				return new ResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "SERVICE": {
				Map<String, Object> core = mapCoreService(json);
				return new ServiceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "USER_EXT_SOURCE": {
				Map<String, Object> core = mapCoreUserExtSource(json);
				return new UserExtSourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "USER": {
				Map<String, Object> core = mapCoreUser(json);
				return new UserInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "VO": {
				Map<String, Object> core = mapCoreVo(json);
				return new VoInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "GROUP_RESOURCE": {
				Map<String, Object> core = mapCoreGroupResource(json);
				return new GroupResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER_GROUP": {
				Map<String, Object> core = mapCoreMemberGroup(json);
				return new MemberGroupInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER_RESOURCE": {
				Map<String, Object> core = mapCoreMemberResource(json);
				return new MemberResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "USER_FACILITY": {
				Map<String, Object> core = mapCoreUserFacility(json);
				return new UserFacilityInput(isTopLevel, core, attributes, attrsNames, entities);
			}
		}

		return null;
	}

	private static Map<String, Object> mapCoreExtSource(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("name")) {
			map.put("name", getString(json, "name"));
		}

		if (json.has("type")) {
			map.put("type", getString(json, "type"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreFacility(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("name")) {
			map.put("name", getString(json, "name"));
		}

		if (json.has("description")) {
			map.put("dsc", getString(json, "description"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreGroup(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("name")) {
			map.put("name", getString(json, "name"));
		}

		if (json.has("description")) {
			map.put("dsc", getString(json, "description"));
		}

		if (json.has("voId")) {
			map.put("vo_id", getLong(json, "voId"));
		}

		if (json.has("parentGroupId")) {
			map.put("parent_group_id", getLong(json, "parentGroupId"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreHost(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("hostname")) {
			map.put("hostname", getString(json, "hostname"));
		}

		if (json.has("facilityId")) {
			map.put("facility_id", getLong(json, "facilityId"));
		}

		if (json.has("description")) {
			map.put("dsc", getString(json, "description"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreMember(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("userId")) {
			map.put("user_id", getLong(json, "userId"));
		}

		if (json.has("voId")) {
			map.put("vo_id", getLong(json, "voId"));
		}

		if (json.has("sponsored")) {
			map.put("sponsored", getBoolean(json, "sponsored"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreResource(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("facilityId")) {
			map.put("facility_id", getLong(json, "facilityId"));
		}

		if (json.has("name")) {
			map.put("name", getString(json, "name"));
		}

		if (json.has("description")) {
			map.put("dsc", getString(json, "description"));
		}

		if (json.has("voId")) {
			map.put("vo_id", getLong(json, "voId"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreService(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("name")) {
			map.put("name", getString(json, "name"));
		}

		if (json.has("description")) {
			map.put("description", getString(json, "description"));
		}

		if (json.has("delay")) {
			map.put("delay", getInt(json, "delay"));
		}

		if (json.has("recurrence")) {
			map.put("recurrence", getInt(json, "recurrence"));
		}

		if (json.has("enabled")) {
			Boolean param = getBoolean(json, "enabled");
			if (param == null) {
				map.put("enabled", null);
			} else if (param) {
				map.put("enabled", "t");
			} else {
				map.put("enabled", "f");
			}
		}

		if (json.has("script")) {
			map.put("script", getString(json, "script"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreUserExtSource(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("userId")) {
			map.put("user_id", getLong(json, "userId"));
		}

		if (json.has("loginExt")) {
			map.put("login_ext", getString(json, "loginExt"));
		}

		if (json.has("extSourcesId")) {
			map.put("ext_sources_id", getLong(json, "extSourcesId"));
		}

		if (json.has("loa")) {
			map.put("loa", getInt(json, "loa"));
		}

		if (json.has("lastAccess")) {
			String last = getString(json, "lastAccess");
			if (last == null) {
				map.put("last_access", null);
			} else {
				//TODO: exception when not in correct format?
				map.put("lastAccess", Timestamp.valueOf(last));
			}
		}

		return map;
	}

	private static Map<String, Object> mapCoreUser(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("firstName")) {
			map.put("first_name", getString(json, "firstName"));
		}

		if (json.has("middleName")) {
			map.put("middle_name", getString(json, "middleName"));
		}

		if (json.has("lastName")) {
			map.put("last_name", getString(json, "lastName"));
		}

		if (json.has("titleBefore")) {
			map.put("title_before", getString(json, "titleBefore"));
		}

		if (json.has("titleAfter")) {
			map.put("titleAfter", getString(json, "titleAfter"));
		}

		if (json.has("serviceAcc")) {
			Boolean param = getBoolean(json, "serviceAcc");
			if (param == null) {
				map.put("service_acc", null);
			} else if (param) {
				map.put("service_acc", "t");
			} else {
				map.put("service_acc", "f");
			}
		}

		if (json.has("sponsoredAcc")) {
			Boolean param = getBoolean(json, "sponsoredAcc");
			if (param == null) {
				map.put("sponsored_acc", null);
			} else if (param) {
				map.put("sponsored_acc", "t");
			} else {
				map.put("sponsored_acc", "f");
			}
		}

		return map;
	}

	private static Map<String, Object> mapCoreVo(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("id")) {
			map.put("id", getLong(json, "id"));
		}

		if (json.has("name")) {
			map.put("name", getString(json, "name"));
		}

		if (json.has("shortName")) {
			map.put("short_name", getString(json, "shortName"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreGroupResource(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("groupId")) {
			map.put("group_id", getLong(json, "groupId"));
		}

		if (json.has("resourceId")) {
			map.put("resource_id", getLong(json, "resourceId"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreMemberGroup(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("memberId")) {
			map.put("member_id", getLong(json, "memberId"));
		}

		if (json.has("groupId")) {
			map.put("group_id", getLong(json, "groupId"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreMemberResource(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("memberId")) {
			map.put("member_id", getLong(json, "memberId"));
		}

		if (json.has("resourceId")) {
			map.put("resource_id", getLong(json, "resourceId"));
		}

		return map;
	}

	private static Map<String, Object> mapCoreUserFacility(JSONObject json) {
		Map<String, Object> map = new HashMap<>();
		if (json.has("userId")) {
			map.put("user_id", getLong(json, "userId"));
		}

		if (json.has("facilityId")) {
			map.put("facility_id", getLong(json, "facilityId"));
		}

		return map;
	}

	private static List<InputAttribute> parseAttributes(JSONArray attrsJsonArray) throws InputParseException {
		List<InputAttribute> attributes = new ArrayList<>();
		if (attrsJsonArray != null) {
			for (int i = 0; i < attrsJsonArray.length(); i++) {
				JSONObject attributeJson = attrsJsonArray.getJSONObject(i);
				InputAttribute attribute = parseAttribute(attributeJson);
				attributes.add(attribute);
			}
		}

		return attributes;
	}

	private static List<String> parseAttrsNames(JSONArray attrsNamesJsonArray) {
		List<String> attrsNames = new ArrayList<>();
		if (attrsNamesJsonArray != null) {
			for (int i = 0; i < attrsNamesJsonArray.length(); i++) {
				attrsNames.add(attrsNamesJsonArray.getString(i));
			}
		}

		return attrsNames;
	}

	private static List<InputEntity> parseInnerInputs(JSONArray entitiesJsonArray) throws IllegalRelationException, InputParseException {
		List<InputEntity> entities = new ArrayList<>();
		if (entitiesJsonArray != null) {
			for (int i = 0; i < entitiesJsonArray.length(); i++) {
				JSONObject entityJson = entitiesJsonArray.getJSONObject(i);
				entities.add(parseInputEntity(entityJson, false));
			}
		}

		return entities;
	}

	private static InputAttribute parseAttribute(JSONObject attributeJson) throws InputParseException {
		String name = attributeJson.getString("name");
		Object value = attributeJson.get("value");

		try {
			return new InputAttribute(name, value);
		} catch (AttributeTypeException e) {
			throw new InputParseException("Input attribute cannot be parsed", e);
		}
	}

	private static String getString(JSONObject json, String key) {
		return (json.get(key) != JSONObject.NULL) ?
				json.getString(key) : null;
	}

	private static Boolean getBoolean(JSONObject json, String key) {
		return (json.get(key) != JSONObject.NULL) ?
				json.getBoolean(key) : null;
	}

	private static Long getLong(JSONObject json, String key) {
		return (json.get(key) != JSONObject.NULL) ?
				json.getLong(key) : null;
	}

	private static Integer getInt(JSONObject json, String key) {
		return (json.get(key) != JSONObject.NULL) ?
				json.getInt(key) : null;
	}
}
