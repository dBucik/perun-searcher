package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
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
public class MemberSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Member EXPECTED1;
	private Member EXPECTED2;
	private Member EXPECTED3;

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
		setUpMember1();
		setUpMember2();
		setUpMember3();
	}

	private void setUpMember1() throws Exception {
		PerunAttribute member1attr1 = new PerunAttribute("member_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute member1attr2 = new PerunAttribute("member_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute member1attr3 = new PerunAttribute("member_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute member1attr4 = new PerunAttribute("member_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute member1attr5 = new PerunAttribute("member_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute member1attr6 = new PerunAttribute("member_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute member1attr7 = new PerunAttribute("member_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_attr_str", member1attr1);
		attributes.put("member_attr_int", member1attr2);
		attributes.put("member_attr_bool", member1attr3);
		attributes.put("member_attr_array", member1attr4);
		attributes.put("member_attr_map", member1attr5);
		attributes.put("member_attr_lstring", member1attr6);
		attributes.put("member_attr_larray", member1attr7);

		Long id = 1L;
		Long userId = 1L;
		Long voId = 1L;
		boolean sponsored = true;

		EXPECTED1 = new Member(id, userId, voId, sponsored, attributes, null);
	}

	private void setUpMember2() {
		PerunAttribute member2attr1 = new PerunAttribute("member_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute member2attr2 = new PerunAttribute("member_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute member2attr3 = new PerunAttribute("member_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute member2attr4 = new PerunAttribute("member_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute member2attr5 = new PerunAttribute("member_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute member2attr6 = new PerunAttribute("member_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute member2attr7 = new PerunAttribute("member_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_attr_str", member2attr1);
		attributes.put("member_attr_int", member2attr2);
		attributes.put("member_attr_bool", member2attr3);
		attributes.put("member_attr_array", member2attr4);
		attributes.put("member_attr_map", member2attr5);
		attributes.put("member_attr_lstring", member2attr6);
		attributes.put("member_attr_larray", member2attr7);

		Long id = 2L;
		Long userId = 2L;
		Long voId = 2L;
		boolean sponsored = false;

		EXPECTED2 = new Member(id, userId, voId, sponsored, attributes, null);
	}

	private void setUpMember3() {
		PerunAttribute member3attr1 = new PerunAttribute("member_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute member3attr2 = new PerunAttribute("member_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute member3attr3 = new PerunAttribute("member_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute member3attr4 = new PerunAttribute("member_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute member3attr5 = new PerunAttribute("member_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute member3attr6 = new PerunAttribute("member_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute member3attr7 = new PerunAttribute("member_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_attr_str", member3attr1);
		attributes.put("member_attr_int", member3attr2);
		attributes.put("member_attr_bool", member3attr3);
		attributes.put("member_attr_array", member3attr4);
		attributes.put("member_attr_map", member3attr5);
		attributes.put("member_attr_lstring", member3attr6);
		attributes.put("member_attr_larray", member3attr7);

		Long id = 3L;
		Long userId = 3L;
		Long voId = 3L;
		boolean sponsored = true;

		EXPECTED3 = new Member(id, userId, voId, sponsored, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findMemberByIdTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"id\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByUserIdTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"userId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByVoIdTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"voId\" : 1, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberBySponsoredTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"sponsored\" : false, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findAllMembersTest() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findMemberByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_bool\", \"value\" : false}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED2, result.get(0));
	}

	@Test
	public void findMemberByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [ { \"name\" : \"member_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByVoEntity() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"vo\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByUserEntity() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"user\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByResourceEntity() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByGroupEntity() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByMemberResourceRelation() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member_resource\", \"resourceId\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberByMemberGroupRelation() throws Exception {
		String input = "{\"entityName\" : \"member\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member_group\", \"groupId\" : 1 }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

