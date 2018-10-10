package cz.metacentrum.perunsearch.persistence;

import com.opentable.db.postgres.junit.EmbeddedPostgresRules;
import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.DBUtils;
import org.junit.AfterClass;
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

@RunWith(SpringRunner.class)
@ContextConfiguration("classpath*:applicationContext.xml")
@SpringBootTest
public class DatabaseTests {

	@ClassRule
	public static SingleInstancePostgresRule pg = EmbeddedPostgresRules.singleInstance();

	private static DataSource dataSource;
	private static JdbcTemplate template;

	private static Resource scriptsFile = new ClassPathResource("db_init.sql");

	@BeforeClass
	public static void setUpDatabaseTables() throws Exception {
		dataSource = pg.getEmbeddedPostgres().getPostgresDatabase();
		DBUtils.executeSqlScript(dataSource, scriptsFile.getFile());
		template = new JdbcTemplate(dataSource);
	}

	@Test
	public void testEmbeddedPg() throws Exception {
		template.query("SELECT * FROM test", resultSet -> {
			String test = resultSet.getString("info");
			System.out.println(test);
		});
	}

	@AfterClass
	public static void tearDownClass() throws IOException {
		pg.getEmbeddedPostgres().close();
	}
}

