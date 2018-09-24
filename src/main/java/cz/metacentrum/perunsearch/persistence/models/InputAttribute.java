package cz.metacentrum.perunsearch.persistence.models;

import cz.metacentrum.perunsearch.persistence.enums.InputAttributeType;
import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;

/**
 * Attribute obtained as input from user in query.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public class InputAttribute {

	private String name;
	private InputAttributeType type;
	private String value;

	public InputAttribute(String name, String type, String value) throws AttributeTypeException {
		this.name = name;
		this.type = InputAttributeType.fromString(type);
		if (value == null || value.trim().length() == 0) {
			this.value = null;
		} else {
			this.value = value;
		}
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

	public void setValue(String value) {
		this.value = value;
	}
}
