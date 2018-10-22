package cz.metacentrum.perunsearch;

import com.opentable.db.postgres.junit.SingleInstancePostgresRule;
import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAOImpl;
import cz.metacentrum.perunsearch.service.SearcherService;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class TestUtils {

	public static SearcherService setUpDatabaseTables(SingleInstancePostgresRule pg, Resource tablesFile,
													  Resource dataFile) throws Exception {
		DataSource dataSource = pg.getEmbeddedPostgres().getPostgresDatabase();
		DBUtils.executeSqlScript(dataSource, tablesFile.getFile());
		DBUtils.executeSqlScript(dataSource, dataFile.getFile());
		JdbcTemplate template = new JdbcTemplate(dataSource);
		PerunEntitiesDAOImpl dao = new PerunEntitiesDAOImpl();
		dao.setTemplate(template);

		return new SearcherService(dao);
	}
}
