package cz.metacentrum.perunsearch.persistence.data;

import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.mappers.ExtSourceMapper;
import cz.metacentrum.perunsearch.persistence.mappers.FacilityMapper;
import cz.metacentrum.perunsearch.persistence.mappers.GroupMapper;
import cz.metacentrum.perunsearch.persistence.mappers.GroupResourceMapper;
import cz.metacentrum.perunsearch.persistence.mappers.HostMapper;
import cz.metacentrum.perunsearch.persistence.mappers.MemberGroupMapper;
import cz.metacentrum.perunsearch.persistence.mappers.MemberMapper;
import cz.metacentrum.perunsearch.persistence.mappers.MemberResourceMapper;
import cz.metacentrum.perunsearch.persistence.mappers.ResourceMapper;
import cz.metacentrum.perunsearch.persistence.mappers.ServiceMapper;
import cz.metacentrum.perunsearch.persistence.mappers.UserExtSourceMapper;
import cz.metacentrum.perunsearch.persistence.mappers.UserFacilityMapper;
import cz.metacentrum.perunsearch.persistence.mappers.UserMapper;
import cz.metacentrum.perunsearch.persistence.mappers.VoMapper;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.Query;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunRichEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.FACILITY;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.GROUP;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.GROUP_RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER_GROUP;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.MEMBER_RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.RESOURCE;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER;
import static cz.metacentrum.perunsearch.persistence.enums.PerunEntityType.USER_FACILITY;

public class PerunEntitiesDAOImpl implements PerunEntitiesDAO {

	private NamedParameterJdbcTemplate template;
	private static final ExtSourceMapper EXT_SOURCE_MAPPER = new ExtSourceMapper();
	private static final FacilityMapper FACILITY_MAPPER = new FacilityMapper();
	private static final GroupMapper GROUP_MAPPER  = new GroupMapper();
	private static final HostMapper HOST_MAPPER = new HostMapper();
	private static final MemberMapper MEMBER_MAPPER  = new MemberMapper();
	private static final ResourceMapper RESOURCE_MAPPER = new ResourceMapper();
	private static final ServiceMapper SERVICE_MAPPER = new ServiceMapper();
	private static final UserExtSourceMapper USER_EXT_SOURCE_MAPPER = new UserExtSourceMapper();
	private static final UserMapper USER_MAPPER = new UserMapper();
	private static final VoMapper VO_MAPPER = new VoMapper();
	private static final GroupResourceMapper GROUP_RESOURCE_MAPPER = new GroupResourceMapper();
	private static final MemberGroupMapper MEMBER_GROUP_MAPPER = new MemberGroupMapper();
	private static final MemberResourceMapper MEMBER_RESOURCE_MAPPER = new MemberResourceMapper();
	private static final UserFacilityMapper USER_FACILITY_MAPPER = new UserFacilityMapper();


	public void setTemplate(JdbcTemplate template) {
		if (template == null) {
			throw new IllegalArgumentException("Attempting to set null JDBC template");
		} else if (template.getDataSource() == null) {
			throw new IllegalArgumentException("Attempting to set JDBC template without dataSource");
		}

		this.template = new NamedParameterJdbcTemplate(template);
	}

	@Override
	public List<PerunEntity> executeQuery(Query query) {
		if (PerunEntityType.isRelation(query.getEntityType())) {
			Set<Long> ids1 = getIdsForPrimaryKey(query.getPrimaryKey(), query.getInnerQueries());
			Set<Long> ids2 = getIdsForSecondaryKey(query.getSecondaryKey(), query.getInnerQueries());

			if (query.getInnerQueries() != null && !query.getInnerQueries().isEmpty()
					&& (ids1.isEmpty() && ids2.isEmpty())) {
				return Collections.emptyList();
			}

			query.setIds(query.getPrimaryKey(), ids1, query.getSecondaryKey(), ids2);
		} else {
			Set<Long> ids = new HashSet<>();
			for (Query inner: query.getInnerQueries()) {
				ids.addAll(this.executeQueryForIds(inner));
			}
			if (query.getInnerQueries() != null && !query.getInnerQueries().isEmpty() && ids.isEmpty()) {
				return Collections.emptyList();
			}

			query.setIds(ids);
		}

		String queryString = query.getQueryString();
		MapSqlParameterSource params = query.getParameters();

		List<PerunEntity> result = new ArrayList<>();
		switch (query.getEntityType()) {
			case EXT_SOURCE: result.addAll(template.query(queryString, params, EXT_SOURCE_MAPPER));
				break;
			case FACILITY: result.addAll(template.query(queryString, params, FACILITY_MAPPER));
				break;
			case GROUP: result.addAll(template.query(queryString, params, GROUP_MAPPER));
				break;
			case HOST: result.addAll(template.query(queryString, params, HOST_MAPPER));
				break;
			case MEMBER: result.addAll(template.query(queryString, params, MEMBER_MAPPER));
				break;
			case RESOURCE: result.addAll(template.query(queryString, params, RESOURCE_MAPPER));
				break;
			case SERVICE: result.addAll(template.query(queryString, params, SERVICE_MAPPER));
				break;
			case USER: result.addAll(template.query(queryString, params, USER_MAPPER));
				break;
			case USER_EXT_SOURCE: result.addAll(template.query(queryString, params, USER_EXT_SOURCE_MAPPER));
				break;
			case VO: result.addAll(template.query(queryString, params, VO_MAPPER));
				break;
			case GROUP_RESOURCE: result.addAll(template.query(queryString, params, GROUP_RESOURCE_MAPPER));
				break;
			case MEMBER_GROUP: result.addAll(template.query(queryString, params, MEMBER_GROUP_MAPPER));
				break;
			case MEMBER_RESOURCE: result.addAll(template.query(queryString, params, MEMBER_RESOURCE_MAPPER));
				break;
			case USER_FACILITY: result.addAll(template.query(queryString, params, USER_FACILITY_MAPPER));
				break;
		}

		return result.stream()
				.filter(entity -> !(entity instanceof PerunRichEntity) || hasAllAttributes(((PerunRichEntity) entity).getAttributes(), query.getInputAttributes()))
				.collect(Collectors.toList());
	}

	private Set<Long> getIdsForPrimaryKey(String primaryKey, List<Query> innerQueries) {
		Set<Long> result = new HashSet<>();
		List<Query> queries = filterQueriesPrimaryKey(primaryKey, innerQueries);

		for (Query inner: queries) {
			result.addAll(this.executeQueryForIds(inner));
		}

		return result;
	}

	private List<Query> filterQueriesPrimaryKey(String primaryKey, List<Query> innerQueries) {
		List<PerunEntityType> lookFor = new ArrayList<>();
		switch (primaryKey) {
			case "group_id" : lookFor = Arrays.asList(GROUP, GROUP_RESOURCE, MEMBER_GROUP);
				break;
			case "member_id" : lookFor = Arrays.asList(MEMBER, MEMBER_GROUP, MEMBER_RESOURCE);
				break;
			case "user_id" : lookFor = Arrays.asList(USER, USER_FACILITY);
				break;
		}

		List<Query> queriesToExecute = new ArrayList<>();
		for (Query q: innerQueries) {
			if (lookFor.contains(q.getEntityType())) {
				queriesToExecute.add(q);
			}
		}

		return queriesToExecute;
	}

	private Set<Long> getIdsForSecondaryKey(String secondaryKey, List<Query> innerQueries) {
		Set<Long> result = new HashSet<>();
		List<Query> queries = filterQueriesSecondaryKey(secondaryKey, innerQueries);

		for (Query inner: queries) {
			result.addAll(this.executeQueryForIds(inner));
		}

		return result;
	}

	private List<Query> filterQueriesSecondaryKey(String secondaryKey, List<Query> innerQueries) {
		List<PerunEntityType> lookFor = new ArrayList<>();
		switch (secondaryKey) {
			case "resource_id" : lookFor = Arrays.asList(RESOURCE, GROUP_RESOURCE, MEMBER_RESOURCE);
				break;
			case "group_id" : lookFor = Arrays.asList(GROUP, MEMBER_GROUP);
				break;
			case "facility_id" : lookFor = Arrays.asList(FACILITY, USER_FACILITY);
				break;
		}

		List<Query> queriesToExecute = new ArrayList<>();
		for (Query q: innerQueries) {
			if (lookFor.contains(q.getEntityType())) {
				queriesToExecute.add(q);
			}
		}

		return queriesToExecute;
	}

	@Override
	public List<Long> executeQueryForIds(Query query) {
		List<PerunEntity> entities = executeQuery(query);

		return entities.stream()
				.filter(entity -> !(entity instanceof PerunRichEntity) || hasAllAttributes(((PerunRichEntity) entity).getAttributes(), query.getInputAttributes()))
				.map(PerunEntity::getForeignId)
				.collect(Collectors.toList());
	}

	private boolean hasAllAttributes(Map<String, PerunAttribute> attributes, List<InputAttribute> inputAttributes) {
		for (InputAttribute a: inputAttributes) {
			if (! attributes.containsKey(a.getName())) {
				return false;
			}

			PerunAttribute attribute = attributes.get(a.getName());
			if (!compareAttributeTypes(a, attribute) ||!compareAttributeValues(a, attribute)) {
				return false;
			}
		}

		return true;
	}

	private boolean compareAttributeTypes(InputAttribute a, PerunAttribute attribute) {
		switch (a.getType()) {
			case STRING: return attribute.getType() == PerunAttributeType.STRING
					|| attribute.getType() == PerunAttributeType.LARGE_STRING;
			case INTEGER: return attribute.getType() == PerunAttributeType.INTEGER;
			case BOOLEAN: return attribute.getType() == PerunAttributeType.BOOLEAN;
			case ARRAY: return attribute.getType() == PerunAttributeType.ARRAY
					|| attribute.getType() == PerunAttributeType.LARGE_ARRAY_LIST;
			case MAP: return attribute.getType() == PerunAttributeType.MAP;
			case TIMESTAMP: return attribute.getType() == PerunAttributeType.STRING;
		}

		return false;
	}

	private boolean compareAttributeValues(InputAttribute a1, PerunAttribute a2) {
		if (a1.isLikeMatch()) {
			return compareValuesLike(a1, a2.stringValue());
		}

		switch (a1.getType()) {
			case STRING: {
				String value1 = a1.valueAsString();
				String value2 = a2.valueAsString();
				return compareStringValues(value1, value2);
			}
			case INTEGER: {
				Integer value1 = a1.valueAsInt();
				Integer value2 = a2.valueAsInt();
				return compareIntValues(value1, value2);
			}
			case BOOLEAN: {
				Boolean value1 = a1.valueAsBoolean();
				Boolean value2 = a2.valueAsBoolean();
				return compareBooleanValues(value1, value2);
			}
			case ARRAY: {
				List<String> value1 = a1.valueAsList();
				List<String> value2 = a2.valueAsList();
				return compareListValues(value1, value2);
			}
			case MAP: {
				Map<String, String> value1 = a1.valueAsMap();
				Map<String, String> value2 = a2.valueAsMap();
				return compareMapValues(value1, value2);
			}
		}

		return false;
	}

	private boolean compareValuesLike(InputAttribute a1, String a2) {
		switch (a1.getType()) {
			case STRING: {
				return a2.contains(a1.valueAsString());
			}
			case INTEGER: {
				return a2.contains(a1.valueAsInt().toString());
			}
			case BOOLEAN: {
				return a2.contains(a1.valueAsBoolean().toString());
			}
			case ARRAY: {
				List<String> value1 = a1.valueAsList();
				for (String subval: value1) {
					if (!a2.contains(subval)) {
						return false;
					}
				}
				return true;
			}
			case MAP: {
				Map<String, String> value1 = a1.valueAsMap();
				for (Map.Entry<String, String> subval: value1.entrySet()) {
					String key = subval.getKey();
					String value = subval.getValue();
					if (!a2.contains(key) || !a2.contains(value)) {
						return false;
					}
				}
				return true;
			}
		}

		return false;
	}

	private boolean compareMapValues(Map<String, String> value1, Map<String, String> value2) {
		if (value1 != null && value2 != null) {
			return value1.entrySet().containsAll(value2.entrySet());
		}

		return value1 == value2;
	}

	private boolean compareListValues(List<String> value1, List<String> value2) {
		if (value1 != null) {
			return value1.containsAll(value2);
		}

		return value2 == null;
	}

	private boolean compareBooleanValues(Boolean value1, Boolean value2) {
		if (value1 != null) {
			return value1.equals(value2);
		}

		return value2 == null;
	}

	private boolean compareIntValues(Integer value1, Integer value2) {
		if (value1 != null) {
			return value1.equals(value2);
		}

		return value2 == null;
	}

	private boolean compareStringValues(String value1, String value2) {
		if (value1 != null) {
			return value1.equals(value2);
		}

		return value2 == null;
	}
}
