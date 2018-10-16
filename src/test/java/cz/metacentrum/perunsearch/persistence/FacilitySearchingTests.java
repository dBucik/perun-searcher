package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Facility;
import cz.metacentrum.perunsearch.service.SearcherService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
@SpringBootTest
public class FacilitySearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Facility EXPECTED1;
	private Facility EXPECTED2;
	private Facility EXPECTED3;

	@BeforeClass
	public static void setUpDatabaseTables() throws Exception {
		dataSource = pg.getEmbeddedPostgres().getPostgresDatabase();
		DBUtils.executeSqlScript(dataSource, tablesFile.getFile());
		DBUtils.executeSqlScript(dataSource, dataFile.getFile());
		template = new JdbcTemplate(dataSource);
		dao = new PerunEntitiesDAOImpl();
		((PerunEntitiesDAOImpl) dao).setTemplate(template);
		service = new SearcherService(dao);
	}

	@Before
	public void setUp() throws Exception {
		setUpFacility1();
		setUpFacility2();
		setUpFacility3();
	}

	private void setUpFacility1() throws Exception {
		PerunAttribute facility1attr1 = new PerunAttribute("facility_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute facility1attr2 = new PerunAttribute("facility_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute facility1attr3 = new PerunAttribute("facility_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute facility1attr4 = new PerunAttribute("facility_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute facility1attr5 = new PerunAttribute("facility_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute facility1attr6 = new PerunAttribute("facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute facility1attr7 = new PerunAttribute("facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("facility_attr_str", facility1attr1);
		attributes.put("facility_attr_int", facility1attr2);
		attributes.put("facility_attr_bool", facility1attr3);
		attributes.put("facility_attr_array", facility1attr4);
		attributes.put("facility_attr_map", facility1attr5);
		attributes.put("facility_attr_lstring", facility1attr6);
		attributes.put("facility_attr_larray", facility1attr7);

		Long id = 1L;
		String name = "facility1";
		String description = "dsc1";

		EXPECTED1 = new Facility(id, name, description, attributes, null);
	}

	private void setUpFacility2() {
		PerunAttribute facility2attr1 = new PerunAttribute("facility_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute facility2attr2 = new PerunAttribute("facility_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute facility2attr3 = new PerunAttribute("facility_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute facility2attr4 = new PerunAttribute("facility_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute facility2attr5 = new PerunAttribute("facility_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute facility2attr6 = new PerunAttribute("facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute facility2attr7 = new PerunAttribute("facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("facility_attr_str", facility2attr1);
		attributes.put("facility_attr_int", facility2attr2);
		attributes.put("facility_attr_bool", facility2attr3);
		attributes.put("facility_attr_array", facility2attr4);
		attributes.put("facility_attr_map", facility2attr5);
		attributes.put("facility_attr_lstring", facility2attr6);
		attributes.put("facility_attr_larray", facility2attr7);

		Long id = 2L;
		String name = "facility2";
		String description = "dsc2";

		EXPECTED2 = new Facility(id, name, description, attributes, null);
	}

	private void setUpFacility3() {
		PerunAttribute facility3attr1 = new PerunAttribute("facility_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute facility3attr2 = new PerunAttribute("facility_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute facility3attr3 = new PerunAttribute("facility_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute facility3attr4 = new PerunAttribute("facility_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute facility3attr5 = new PerunAttribute("facility_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute facility3attr6 = new PerunAttribute("facility_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute facility3attr7 = new PerunAttribute("facility_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("facility_attr_str", facility3attr1);
		attributes.put("facility_attr_int", facility3attr2);
		attributes.put("facility_attr_bool", facility3attr3);
		attributes.put("facility_attr_array", facility3attr4);
		attributes.put("facility_attr_map", facility3attr5);
		attributes.put("facility_attr_lstring", facility3attr6);
		attributes.put("facility_attr_larray", facility3attr7);

		Long id = 3L;
		String name = "facility3";
		String description = "dsc3";

		EXPECTED3 = new Facility(id, name, description, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findFacilityByIdTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"id\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByNameTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"name\" : \"facility1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"description\" : \"dsc1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllFacilitiesTest() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findFacilityByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_bool\", \"value\" : false}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findFacilityByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [ { \"name\" : \"facility_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByUserEntity() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByResourceEntity() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : 2 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findFacilityByHostEntity() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"host\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findFacilityByUserFacilityRelation() throws Exception {
		String input = "{\"entityName\" : \"facility\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user_facility\", \"userId\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

}

