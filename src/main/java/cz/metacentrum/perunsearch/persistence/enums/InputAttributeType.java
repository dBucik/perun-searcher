package cz.metacentrum.perunsearch.persistence.enums;

import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;

/**
 * Class represents type of attributes from user input.
 *
 * @author Dominik Frantisek Bucik <bucik@ics.muni.cz>
 */
public enum InputAttributeType {
	STRING,
	INTEGER,
	BOOLEAN,
	ARRAY,
	MAP;

	private static final String STRING_TYPE = "STRING";
	private static final String INTEGER_TYPE = "INTEGER";
	private static final String BOOLEAN_TYPE = "BOOLEAN";
	private static final String ARRAY_TYPE = "ARRAY";
	private static final String MAP_TYPE = "MAP";

	/**
	 * Get type of attribute from provided string.
	 * Allowed values are: STRING, INTEGER, BOOLEAN, ARRAY, MAP.
	 * @param type String representation of type.
	 * @return Parsed type.
	 * @throws AttributeTypeException When the input does not match allowed values.
	 */
	public static InputAttributeType fromString(String type) throws AttributeTypeException {
		switch (type) {
			case STRING_TYPE: return STRING;
			case INTEGER_TYPE: return INTEGER;
			case BOOLEAN_TYPE: return BOOLEAN;
			case ARRAY_TYPE: return ARRAY;
			case MAP_TYPE: return MAP;
		}

		throw new AttributeTypeException("Attribute cannot have type: " + type);
	}
}
