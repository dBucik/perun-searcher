package cz.metacentrum.perunsearch.persistence.models.inputEntities;

import cz.metacentrum.perunsearch.persistence.enums.PerunEntityType;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.Query;
import org.apache.commons.collections4.CollectionUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class InputEntity {

	public static final String NO_VALUE = null;
	public static final String EXACT_MATCH = " = ";
	public static final String LIKE_MATCH = " LIKE ";
	public static final String NULL_MATCH = " IS NULL ";

	private PerunEntityType entityType;
	private boolean isTopLevel;
	private Map<String, Object> core = new HashMap<>();
	private List<InputAttribute> attributes = new ArrayList<>();
	private List<String> attrNames = new ArrayList<>();
	private List<InputEntity> innerInputs = new ArrayList<>();

	public InputEntity(PerunEntityType entityType, boolean isTopLevel, Map<String, Object> core, List<InputAttribute> attributes,
					   List<String> attrNames, List<InputEntity> innerInputs) throws IllegalRelationException {
		this.entityType = entityType;
		this.isTopLevel = isTopLevel;
		this.core.putAll(core);
		this.attributes.addAll(attributes);
		this.attrNames.addAll(attrNames);

		for (InputEntity e: innerInputs) {
			if (!isAllowedInnerInput(e.getEntityType())) {
				throw new IllegalRelationException(e.getEntityType() +
						" does not have a relationship with " + this.entityType);
			}

			this.innerInputs.add(e);
		}
	}

	public abstract boolean isAllowedInnerInput(PerunEntityType entityType);

	public abstract String getAttrNamesTable();

	public abstract Query toQuery(PerunEntityType sourceType) throws IncorrectCoreAttributeTypeException;

	public abstract Query initQuery();

	public abstract String buildSelectFrom(PerunEntityType sourceType, boolean isSimple);

	public PerunEntityType getEntityType() {
		return entityType;
	}

	public boolean isTopLevel() {
		return isTopLevel;
	}

	public Map<String, Object> getCore() {
		return core;
	}

	public List<InputAttribute> getAttributes() {
		return attributes;
	}

	public List<String> getAttrNames() {
		return attrNames;
	}

	public List<InputEntity> getInnerInputs() {
		return innerInputs;
	}

	boolean isSimpleQuery() {
		return (this.attrNames.isEmpty() && this.attributes.isEmpty());
	}

	List<String> mergeNames(List<String> attrNames, List<InputAttribute> attributes) {
		//fetch all attributes
		if (attrNames.size() == 1) {
			String param = attrNames.get(0);
			if ("ALL".equals(param.toUpperCase())) {
				return Collections.emptyList();
			}
		}

		Set<String> names = new HashSet<>(attrNames);
		for (InputAttribute a : attributes) {
			names.add(a.getName());
		}

		return new ArrayList<>(names);
	}

	String resolveMatchOperator(Object o) throws IncorrectCoreAttributeTypeException {
		if (o == null) {
			return NULL_MATCH;
		} else if ((o instanceof String) || (o instanceof Number) || (o instanceof Boolean) || (o instanceof Timestamp)) {
			return EXACT_MATCH;
		} else {
			throw new IncorrectCoreAttributeTypeException("Unsupported core attribute type found for input");
		}
	}

	@Override
	public int hashCode() {
		int hash = this.entityType.hashCode();
		hash *= 31 * this.attributes.hashCode();
		hash *= 31 * this.core.hashCode();
		hash *= 31 * this.attrNames.hashCode();
		hash *= 31 * this.innerInputs.hashCode();

		return hash;
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (! this.getClass().equals(o.getClass())) {
			return false;
		} else {
			InputEntity them = (InputEntity) o;
			return this.entityType == them.entityType
					&& this.isTopLevel == them.isTopLevel
					&& this.core.equals(them.core)
					&& CollectionUtils.isEqualCollection(this.attributes, them.attributes)
					&& CollectionUtils.isEqualCollection(this.innerInputs, them.innerInputs);
		}
	}
}
