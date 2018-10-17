package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Resource;
import cz.metacentrum.perunsearch.service.SearcherService;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
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
public class ResourceSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static org.springframework.core.io.Resource tablesFile = new ClassPathResource("db_init.sql");
	private static org.springframework.core.io.Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Resource EXPECTED1;
	private Resource EXPECTED2;
	private Resource EXPECTED3;

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
		setUpResource1();
		setUpResource2();
		setUpResource3();
	}

	private void setUpResource1() throws Exception {
		PerunAttribute resource1attr1 = new PerunAttribute("resource_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute resource1attr2 = new PerunAttribute("resource_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute resource1attr3 = new PerunAttribute("resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute resource1attr4 = new PerunAttribute("resource_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute resource1attr5 = new PerunAttribute("resource_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute resource1attr6 = new PerunAttribute("resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute resource1attr7 = new PerunAttribute("resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("resource_attr_str", resource1attr1);
		attributes.put("resource_attr_int", resource1attr2);
		attributes.put("resource_attr_bool", resource1attr3);
		attributes.put("resource_attr_array", resource1attr4);
		attributes.put("resource_attr_map", resource1attr5);
		attributes.put("resource_attr_lstring", resource1attr6);
		attributes.put("resource_attr_larray", resource1attr7);

		Long id = 1L;
		Long facilityId = 1L;
		String name = "resource1";
		String dsc = "dsc1";
		Long voId = 1L;

		EXPECTED1 = new Resource(id, facilityId, name, dsc, voId, attributes, null);
	}

	private void setUpResource2() {
		PerunAttribute resource2attr1 = new PerunAttribute("resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute resource2attr2 = new PerunAttribute("resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute resource2attr3 = new PerunAttribute("resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute resource2attr4 = new PerunAttribute("resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute resource2attr5 = new PerunAttribute("resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute resource2attr6 = new PerunAttribute("resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute resource2attr7 = new PerunAttribute("resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("resource_attr_str", resource2attr1);
		attributes.put("resource_attr_int", resource2attr2);
		attributes.put("resource_attr_bool", resource2attr3);
		attributes.put("resource_attr_array", resource2attr4);
		attributes.put("resource_attr_map", resource2attr5);
		attributes.put("resource_attr_lstring", resource2attr6);
		attributes.put("resource_attr_larray", resource2attr7);

		Long id = 2L;
		Long facilityId = 2L;
		String name = "resource2";
		String dsc = "dsc2";
		Long voId = 2L;

		EXPECTED2 = new Resource(id, facilityId, name, dsc, voId, attributes, null);
	}

	private void setUpResource3() {
		PerunAttribute resource3attr1 = new PerunAttribute("resource_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute resource3attr2 = new PerunAttribute("resource_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute resource3attr3 = new PerunAttribute("resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute resource3attr4 = new PerunAttribute("resource_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute resource3attr5 = new PerunAttribute("resource_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute resource3attr6 = new PerunAttribute("resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute resource3attr7 = new PerunAttribute("resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("resource_attr_str", resource3attr1);
		attributes.put("resource_attr_int", resource3attr2);
		attributes.put("resource_attr_bool", resource3attr3);
		attributes.put("resource_attr_array", resource3attr4);
		attributes.put("resource_attr_map", resource3attr5);
		attributes.put("resource_attr_lstring", resource3attr6);
		attributes.put("resource_attr_larray", resource3attr7);

		Long id = 3L;
		Long facilityId = 3L;
		String name = "resource3";
		String dsc = "dsc3";
		Long voId = 3L;

		EXPECTED3 = new Resource(id, facilityId, name, dsc, voId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findResourceByIdTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"id\" : {\"value\": 1}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByFacilityIdTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"facilityId\" : {\"value\": 1} , \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByNameTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"name\" : {\"value\":\"resource1\"}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"description\" : {\"value\":\"dsc1\"}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByVoIdTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"voId\" : {\"value\": 1}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllResourcesTest() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findResourceByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_bool\", \"value\" : false}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findResourceByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [ { \"name\" : \"resource_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}


	@Test
	public void findResourceByMemberEntity() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByVoEntity() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"vo\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByServiceEntity() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"service\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByFacilityEntity() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"facility\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByGroupEntity() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByMemberResourceRelation() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member_resource\", \"memberId\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findResourceByGroupResourceRelation() throws Exception {
		String input = "{\"entityName\" : \"resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group_resource\", \"groupId\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

