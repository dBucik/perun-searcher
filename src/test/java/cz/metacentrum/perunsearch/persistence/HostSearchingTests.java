package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Host;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Member;
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
public class HostSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Host EXPECTED1;
	private Host EXPECTED2;
	private Host EXPECTED3;

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
		setUpHost1();
		setUpHost2();
		setUpHost3();
	}

	private void setUpHost1() throws Exception {
		PerunAttribute host1attr1 = new PerunAttribute("host_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute host1attr2 = new PerunAttribute("host_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute host1attr3 = new PerunAttribute("host_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute host1attr4 = new PerunAttribute("host_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute host1attr5 = new PerunAttribute("host_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute host1attr6 = new PerunAttribute("host_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute host1attr7 = new PerunAttribute("host_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("host_attr_str", host1attr1);
		attributes.put("host_attr_int", host1attr2);
		attributes.put("host_attr_bool", host1attr3);
		attributes.put("host_attr_array", host1attr4);
		attributes.put("host_attr_map", host1attr5);
		attributes.put("host_attr_lstring", host1attr6);
		attributes.put("host_attr_larray", host1attr7);

		Long id = 1L;
		String hostname = "hostname1";
		Long facilityId = 1L;
		String dsc = "dsc1";

		EXPECTED1 = new Host(id, hostname, facilityId, dsc, attributes, null);
	}

	private void setUpHost2() {
		PerunAttribute host2attr1 = new PerunAttribute("host_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute host2attr2 = new PerunAttribute("host_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute host2attr3 = new PerunAttribute("host_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute host2attr4 = new PerunAttribute("host_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute host2attr5 = new PerunAttribute("host_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute host2attr6 = new PerunAttribute("host_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute host2attr7 = new PerunAttribute("host_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("host_attr_str", host2attr1);
		attributes.put("host_attr_int", host2attr2);
		attributes.put("host_attr_bool", host2attr3);
		attributes.put("host_attr_array", host2attr4);
		attributes.put("host_attr_map", host2attr5);
		attributes.put("host_attr_lstring", host2attr6);
		attributes.put("host_attr_larray", host2attr7);

		Long id = 2L;
		String hostname = "hostname2";
		Long facilityId = 2L;
		String dsc = "dsc2";

		EXPECTED2 = new Host(id, hostname, facilityId, dsc, attributes, null);
	}

	private void setUpHost3() {
		PerunAttribute host3attr1 = new PerunAttribute("host_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute host3attr2 = new PerunAttribute("host_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute host3attr3 = new PerunAttribute("host_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute host3attr4 = new PerunAttribute("host_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute host3attr5 = new PerunAttribute("host_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute host3attr6 = new PerunAttribute("host_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute host3attr7 = new PerunAttribute("host_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("host_attr_str", host3attr1);
		attributes.put("host_attr_int", host3attr2);
		attributes.put("host_attr_bool", host3attr3);
		attributes.put("host_attr_array", host3attr4);
		attributes.put("host_attr_map", host3attr5);
		attributes.put("host_attr_lstring", host3attr6);
		attributes.put("host_attr_larray", host3attr7);

		Long id = 3L;
		String hostname = "hostname3";
		Long facilityId = 3L;
		String dsc = "dsc3";

		EXPECTED3 = new Host(id, hostname, facilityId, dsc, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findHostByIdTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"id\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByHostnameTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"hostname\" : \"hostname1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByFacilityIdTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"facilityId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"description\" : \"dsc1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllHostsTest() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findHostByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_bool\", \"value\" : false}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findHostByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [ { \"name\" : \"host_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findHostByFacilityEntity() throws Exception {
		String input = "{\"entityName\" : \"host\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"facility\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

}

