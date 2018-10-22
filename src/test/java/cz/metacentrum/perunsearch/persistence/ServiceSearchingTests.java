package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.TestUtils;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.basic.Service;
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
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
@SpringBootTest
public class ServiceSearchingTests {

	@ClassRule
	public static final SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static SearcherService service;
	private static final Resource tablesFile = new ClassPathResource("db_init.sql");
	private static final Resource dataFile = new ClassPathResource("db_init_data.sql");

	private Service EXPECTED1;
	private Service EXPECTED2;
	private Service EXPECTED23;

	@BeforeClass
	public static void setUpDatabaseTables() throws Exception {
		service = TestUtils.setUpDatabaseTables(pg, tablesFile, dataFile);
	}

	@Before
	public void setUp() {
		setUpService1();
		setUpService2();
		setUpService23();
	}

	private void setUpService1() {
		Integer id = 1;
		String name = "service1";
		String description = "dsc1";
		int delay = 1;
		int recurrence = 1;
		boolean enabled = true;
		String script = "script1";

		EXPECTED1 = new Service(id, name, description, delay, recurrence, enabled, script, null);
	}

	private void setUpService2() {
		Integer id = 2;
		String name = "service2";
		String description = "dsc2";
		int delay = 2;
		int recurrence = 2;
		boolean enabled = false;
		String script = "script2";

		EXPECTED2 = new Service(id, name, description, delay, recurrence, enabled, script, null);
	}

	private void setUpService23() {
		Integer id = 23;
		String name = "service23";
		String description = "dsc23";
		int delay = 23;
		int recurrence = 23;
		boolean enabled = false;
		String script = "script23";

		EXPECTED23 = new Service(id, name, description, delay, recurrence, enabled, script, null);
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}

	@Test
	public void findServiceByIdTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"id\" : {\"value\": [1]} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByNameTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"name\" : {\"value\": [\"service1\"]} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByDescriptionTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"description\" : {\"value\": [\"dsc1\"]} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByDelayTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"delay\" : {\"value\": [1]} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByRecurrenceTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"recurrence\" : {\"value\": [1]} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByEnabledTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"enabled\" : {\"value\": [true]} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findServiceByScriptTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"script\" : {\"value\": [\"script1\"]}}";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}

	@Test
	public void findAllServicesTest() throws Exception {
		String input = "{\"entityName\" : \"service\"}";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(3, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2, EXPECTED1));
	}

	@Test
	public void findServiceByIdLikeTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"id\" : {\"value\": [2], \"matchLike\": true} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByNameLikeTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"name\" : {\"value\": [\"service2\"], \"matchLike\": true} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByDescriptionLikeTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"description\" : {\"value\": [\"dsc2\"], \"matchLike\": true} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByDelayLikeTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"delay\" : {\"value\": [2], \"matchLike\": true} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByRecurrenceLikeTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"recurrence\" : {\"value\": [2], \"matchLike\": true} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByEnabledLikeTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"enabled\" : {\"value\": [false], \"matchLike\": true} }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByScriptLikeTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"script\" : {\"value\": [\"script2\"], \"matchLike\": true}}";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(2, result.size());
		assertThat(result, hasItems(EXPECTED23, EXPECTED2));
	}

	@Test
	public void findServiceByResourceEntityTest() throws Exception {
		String input = "{\"entityName\" : \"service\", \"attributes\" : [], \"attributesNames\" : [\"ALL\"], \"relations\" : [" +
				"{ \"entityName\" : \"resource\", \"id\" : {\"value\": [1]} }" +
				"] }";

		List<PerunEntity> result = service.performSearch(input);
		assertNotNull(result);
		assertEquals(1, result.size());
		assertEquals(EXPECTED1, result.get(0));
	}
}

