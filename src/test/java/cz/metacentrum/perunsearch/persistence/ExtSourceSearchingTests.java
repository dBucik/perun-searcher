package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.ExtSource;
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
public class ExtSourceSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private ExtSource EXPECTED1;
	private ExtSource EXPECTED2;
	private ExtSource EXPECTED3;

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
		setUpExtSource1();
		setUpExtSource2();
		setUpExtSource3();
	}

	private void setUpExtSource1() throws Exception {
		PerunAttribute extSource1attr1 = new PerunAttribute("ext_source_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute extSource1attr2 = new PerunAttribute("ext_source_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute extSource1attr3 = new PerunAttribute("ext_source_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute extSource1attr4 = new PerunAttribute("ext_source_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute extSource1attr5 = new PerunAttribute("ext_source_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute extSource1attr6 = new PerunAttribute("ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute extSource1attr7 = new PerunAttribute("ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("ext_source_attr_str", extSource1attr1);
		attributes.put("ext_source_attr_int", extSource1attr2);
		attributes.put("ext_source_attr_bool", extSource1attr3);
		attributes.put("ext_source_attr_array", extSource1attr4);
		attributes.put("ext_source_attr_map", extSource1attr5);
		attributes.put("ext_source_attr_lstring", extSource1attr6);
		attributes.put("ext_source_attr_larray", extSource1attr7);

		Long id = 1L;
		String name = "ext_source1";
		String type = "type1";

		EXPECTED1 = new ExtSource(id, name, type, attributes, null);
	}

	private void setUpExtSource2() {
		PerunAttribute extSource2attr1 = new PerunAttribute("ext_source_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute extSource2attr2 = new PerunAttribute("ext_source_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute extSource2attr3 = new PerunAttribute("ext_source_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute extSource2attr4 = new PerunAttribute("ext_source_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute extSource2attr5 = new PerunAttribute("ext_source_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute extSource2attr6 = new PerunAttribute("ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute extSource2attr7 = new PerunAttribute("ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("ext_source_attr_str", extSource2attr1);
		attributes.put("ext_source_attr_int", extSource2attr2);
		attributes.put("ext_source_attr_bool", extSource2attr3);
		attributes.put("ext_source_attr_array", extSource2attr4);
		attributes.put("ext_source_attr_map", extSource2attr5);
		attributes.put("ext_source_attr_lstring", extSource2attr6);
		attributes.put("ext_source_attr_larray", extSource2attr7);

		Long id = 2L;
		String name = "ext_source2";
		String type = "type2";

		EXPECTED2 = new ExtSource(id, name, type, attributes, null);
	}

	private void setUpExtSource3() {
		PerunAttribute extSource3attr1 = new PerunAttribute("ext_source_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute extSource3attr2 = new PerunAttribute("ext_source_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute extSource3attr3 = new PerunAttribute("ext_source_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute extSource3attr4 = new PerunAttribute("ext_source_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute extSource3attr5 = new PerunAttribute("ext_source_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute extSource3attr6 = new PerunAttribute("ext_source_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute extSource3attr7 = new PerunAttribute("ext_source_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("ext_source_attr_str", extSource3attr1);
		attributes.put("ext_source_attr_int", extSource3attr2);
		attributes.put("ext_source_attr_bool", extSource3attr3);
		attributes.put("ext_source_attr_array", extSource3attr4);
		attributes.put("ext_source_attr_map", extSource3attr5);
		attributes.put("ext_source_attr_lstring", extSource3attr6);
		attributes.put("ext_source_attr_larray", extSource3attr7);

		Long id = 3L;
		String name = "ext_source3";
		String type = "type3";

		EXPECTED3 = new ExtSource(id, name, type, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findExtSourceByIdTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"id\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByNameTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"name\" : \"ext_source1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByTypeTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"type\" : \"type1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllFacilitiesTest() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findExtSourceByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_int\", \"value\" : \"1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_bool\", \"value\" : \"true\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_array\", \"value\" : \"1,2\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_map\", \"value\" : \"key1:value1,key2:value2\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [ { \"name\" : \"ext_source_attr_larray\", \"value\" : \"1,2\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByVoEntity() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"vo\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByGroupEntity() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findExtSourceByUserExtSourceEntity() throws Exception {
		String input = "{\"entityName\" : \"ext_source\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user_ext_source\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

