package cz.metacentrum.perunsearch.service;

import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.ExtSourceInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.VoInput;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class JsonToInputParserParsingExtSourceInputTest {

	private String input;
	private Map<String, Object> core;
	private List<InputAttribute> attributes;
	private List<String> attributeNames;
	private List<InputEntity> innerInputs;

	@Before
	public void setUp() throws Exception {
		setUpInputString();
		setUpCoreAttributes();
		setUpAttributes();
		setUpAttributesNames();
		setUpInnerInputs();
	}

	private void setUpInputString() {
		input = "{\n" +
				"  \"entityName\": \"EXT_SOURCE\",\n" +
				"  \"id\": 1,\n" +
				"  \"name\": \"test_ext_source\",\n" +
				"  \"type\": \"test_type\",\n" +
				"  \"attributes\": [\n" +
				"    {\n" +
				"      \"name\": \"urn:perun:ext_source:attribute-def:def:attribute1\",\n" +
				"      \"value\": 1\n" +
				"    }\n" +
				"  ],\n" +
				"  \"attributeNames\": [\n" +
				"    \"attribute2\"\n" +
				"  ],\n" +
				"  \"relations\": [\n" +
				"    {\n" +
				"      \"entityName\": \"VO\"\n" +
				"    }\n" +
				"  ]\n" +
				"}";
	}

	private void setUpCoreAttributes() {
		Map<String, Object> core = new HashMap<>();
		core.put("id", 1L);
		core.put("name", "test_ext_source");
		core.put("type", "test_type");

		this.core = core;
	}

	private void setUpAttributes() throws Exception {
		List<InputAttribute> attributes = new ArrayList<>();
		InputAttribute attr = new InputAttribute("urn:perun:ext_source:attribute-def:def:attribute1", 1);
		attributes.add(attr);

		this.attributes = attributes;
	}

	private void setUpAttributesNames() {
		List<String> attributeNames = new ArrayList<>();
		attributeNames.add("attribute2");

		this.attributeNames = attributeNames;
	}

	private void setUpInnerInputs() throws Exception {
		Map<String, Object> core = new HashMap<>();
		List<InputAttribute> attributes = new ArrayList<>();
		List<String> attributeNames = new ArrayList<>();
		List<InputEntity> inputs = new ArrayList<>();

		VoInput vo = new VoInput(false, core, attributes, attributeNames, inputs);

		List<InputEntity> innerInputs = new ArrayList<>();
		innerInputs.add(vo);

		this.innerInputs = innerInputs;
	}

	@Test
	public void testExtSourceInputClass() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(entity instanceof ExtSourceInput);
	}

	@Test
	public void testExtSourceInputCore() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertEquals(this.core, entity.getCore());
	}

	@Test
	public void testExtSourceInputAttributes() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertEquals(this.attributes, entity.getAttributes());
	}

	@Test
	public void testExtSourceInputAttributeNames() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertEquals(this.attributeNames, entity.getAttrNames());
	}

	@Test
	public void testExtSourceInputInnerInputs() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertEquals(this.innerInputs, entity.getInnerInputs());
	}

}