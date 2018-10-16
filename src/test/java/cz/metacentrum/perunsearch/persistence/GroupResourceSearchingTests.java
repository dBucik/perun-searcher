package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.relations.GroupResource;
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
public class GroupResourceSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private GroupResource EXPECTED1;
	private GroupResource EXPECTED2;
	private GroupResource EXPECTED3;

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
		setUpGroupResource1();
		setUpGroupResource2();
		setUpGroupResource3();
	}

	private void setUpGroupResource1() throws Exception {
		PerunAttribute groupResource1attr1 = new PerunAttribute("group_resource_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute groupResource1attr2 = new PerunAttribute("group_resource_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute groupResource1attr3 = new PerunAttribute("group_resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute groupResource1attr4 = new PerunAttribute("group_resource_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute groupResource1attr5 = new PerunAttribute("group_resource_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute groupResource1attr6 = new PerunAttribute("group_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute groupResource1attr7 = new PerunAttribute("group_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_resource_attr_str", groupResource1attr1);
		attributes.put("group_resource_attr_int", groupResource1attr2);
		attributes.put("group_resource_attr_bool", groupResource1attr3);
		attributes.put("group_resource_attr_array", groupResource1attr4);
		attributes.put("group_resource_attr_map", groupResource1attr5);
		attributes.put("group_resource_attr_lstring", groupResource1attr6);
		attributes.put("group_resource_attr_larray", groupResource1attr7);

		Long groupId = 1L;
		Long resourceId = 1L;

		EXPECTED1 = new GroupResource(groupId, resourceId, attributes, null);
	}

	private void setUpGroupResource2() {
		PerunAttribute groupResource2attr1 = new PerunAttribute("group_resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute groupResource2attr2 = new PerunAttribute("group_resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute groupResource2attr3 = new PerunAttribute("group_resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute groupResource2attr4 = new PerunAttribute("group_resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute groupResource2attr5 = new PerunAttribute("group_resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute groupResource2attr6 = new PerunAttribute("group_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute groupResource2attr7 = new PerunAttribute("group_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_resource_attr_str", groupResource2attr1);
		attributes.put("group_resource_attr_int", groupResource2attr2);
		attributes.put("group_resource_attr_bool", groupResource2attr3);
		attributes.put("group_resource_attr_array", groupResource2attr4);
		attributes.put("group_resource_attr_map", groupResource2attr5);
		attributes.put("group_resource_attr_lstring", groupResource2attr6);
		attributes.put("group_resource_attr_larray", groupResource2attr7);

		Long groupId = 2L;
		Long resourceId = 2L;

		EXPECTED2 = new GroupResource(groupId, resourceId, attributes, null);
	}

	private void setUpGroupResource3() {
		PerunAttribute groupResource3attr1 = new PerunAttribute("group_resource_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute groupResource3attr2 = new PerunAttribute("group_resource_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute groupResource3attr3 = new PerunAttribute("group_resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute groupResource3attr4 = new PerunAttribute("group_resource_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute groupResource3attr5 = new PerunAttribute("group_resource_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute groupResource3attr6 = new PerunAttribute("group_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute groupResource3attr7 = new PerunAttribute("group_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_resource_attr_str", groupResource3attr1);
		attributes.put("group_resource_attr_int", groupResource3attr2);
		attributes.put("group_resource_attr_bool", groupResource3attr3);
		attributes.put("group_resource_attr_array", groupResource3attr4);
		attributes.put("group_resource_attr_map", groupResource3attr5);
		attributes.put("group_resource_attr_lstring", groupResource3attr6);
		attributes.put("group_resource_attr_larray", groupResource3attr7);

		Long groupId = 3L;
		Long resourceId = 3L;

		EXPECTED3 = new GroupResource(groupId, resourceId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findGroupResourceRelationByGroupIdTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"groupId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByResourceIdTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"resourceId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllGroupResourceRelationsTest() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findGroupResourceRelationByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_bool\", \"value\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [ { \"name\" : \"group_resource_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceRelationByGroupEntity() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupResourceByResourceEntity() throws Exception {
		String input = "{\"entityName\" : \"group_resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

