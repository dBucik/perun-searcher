package cz.metacentrum.perunsearch.persistence.data;

import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunRichEntity;
import cz.metacentrum.perunsearch.persistence.models.Query;

import java.util.List;

public interface PerunEntitiesDAO {

	List<PerunEntity> executeQuery(Query query);

	List<Long> executeQueryForIds(Query query);

}
