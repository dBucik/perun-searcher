package cz.metacentrum.perunsearch.service;

import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.exceptions.AttributeTypeException;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.Query;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearcherService {

	private final PerunEntitiesDAO dao;

	public SearcherService(PerunEntitiesDAO perunEntitiesDAO) {
		this.dao = perunEntitiesDAO;
	}

	public List<PerunEntity> performSearch(String input) throws IllegalRelationException, InputParseException, IncorrectCoreAttributeTypeException, AttributeTypeException {
		InputEntity parsedInput = JsonToInputParser.parseInput(input);
		Query query = parsedInput.toQuery(null);
		return dao.executeQuery(query);
	}

}
