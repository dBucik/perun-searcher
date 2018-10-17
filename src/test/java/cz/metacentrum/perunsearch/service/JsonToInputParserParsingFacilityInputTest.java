package cz.metacentrum.perunsearch.service;

import cz.metacentrum.perunsearch.persistence.models.InputAttribute;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.FacilityInput;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.basic.UserInput;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JsonToInputParserParsingFacilityInputTest {

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
		this.input = "{\n" +
				"  \"entityName\": \"FACILITY\",\n" +
				"  \"id\": {\"value\": 1},\n" +
				"  \"name\": {\"value\":\"test_facility\"},\n" +
				"  \"description\": {\"value\":\"test_description\"},\n" +
				"  \"attributes\": [\n" +
				"    {\n" +
				"      \"name\": \"urn:perun:facility:attribute-def:def:attribute1\",\n" +
				"      \"value\": 1\n" +
				"    }\n" +
				"  ],\n" +
				"  \"attributeNames\": [\n" +
				"    \"attribute2\"\n" +
				"  ],\n" +
				"  \"relations\": [\n" +
				"    {\n" +
				"      \"entityName\": \"USER\"" +
				"    }\n" +
				"  ]\n" +
				"}";
	}

	private void setUpCoreAttributes() {
		Map<String, Object> core = new HashMap<>();
		core.put("id", 1L);
		core.put("name", "test_facility");
		core.put("dsc", "test_description");

		this.core = core;
	}

	private void setUpAttributes() throws Exception {
		List<InputAttribute> attributes = new ArrayList<>();
		InputAttribute attr = new InputAttribute("urn:perun:facility:attribute-def:def:attribute1", false, 1);
		attributes.add(attr);

		this.attributes = attributes;
	}

	private void setUpAttributesNames() {
		List<String> attributeNames = new ArrayList<>();
		attributeNames.add("attribute2");

		this.attributeNames = attributeNames;
	}

	private void setUpInnerInputs() throws Exception {
		List<InputAttribute> core = new ArrayList<>();
		List<InputAttribute> attributes = new ArrayList<>();
		List<String> attributeNames = new ArrayList<>();
		List<InputEntity> inputs = new ArrayList<>();

		UserInput user = new UserInput(false, core, attributes, attributeNames, inputs);

		List<InputEntity> innerInputs = new ArrayList<>();
		innerInputs.add(user);

		this.innerInputs = innerInputs;
	}

	@Test
	public void testFacilityInputClass() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertTrue(entity instanceof FacilityInput);
	}

	@Test
	public void testFacilityInputCore() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertEquals(this.core, entity.getCore());
	}

	@Test
	public void testFacilityInputAttributes() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertEquals(this.attributes, entity.getAttributes());
	}

	@Test
	public void testFacilityInputAttributeNames() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertEquals(this.attributeNames, entity.getAttrNames());
	}

	@Test
	public void testFacilityInputInnerInputs() throws Exception {
		InputEntity entity = JsonToInputParser.parseInput(input);
		assertEquals(this.innerInputs, entity.getInnerInputs());
	}

}