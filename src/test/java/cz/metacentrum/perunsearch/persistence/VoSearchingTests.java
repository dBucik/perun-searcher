package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Vo;
import cz.metacentrum.perunsearch.service.SearcherService;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Equator;
import org.assertj.core.util.Lists;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.CoreMatchers.allOf;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.both;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
@SpringBootTest
public class VoSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Vo EXPECTED1;
	private Vo EXPECTED2;
	private Vo EXPECTED3;

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
		setUpVo1();
		setUpVo2();
		setUpVo3();
	}

	private void setUpVo1() {
		PerunAttribute vo1attr1 = new PerunAttribute("vo_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute vo1attr2 = new PerunAttribute("vo_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute vo1attr3 = new PerunAttribute("vo_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute vo1attr4 = new PerunAttribute("vo_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute vo1attr5 = new PerunAttribute("vo_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute vo1attr6 = new PerunAttribute("vo_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute vo1attr7 = new PerunAttribute("vo_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("vo_attr_str", vo1attr1);
		attributes.put("vo_attr_int", vo1attr2);
		attributes.put("vo_attr_bool", vo1attr3);
		attributes.put("vo_attr_array", vo1attr4);
		attributes.put("vo_attr_map", vo1attr5);
		attributes.put("vo_attr_lstring", vo1attr6);
		attributes.put("vo_attr_larray", vo1attr7);

		Long id = 1L;
		String shortName = "vo1";
		String name = "virtual_organization1";

		EXPECTED1 = new Vo(id, name, shortName, attributes, null);
	}

	private void setUpVo2() {
		PerunAttribute vo2attr1 = new PerunAttribute("vo_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute vo2attr2 = new PerunAttribute("vo_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute vo2attr3 = new PerunAttribute("vo_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute vo2attr4 = new PerunAttribute("vo_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute vo2attr5 = new PerunAttribute("vo_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute vo2attr6 = new PerunAttribute("vo_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute vo2attr7 = new PerunAttribute("vo_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("vo_attr_str", vo2attr1);
		attributes.put("vo_attr_int", vo2attr2);
		attributes.put("vo_attr_bool", vo2attr3);
		attributes.put("vo_attr_array", vo2attr4);
		attributes.put("vo_attr_map", vo2attr5);
		attributes.put("vo_attr_lstring", vo2attr6);
		attributes.put("vo_attr_larray", vo2attr7);

		Long id = 2L;
		String shortName = "vo2";
		String name = "virtual_organization2";

		EXPECTED2 = new Vo(id, name, shortName, attributes, null);
	}

	private void setUpVo3() {
		PerunAttribute vo3attr1 = new PerunAttribute("vo_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute vo3attr2 = new PerunAttribute("vo_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute vo3attr3 = new PerunAttribute("vo_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute vo3attr4 = new PerunAttribute("vo_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute vo3attr5 = new PerunAttribute("vo_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute vo3attr6 = new PerunAttribute("vo_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute vo3attr7 = new PerunAttribute("vo_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("vo_attr_str", vo3attr1);
		attributes.put("vo_attr_int", vo3attr2);
		attributes.put("vo_attr_bool", vo3attr3);
		attributes.put("vo_attr_array", vo3attr4);
		attributes.put("vo_attr_map", vo3attr5);
		attributes.put("vo_attr_lstring", vo3attr6);
		attributes.put("vo_attr_larray", vo3attr7);

		Long id = 3L;
		String shortName = "vo3";
		String name = "virtual_organization3";

		EXPECTED3 = new Vo(id, name, shortName, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findVoByIdTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"id\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByNameTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"name\" : \"virtual_organization1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByShortNameTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"shortName\" : \"vo1\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllVosTest() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findVoByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_bool\", \"value\" : false}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findVoByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findVoByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"vo\", \"attributes\" : [ { \"name\" : \"vo_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

