package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Group;
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
public class GroupSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Group EXPECTED1;
	private Group EXPECTED2;
	private Group EXPECTED3;

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
		setUpGroup1();
		setUpGroup2();
		setUpGroup3();
	}

	private void setUpGroup1() throws Exception {
		PerunAttribute group1attr1 = new PerunAttribute("group_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute group1attr2 = new PerunAttribute("group_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute group1attr3 = new PerunAttribute("group_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute group1attr4 = new PerunAttribute("group_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute group1attr5 = new PerunAttribute("group_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute group1attr6 = new PerunAttribute("group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute group1attr7 = new PerunAttribute("group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_attr_str", group1attr1);
		attributes.put("group_attr_int", group1attr2);
		attributes.put("group_attr_bool", group1attr3);
		attributes.put("group_attr_array", group1attr4);
		attributes.put("group_attr_map", group1attr5);
		attributes.put("group_attr_lstring", group1attr6);
		attributes.put("group_attr_larray", group1attr7);

		Long id = 1L;
		String name = "group1";
		String description = "dsc1";
		Long voId = 1L;
		Long parentGroupId = null;

		EXPECTED1 = new Group(id, name, description, voId, parentGroupId, attributes, null);
	}

	private void setUpGroup2() {
		PerunAttribute group2attr1 = new PerunAttribute("group_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute group2attr2 = new PerunAttribute("group_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute group2attr3 = new PerunAttribute("group_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute group2attr4 = new PerunAttribute("group_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute group2attr5 = new PerunAttribute("group_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute group2attr6 = new PerunAttribute("group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute group2attr7 = new PerunAttribute("group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_attr_str", group2attr1);
		attributes.put("group_attr_int", group2attr2);
		attributes.put("group_attr_bool", group2attr3);
		attributes.put("group_attr_array", group2attr4);
		attributes.put("group_attr_map", group2attr5);
		attributes.put("group_attr_lstring", group2attr6);
		attributes.put("group_attr_larray", group2attr7);

		Long id = 2L;
		String name = "group2";
		String description = "dsc2";
		Long voId = 2L;
		Long parentGroupId = null;

		EXPECTED2 = new Group(id, name, description, voId, parentGroupId, attributes, null);
	}

	private void setUpGroup3() {
		PerunAttribute group3attr1 = new PerunAttribute("group_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute group3attr2 = new PerunAttribute("group_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute group3attr3 = new PerunAttribute("group_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute group3attr4 = new PerunAttribute("group_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute group3attr5 = new PerunAttribute("group_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute group3attr6 = new PerunAttribute("group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute group3attr7 = new PerunAttribute("group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("group_attr_str", group3attr1);
		attributes.put("group_attr_int", group3attr2);
		attributes.put("group_attr_bool", group3attr3);
		attributes.put("group_attr_array", group3attr4);
		attributes.put("group_attr_map", group3attr5);
		attributes.put("group_attr_lstring", group3attr6);
		attributes.put("group_attr_larray", group3attr7);

		Long id = 3L;
		String name = "group3";
		String description = "dsc3";
		Long voId = 1L;
		Long parentGroupId = 1L;

		EXPECTED3 = new Group(id, name, description, voId, parentGroupId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findGroupByIdTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"id\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByNameTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"name\" : \"group1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"description\" : \"dsc1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByVoIdTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"voId\" : 2, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findGroupByParentGroupIdTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"parentGroupId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED3, result.get(0));
	}

	@Test
	public void findAllGroupsTest() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findGroupByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_bool\", \"value\" : false}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findGroupByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [ { \"name\" : \"group_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByGroupEntity() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : 3 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByVoEntity() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"vo\", \"id\" : 2 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findGroupByExtSourceEntity() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"ext_source\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByMemberEntity() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByResourceEntity() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByGroupResourceRelation() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group_resource\", \"resourceId\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findGroupByExtMemberGroupRelation() throws Exception {
		String input = "{\"entityName\" : \"group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member_group\", \"memberId\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

