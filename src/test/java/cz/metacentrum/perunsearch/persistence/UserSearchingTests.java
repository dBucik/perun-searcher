package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.User;
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
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
@SpringBootTest
public class UserSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private User EXPECTED1;
	private User EXPECTED2;
	private User EXPECTED3;

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
	public void setUp() {
		setUpUser1();
		setUpUser2();
		setUpUser3();
	}

	private void setUpUser1() {
		PerunAttribute user1attr1 = new PerunAttribute("user_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute user1attr2 = new PerunAttribute("user_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute user1attr3 = new PerunAttribute("user_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute user1attr4 = new PerunAttribute("user_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute user1attr5 = new PerunAttribute("user_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute user1attr6 = new PerunAttribute("user_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute user1attr7 = new PerunAttribute("user_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_attr_str", user1attr1);
		attributes.put("user_attr_int", user1attr2);
		attributes.put("user_attr_bool", user1attr3);
		attributes.put("user_attr_array", user1attr4);
		attributes.put("user_attr_map", user1attr5);
		attributes.put("user_attr_lstring", user1attr6);
		attributes.put("user_attr_larray", user1attr7);

		Long id = 1L;
		String firstName = "first_name1";
		String middleName = "middle_name1";
		String lastName = "last_name1";
		String title_before = "title_before1";
		String title_after = "title_after1";
		boolean service = true;
		boolean sponsored = false;

		EXPECTED1 = new User(id, firstName, middleName, lastName, title_before, title_after, service, sponsored, attributes, null);
	}

	private void setUpUser2() {
		PerunAttribute user2attr1 = new PerunAttribute("user_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute user2attr2 = new PerunAttribute("user_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute user2attr3 = new PerunAttribute("user_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute user2attr4 = new PerunAttribute("user_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute user2attr5 = new PerunAttribute("user_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute user2attr6 = new PerunAttribute("user_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute user2attr7 = new PerunAttribute("user_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_attr_str", user2attr1);
		attributes.put("user_attr_int", user2attr2);
		attributes.put("user_attr_bool", user2attr3);
		attributes.put("user_attr_array", user2attr4);
		attributes.put("user_attr_map", user2attr5);
		attributes.put("user_attr_lstring", user2attr6);
		attributes.put("user_attr_larray", user2attr7);

		Long id = 2L;
		String firstName = "first_name2";
		String middleName = "middle_name2";
		String lastName = "last_name2";
		String title_before = "title_before2";
		String title_after = "title_after2";
		boolean service = false;
		boolean sponsored = false;

		EXPECTED2 = new User(id, firstName, middleName, lastName, title_before, title_after, service, sponsored, attributes, null);
	}

	private void setUpUser3() {
		PerunAttribute user3attr1 = new PerunAttribute("user_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute user3attr2 = new PerunAttribute("user_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute user3attr3 = new PerunAttribute("user_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute user3attr4 = new PerunAttribute("user_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute user3attr5 = new PerunAttribute("user_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute user3attr6 = new PerunAttribute("user_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute user3attr7 = new PerunAttribute("user_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_attr_str", user3attr1);
		attributes.put("user_attr_int", user3attr2);
		attributes.put("user_attr_bool", user3attr3);
		attributes.put("user_attr_array", user3attr4);
		attributes.put("user_attr_map", user3attr5);
		attributes.put("user_attr_lstring", user3attr6);
		attributes.put("user_attr_larray", user3attr7);

		Long id = 3L;
		String firstName = "first_name3";
		String middleName = "middle_name3";
		String lastName = "last_name3";
		String title_before = "title_before3";
		String title_after = "title_after3";
		boolean service = false;
		boolean sponsored = true;

		EXPECTED3 = new User(id, firstName, middleName, lastName, title_before, title_after, service, sponsored, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findUserByIdTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"id\" : {\"value\": 1}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByFirstNameTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"firstName\" : {\"value\": \"first_name1\"}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByMiddleNameTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"middleName\" : {\"value\": \"middle_name1\"}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByLastNameTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"lastName\" : {\"value\":\"last_name1\"}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByTitleBeforeTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"titleBefore\" : {\"value\":\"title_before1\"}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByTitleAfterNameTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"titleAfter\" : {\"value\":\"title_after1\"}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByServiceTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"serviceAcc\" : {\"value\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserBySponsoredTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"sponsoredAcc\" : {\"value\": true}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED3, result.get(0));
	}

	@Test
	public void findAllUsersTest() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findUserByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [ { \"name\" : \"user_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [ { \"name\" : \"user_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [ { \"name\" : \"user_attr_bool\", \"value\" : false}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findUserByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [ { \"name\" : \"user_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [ { \"name\" : \"user_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [ { \"name\" : \"user_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [ { \"name\" : \"user_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByFacilityEntity() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"facility\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByMemberEntity() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByUserExtSourceEntity() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user_ext_source\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserByUserFacilityRelation() throws Exception {
		String input = "{\"entityName\" : \"user\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user_facility\", \"facilityId\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

