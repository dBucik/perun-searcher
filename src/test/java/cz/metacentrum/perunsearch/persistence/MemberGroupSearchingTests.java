package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.persistence.enums.PerunAttributeType;
import cz.metacentrum.perunsearch.persistence.models.PerunAttribute;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.relations.MemberGroup;
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
public class MemberGroupSearchingTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;
	private static PerunEntitiesDAO dao;
	private static SearcherService service;

	private static Resource tablesFile = new ClassPathResource("db_init.sql");
	private static Resource dataFile = new ClassPathResource("db_init_data.sql");

	private MemberGroup EXPECTED1;
	private MemberGroup EXPECTED2;
	private MemberGroup EXPECTED3;

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
		setUpMemberGroup1();
		setUpMemberGroup2();
		setUpMemberGroup3();
	}

	private void setUpMemberGroup1() throws Exception {
		PerunAttribute memberGroup1attr1 = new PerunAttribute("member_group_attr_str", PerunAttributeType.STRING, "value1");
		PerunAttribute memberGroup1attr2 = new PerunAttribute("member_group_attr_int", PerunAttributeType.INTEGER, "1");
		PerunAttribute memberGroup1attr3 = new PerunAttribute("member_group_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute memberGroup1attr4 = new PerunAttribute("member_group_attr_array", PerunAttributeType.ARRAY, "1,2");
		PerunAttribute memberGroup1attr5 = new PerunAttribute("member_group_attr_map", PerunAttributeType.MAP, "key1:value1,key2:value2");
		PerunAttribute memberGroup1attr6 = new PerunAttribute("member_group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute memberGroup1attr7 = new PerunAttribute("member_group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "1,2");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_group_attr_str", memberGroup1attr1);
		attributes.put("member_group_attr_int", memberGroup1attr2);
		attributes.put("member_group_attr_bool", memberGroup1attr3);
		attributes.put("member_group_attr_array", memberGroup1attr4);
		attributes.put("member_group_attr_map", memberGroup1attr5);
		attributes.put("member_group_attr_lstring", memberGroup1attr6);
		attributes.put("member_group_attr_larray", memberGroup1attr7);

		Long memberId = 1L;
		Long groupId = 1L;

		EXPECTED1 = new MemberGroup(memberId, groupId, attributes, null);
	}

	private void setUpMemberGroup2() {
		PerunAttribute memberGroup2attr1 = new PerunAttribute("member_group_attr_str", PerunAttributeType.STRING, "value2");
		PerunAttribute memberGroup2attr2 = new PerunAttribute("member_group_attr_int", PerunAttributeType.INTEGER, "2");
		PerunAttribute memberGroup2attr3 = new PerunAttribute("member_group_attr_bool", PerunAttributeType.BOOLEAN, "false");
		PerunAttribute memberGroup2attr4 = new PerunAttribute("member_group_attr_array", PerunAttributeType.ARRAY, "3,4");
		PerunAttribute memberGroup2attr5 = new PerunAttribute("member_group_attr_map", PerunAttributeType.MAP, "key3:value3,key4:value4");
		PerunAttribute memberGroup2attr6 = new PerunAttribute("member_group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value1");
		PerunAttribute memberGroup2attr7 = new PerunAttribute("member_group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "3,4");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_group_attr_str", memberGroup2attr1);
		attributes.put("member_group_attr_int", memberGroup2attr2);
		attributes.put("member_group_attr_bool", memberGroup2attr3);
		attributes.put("member_group_attr_array", memberGroup2attr4);
		attributes.put("member_group_attr_map", memberGroup2attr5);
		attributes.put("member_group_attr_lstring", memberGroup2attr6);
		attributes.put("member_group_attr_larray", memberGroup2attr7);

		Long memberId = 2L;
		Long groupId = 2L;

		EXPECTED2 = new MemberGroup(memberId, groupId, attributes, null);
	}

	private void setUpMemberGroup3() {
		PerunAttribute memberGroup3attr1 = new PerunAttribute("member_group_attr_str", PerunAttributeType.STRING, "value3");
		PerunAttribute memberGroup3attr2 = new PerunAttribute("member_group_attr_int", PerunAttributeType.INTEGER, "3");
		PerunAttribute memberGroup3attr3 = new PerunAttribute("member_group_attr_bool", PerunAttributeType.BOOLEAN, "true");
		PerunAttribute memberGroup3attr4 = new PerunAttribute("member_group_attr_array", PerunAttributeType.ARRAY, "5,6");
		PerunAttribute memberGroup3attr5 = new PerunAttribute("member_group_attr_map", PerunAttributeType.MAP, "key5:value5,key6:value6");
		PerunAttribute memberGroup3attr6 = new PerunAttribute("member_group_attr_lstring", PerunAttributeType.LARGE_STRING, "long_value3");
		PerunAttribute memberGroup3attr7 = new PerunAttribute("member_group_attr_larray", PerunAttributeType.LARGE_ARRAY_LIST, "5,6");

		Map<String, PerunAttribute> attributes = new HashMap<>();
		attributes.put("member_group_attr_str", memberGroup3attr1);
		attributes.put("member_group_attr_int", memberGroup3attr2);
		attributes.put("member_group_attr_bool", memberGroup3attr3);
		attributes.put("member_group_attr_array", memberGroup3attr4);
		attributes.put("member_group_attr_map", memberGroup3attr5);
		attributes.put("member_group_attr_lstring", memberGroup3attr6);
		attributes.put("member_group_attr_larray", memberGroup3attr7);

		Long memberId = 3L;
		Long groupId = 3L;

		EXPECTED3 = new MemberGroup(memberId, groupId, attributes, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findMemberGroupRelationByMemberIdTest() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"memberId\" : {\"value\": 1}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupRelationByGroupIdTest() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"groupId\" : {\"value\": 1}, \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllMemberGroupRelationsTest() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED3, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findMemberGroupRelationByStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [ { \"name\" : \"member_group_attr_str\", \"value\" : \"value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupRelationByIntegerAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [ { \"name\" : \"member_group_attr_int\", \"value\" : 1}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupRelationByBooleanAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [ { \"name\" : \"member_group_attr_bool\", \"value\" : true}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupRelationByArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [ { \"name\" : \"member_group_attr_array\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupRelationByMapAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [ { \"name\" : \"member_group_attr_map\", \"value\" : { \"key1\" : \"value1\", \"key2\" : \"value2\"}}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupRelationByLongStringAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [ { \"name\" : \"member_group_attr_lstring\", \"value\" : \"long_value1\"}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupRelationByLongArrayAttribute() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [ { \"name\" : \"member_group_attr_larray\", \"value\" : [1,2]}], \"attributeNames\" : [\"ALL\"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupRelationByMemberEntity() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"member\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findMemberGroupByGroupEntity() throws Exception {
		String input = "{\"entityName\" : \"member_group\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"group\", \"id\" : {\"value\": 1} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

