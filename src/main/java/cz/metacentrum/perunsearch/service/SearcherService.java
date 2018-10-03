package cz.metacentrum.perunsearch.service;

import cz.metacentrum.perunsearch.persistence.data.PerunEntitiesDAO;
import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.persistence.models.Query;
import cz.metacentrum.perunsearch.persistence.models.inputEntities.InputEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearcherService {

	private final PerunEntitiesDAO dao;

	public SearcherService(PerunEntitiesDAO perunEntitiesDAO) {
		this.dao = perunEntitiesDAO;
	}

	public JSONArray performSearch(String input) throws IllegalRelationException, InputParseException, IncorrectCoreAttributeTypeException {
		InputEntity parsedInput = JsonToInputParser.parseInput(input);
		Query query = parsedInput.toQuery(null);
		List<PerunEntity> result = dao.executeQuery(query);

		return buildResultString(result);
	}

	private JSONArray buildResultString(List<PerunEntity> entities) {
		List<JSONObject> partialJsons = entities.stream()
				.map(PerunEntity::toJson)
				.collect(Collectors.toList());

		JSONArray array = new JSONArray();
		array.put(partialJsons);
		return array;
	}

}
