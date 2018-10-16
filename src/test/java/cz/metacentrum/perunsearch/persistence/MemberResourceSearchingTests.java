package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.relations.MemberResource;
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
public class MemberResourceSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private MemberResource EXPECTED1;
	private MemberResource EXPECTED2;
	private MemberResource EXPECTED3;

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
		setUpMemberResource1();
		setUpMemberResource2();
		setUpMemberResource3();
	}

	private void setUpMemberResource1() throws Exception {
		PerunAttribute memberResource1attr1 = new PerunAttribute("member_resource_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute memberResource1attr2 = new PerunAttribute("member_resource_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute memberResource1attr3 = new PerunAttribute("member_resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute memberResource1attr4 = new PerunAttribute("member_resource_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute memberResource1attr5 = new PerunAttribute("member_resource_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute memberResource1attr6 = new PerunAttribute("member_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute memberResource1attr7 = new PerunAttribute("member_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_resource_attr_str", memberResource1attr1);
		attributes.put("member_resource_attr_int", memberResource1attr2);
		attributes.put("member_resource_attr_bool", memberResource1attr3);
		attributes.put("member_resource_attr_array", memberResource1attr4);
		attributes.put("member_resource_attr_map", memberResource1attr5);
		attributes.put("member_resource_attr_lstring", memberResource1attr6);
		attributes.put("member_resource_attr_larray", memberResource1attr7);

		Long memberId = 1L;
		Long resourceId = 1L;

		EXPECTED1 = new MemberResource(memberId, resourceId, attributes, null);
	}

	private void setUpMemberResource2() {
		PerunAttribute memberResource2attr1 = new PerunAttribute("member_resource_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute memberResource2attr2 = new PerunAttribute("member_resource_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute memberResource2attr3 = new PerunAttribute("member_resource_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute memberResource2attr4 = new PerunAttribute("member_resource_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute memberResource2attr5 = new PerunAttribute("member_resource_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute memberResource2attr6 = new PerunAttribute("member_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute memberResource2attr7 = new PerunAttribute("member_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_resource_attr_str", memberResource2attr1);
		attributes.put("member_resource_attr_int", memberResource2attr2);
		attributes.put("member_resource_attr_bool", memberResource2attr3);
		attributes.put("member_resource_attr_array", memberResource2attr4);
		attributes.put("member_resource_attr_map", memberResource2attr5);
		attributes.put("member_resource_attr_lstring", memberResource2attr6);
		attributes.put("member_resource_attr_larray", memberResource2attr7);

		Long memberId = 2L;
		Long resourceId = 2L;

		EXPECTED2 = new MemberResource(memberId, resourceId, attributes, null);
	}

	private void setUpMemberResource3() {
		PerunAttribute memberResource3attr1 = new PerunAttribute("member_resource_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute memberResource3attr2 = new PerunAttribute("member_resource_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute memberResource3attr3 = new PerunAttribute("member_resource_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute memberResource3attr4 = new PerunAttribute("member_resource_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute memberResource3attr5 = new PerunAttribute("member_resource_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute memberResource3attr6 = new PerunAttribute("member_resource_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute memberResource3attr7 = new PerunAttribute("member_resource_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_resource_attr_str", memberResource3attr1);
		attributes.put("member_resource_attr_int", memberResource3attr2);
		attributes.put("member_resource_attr_bool", memberResource3attr3);
		attributes.put("member_resource_attr_array", memberResource3attr4);
		attributes.put("member_resource_attr_map", memberResource3attr5);
		attributes.put("member_resource_attr_lstring", memberResource3attr6);
		attributes.put("member_resource_attr_larray", memberResource3attr7);

		Long memberId = 3L;
		Long resourceId = 3L;

		EXPECTED3 = new MemberResource(memberId, resourceId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findMemberResourceRelationByMemberIdTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"memberId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByResourceIdTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"resourceId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllMemberResourceRelationsTest() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findMemberResourceRelationByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_bool\", \"value\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [ { \"name\" : \"member_resource_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceRelationByMemberEntity() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberResourceByResourceEntity() throws Exception {
		String input = "{\"entityName\" : \"member_resource\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

