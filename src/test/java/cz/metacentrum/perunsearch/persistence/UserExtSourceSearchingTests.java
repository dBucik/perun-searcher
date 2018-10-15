package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.UserExtSource;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Vo;
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
import java.sql.Timestamp;
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
public class UserExtSourceSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private UserExtSource EXPECTED1;
	private UserExtSource EXPECTED2;
	private UserExtSource EXPECTED3;

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
		setUpUes1();
		setUpUes2();
		setUpUes3();
	}

	private void setUpUes1() throws Exception {
		PerunAttribute userExtSource1attr1 = new PerunAttribute("user_ext_source_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute userExtSource1attr2 = new PerunAttribute("user_ext_source_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute userExtSource1attr3 = new PerunAttribute("user_ext_source_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute userExtSource1attr4 = new PerunAttribute("user_ext_source_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute userExtSource1attr5 = new PerunAttribute("user_ext_source_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute userExtSource1attr6 = new PerunAttribute("user_ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute userExtSource1attr7 = new PerunAttribute("user_ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_ext_source_attr_str", userExtSource1attr1);
		attributes.put("user_ext_source_attr_int", userExtSource1attr2);
		attributes.put("user_ext_source_attr_bool", userExtSource1attr3);
		attributes.put("user_ext_source_attr_array", userExtSource1attr4);
		attributes.put("user_ext_source_attr_map", userExtSource1attr5);
		attributes.put("user_ext_source_attr_lstring", userExtSource1attr6);
		attributes.put("user_ext_source_attr_larray", userExtSource1attr7);

		Long id = 1L;
		Long userId = 1L;
		String loginExt = "login_ext1";
		Long extSourcesId = 1L;
		int loa = 0;
		Timestamp stamp = Timestamp.valueOf("2018-01-01 08:00:00");
		long lastAccess = stamp.getTime();

		EXPECTED1 = new UserExtSource(id, userId, loginExt, extSourcesId, loa, lastAccess, attributes, null);
	}

	private void setUpUes2() {
		PerunAttribute userExtSource2attr1 = new PerunAttribute("user_ext_source_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute userExtSource2attr2 = new PerunAttribute("user_ext_source_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute userExtSource2attr3 = new PerunAttribute("user_ext_source_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute userExtSource2attr4 = new PerunAttribute("user_ext_source_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute userExtSource2attr5 = new PerunAttribute("user_ext_source_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute userExtSource2attr6 = new PerunAttribute("user_ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute userExtSource2attr7 = new PerunAttribute("user_ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_ext_source_attr_str", userExtSource2attr1);
		attributes.put("user_ext_source_attr_int", userExtSource2attr2);
		attributes.put("user_ext_source_attr_bool", userExtSource2attr3);
		attributes.put("user_ext_source_attr_array", userExtSource2attr4);
		attributes.put("user_ext_source_attr_map", userExtSource2attr5);
		attributes.put("user_ext_source_attr_lstring", userExtSource2attr6);
		attributes.put("user_ext_source_attr_larray", userExtSource2attr7);

		Long id = 2L;
		Long userId = 2L;
		String loginExt = "login_ext2";
		Long extSourcesId = 2L;
		int loa = 1;
		Timestamp stamp = Timestamp.valueOf("2018-01-02 08:00:00");
		long lastAccess = stamp.getTime();

		EXPECTED2 = new UserExtSource(id, userId, loginExt, extSourcesId, loa, lastAccess, attributes, null);
	}

	private void setUpUes3() {
		PerunAttribute userExtSource3attr1 = new PerunAttribute("user_ext_source_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute userExtSource3attr2 = new PerunAttribute("user_ext_source_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute userExtSource3attr3 = new PerunAttribute("user_ext_source_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute userExtSource3attr4 = new PerunAttribute("user_ext_source_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute userExtSource3attr5 = new PerunAttribute("user_ext_source_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute userExtSource3attr6 = new PerunAttribute("user_ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute userExtSource3attr7 = new PerunAttribute("user_ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("user_ext_source_attr_str", userExtSource3attr1);
		attributes.put("user_ext_source_attr_int", userExtSource3attr2);
		attributes.put("user_ext_source_attr_bool", userExtSource3attr3);
		attributes.put("user_ext_source_attr_array", userExtSource3attr4);
		attributes.put("user_ext_source_attr_map", userExtSource3attr5);
		attributes.put("user_ext_source_attr_lstring", userExtSource3attr6);
		attributes.put("user_ext_source_attr_larray", userExtSource3attr7);

		Long id = 3L;
		Long userId = 3L;
		String loginExt = "login_ext3";
		Long extSourcesId = 3L;
		int loa = 2;
		Timestamp stamp = Timestamp.valueOf("2018-01-03 08:00:00");
		long lastAccess = stamp.getTime();

		EXPECTED3 = new UserExtSource(id, userId, loginExt, extSourcesId, loa, lastAccess, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findUserExtSourceByIdTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"id\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByUserIdTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"userId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByLoginExtTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"loginExt\" : \"login_ext1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByExtSourcesIdTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"extSourcesId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByLoaTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"loa\" : 0, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByLastAccessTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"lastAccess\" : \"2018-01-01 08:00:00\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}


	@Test
	public void findAllUserExtSourcesTest() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findUserExtSourceByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_bool\", \"value\" : false}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findUserExtSourceByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [ { \"name\" : \"user_ext_source_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByUserEntity() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findUserExtSourceByExtSourceEntity() throws Exception {
		String input = "{\"entityName\" : \"user_ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"ext_source\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

