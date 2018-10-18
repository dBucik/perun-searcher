package cz.metacentrum.perunsearch.service;

import cz.metacentrum.perunsearch.persistence.enums.InputAttributeType;
import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.ExtSourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.FacilityInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.GroupInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.relations.GroupResourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.HostInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.relations.MemberGroupInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.MemberInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.relations.MemberResourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.ResourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.ServiceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.UserExtSourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.relations.UserFacilityInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.UserInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.VoInput;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JsonToInputParser {

	public static InputEntity parseInput(String inputString) throws InputParseException, IllegalRelationException, AttributeTypeException {
		JSONObject json = new JSONObject(inputString);
		return parseInputEntity(json, true);
	}

	private static InputEntity parseInputEntity(JSONObject json, boolean isTopLevel) throws InputParseException, IllegalRelationException, AttributeTypeException {
		if (! json.has("entityName") || json.isNull("entityName")) {
			throw new InputParseException("Input does not have specified entity");
		}

		String entity = json.getString("entityName");
		boolean isSimpleEntity = PerunEntityType.isSimpleEntity(entity);

		JSONArray attributesJsonArray = (isSimpleEntity) ? null : json.optJSONArray("attributes");
		JSONArray attrsNamesJsonArray = (isSimpleEntity) ? null : json.optJSONArray("attributeNames");
		JSONArray entitiesJsonArray = json.optJSONArray("relations");

		List<InputAttribute> attributes = parseAttributes(attributesJsonArray);
		List<String> attrsNames = parseAttrsNames(attrsNamesJsonArray);
		List<InputEntity> entities = parseInnerInputs(entitiesJsonArray);

		return resolveEntity(isTopLevel, json, entity, attributes, attrsNames, entities);
	}

	private static InputEntity resolveEntity(boolean isTopLevel, JSONObject json, String entity, List<InputAttribute> attributes,
											 List<String> attrsNames, List<InputEntity> entities) throws IllegalRelationException, AttributeTypeException {
		switch (entity.toUpperCase()) {
			case "EXT_SOURCE": {
				List<InputAttribute> core = mapCoreExtSource(json);
				return new ExtSourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "FACILITY": {
				List<InputAttribute> core = mapCoreFacility(json);
				return new FacilityInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "GROUP": {
				List<InputAttribute> core = mapCoreGroup(json);
				return new GroupInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "HOST": {
				List<InputAttribute> core = mapCoreHost(json);
				return new HostInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER": {
				List<InputAttribute> core = mapCoreMember(json);
				return new MemberInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "RESOURCE": {
				List<InputAttribute> core = mapCoreResource(json);
				return new ResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "SERVICE": {
				List<InputAttribute> core = mapCoreService(json);
				return new ServiceInput(isTopLevel, core, entities);
			}
			case "USER_EXT_SOURCE": {
				List<InputAttribute> core = mapCoreUserExtSource(json);
				return new UserExtSourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "USER": {
				List<InputAttribute> core = mapCoreUser(json);
				return new UserInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "VO": {
				List<InputAttribute> core = mapCoreVo(json);
				return new VoInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "GROUP_RESOURCE": {
				List<InputAttribute> core = mapCoreGroupResource(json);
				return new GroupResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER_GROUP": {
				List<InputAttribute> core = mapCoreMemberGroup(json);
				return new MemberGroupInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "MEMBER_RESOURCE": {
				List<InputAttribute> core = mapCoreMemberResource(json);
				return new MemberResourceInput(isTopLevel, core, attributes, attrsNames, entities);
			}
			case "USER_FACILITY": {
				List<InputAttribute> core = mapCoreUserFacility(json);
				return new UserFacilityInput(isTopLevel, core, attributes, attrsNames, entities);
			}
		}

		return null;
	}

	private static List<InputAttribute> mapCoreExtSource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("name")) {
			list.add(getInputAttrWithString(json, "name", "name"));
		}

		if (json.has("type")) {
			list.add(getInputAttrWithString(json, "type", "type"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreFacility(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("name")) {
			list.add(getInputAttrWithString(json, "name", "name"));
		}

		if (json.has("description")) {
			list.add(getInputAttrWithString(json, "description", "dsc"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreGroup(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("name")) {
			list.add(getInputAttrWithString(json, "name", "name"));
		}

		if (json.has("description")) {
			list.add(getInputAttrWithString(json, "description", "dsc"));
		}

		if (json.has("voId")) {
			list.add(getInputAttrWithLong(json, "voId", "vo_id"));
		}

		if (json.has("parentGroupId")) {
			list.add(getInputAttrWithLong(json, "parentGroupId", "parent_group_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreHost(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("hostname")) {
			list.add(getInputAttrWithString(json, "hostname", "hostname"));
		}

		if (json.has("facilityId")) {
			list.add(getInputAttrWithLong(json, "facilityId", "facility_id"));
		}

		if (json.has("description")) {
			list.add(getInputAttrWithString(json, "description", "dsc"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreMember(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("userId")) {
			list.add(getInputAttrWithLong(json, "userId", "user_id"));
		}

		if (json.has("voId")) {
			list.add(getInputAttrWithLong(json, "voId", "vo_id"));
		}

		if (json.has("sponsored")) {
			list.add(getInputAttrWithBoolean(json, "sponsored", "sponsored"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreResource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("facilityId")) {
			list.add(getInputAttrWithLong(json, "facilityId", "facility_id"));
		}

		if (json.has("name")) {
			list.add(getInputAttrWithString(json, "name", "name"));
		}

		if (json.has("description")) {
			list.add(getInputAttrWithString(json, "description", "dsc"));
		}

		if (json.has("voId")) {
			list.add(getInputAttrWithLong(json, "voId", "vo_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreService(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("name")) {
			list.add(getInputAttrWithString(json, "name", "name"));
		}

		if (json.has("description")) {
			list.add(getInputAttrWithString(json, "description", "description"));
		}

		if (json.has("delay")) {
			list.add(getInputAttrWithInt(json, "delay", "delay"));
		}

		if (json.has("recurrence")) {
			list.add(getInputAttrWithInt(json, "recurrence", "recurrence"));
		}

		if (json.has("enabled")) {
			InputAttribute attr = getInputAttrWithBoolean(json, "enabled", "enabled");
			list.add(transformBooleanToString(attr));
		}

		if (json.has("script")) {
			list.add(getInputAttrWithString(json, "script", "script"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreUserExtSource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("userId")) {
			list.add(getInputAttrWithLong(json, "userId", "user_id"));
		}

		if (json.has("loginExt")) {
			list.add(getInputAttrWithString(json, "loginExt", "login_ext"));
		}

		if (json.has("extSourcesId")) {
			list.add(getInputAttrWithLong(json, "extSourcesId", "ext_sources_id"));
		}

		if (json.has("loa")) {
			list.add(getInputAttrWithInt(json, "loa", "loa"));
		}

		if (json.has("lastAccess")) {
			list.add(getInputAttrWithTimestamp(json, "lastAccess", "last_access"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreUser(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("firstName")) {
			list.add(getInputAttrWithString(json, "firstName", "first_name"));
		}

		if (json.has("middleName")) {
			list.add(getInputAttrWithString(json, "middleName", "middle_name"));
		}

		if (json.has("lastName")) {
			list.add(getInputAttrWithString(json, "lastName", "last_name"));
		}

		if (json.has("titleBefore")) {
			list.add(getInputAttrWithString(json, "titleBefore", "title_before"));
		}

		if (json.has("titleAfter")) {
			list.add(getInputAttrWithString(json, "titleAfter", "title_after"));
		}

		if (json.has("serviceAcc")) {
			InputAttribute attr = getInputAttrWithBoolean(json, "serviceAcc", "service_acc");
			list.add(transformBooleanToString(attr));
		}

		if (json.has("sponsoredAcc")) {
			InputAttribute attr = getInputAttrWithBoolean(json, "sponsoredAcc", "sponsored_acc");
			list.add(transformBooleanToString(attr));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreVo(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("id")) {
			list.add(getInputAttrWithLong(json, "id", "id"));
		}

		if (json.has("name")) {
			list.add(getInputAttrWithString(json, "name", "name"));
		}

		if (json.has("shortName")) {
			list.add(getInputAttrWithString(json, "shortName", "short_name"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreGroupResource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("groupId")) {
			list.add(getInputAttrWithLong(json, "groupId", "group_id"));
		}

		if (json.has("resourceId")) {
			list.add(getInputAttrWithLong(json, "resourceId", "resource_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreMemberGroup(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("memberId")) {
			list.add(getInputAttrWithLong(json, "memberId", "member_id"));
		}

		if (json.has("groupId")) {
			list.add(getInputAttrWithLong(json, "groupId", "group_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreMemberResource(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("memberId")) {
			list.add(getInputAttrWithLong(json, "memberId", "member_id"));
		}

		if (json.has("resourceId")) {
			list.add(getInputAttrWithLong(json, "resourceId", "resource_id"));
		}

		return list;
	}

	private static List<InputAttribute> mapCoreUserFacility(JSONObject json) throws AttributeTypeException {
		List<InputAttribute> list = new ArrayList<>();
		if (json.has("userId")) {
			list.add(getInputAttrWithLong(json, "userId", "user_id"));
		}

		if (json.has("facilityId")) {
			list.add(getInputAttrWithLong(json, "facilityId", "facility_id"));
		}

		return list;
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

	private static List<InputEntity> parseInnerInputs(JSONArray entitiesJsonArray) throws IllegalRelationException, InputParseException, AttributeTypeException {
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
		boolean match = attributeJson.optBoolean("matchLike", false);
		Object value = attributeJson.get("value");

		try {
			return new InputAttribute(name, match, value);
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

	private static InputAttribute getInputAttrWithLong(JSONObject json, String key, String keyInTable) throws AttributeTypeException {
		JSONObject attr = json.getJSONObject(key);
		boolean match = attr.optBoolean("matchLike", false);
		Long value = getLong(attr, "value");

		return new InputAttribute(keyInTable, match, value);
	}

	private static InputAttribute getInputAttrWithString(JSONObject json, String key, String keyInTable) throws AttributeTypeException {
		JSONObject attr = json.getJSONObject(key);
		boolean match = attr.optBoolean("matchLike", false);
		String value = getString(attr, "value");

		return new InputAttribute(keyInTable, match, value);
	}

	private static InputAttribute getInputAttrWithBoolean(JSONObject json, String key, String keyInTable) throws AttributeTypeException {
		JSONObject attr = json.getJSONObject(key);
		boolean match = attr.optBoolean("matchLike", false);
		Boolean value = getBoolean(attr, "value");

		return new InputAttribute(keyInTable, match, value);
	}

	private static InputAttribute getInputAttrWithInt(JSONObject json, String key, String keyInTable) throws AttributeTypeException {
		JSONObject attr = json.getJSONObject(key);
		boolean match = attr.optBoolean("matchLike", false);
		Integer value = getInt(attr, "value");

		return new InputAttribute(keyInTable, match, value);
	}

	private static InputAttribute getInputAttrWithTimestamp(JSONObject json, String key, String keyInTable) throws AttributeTypeException {
		JSONObject attr = json.getJSONObject(key);
		boolean match = attr.optBoolean("matchLike", false);
		String value = getString(attr, "value");
		if (!match && value != null) {
			Timestamp stamp = Timestamp.valueOf(value);
			return new InputAttribute(keyInTable, match, stamp);

		}

		return new InputAttribute(keyInTable, match, value);
	}

	private static InputAttribute transformBooleanToString(InputAttribute attr) {
		if (attr.valueAsBoolean() == null) {
			attr.setType(InputAttributeType.STRING);
			attr.setValue(null);
		} else if (attr.valueAsBoolean()) {
			attr.setType(InputAttributeType.STRING);
			attr.setValue('1');
		} else {
			attr.setType(InputAttributeType.STRING);
			attr.setValue('0');
		}

		return attr;
	}
}
