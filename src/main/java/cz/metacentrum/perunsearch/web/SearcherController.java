package cz.metacentrum.perunsearch.web;

import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perunsearch.service.InputParseException;
import cz.metacentrum.perunsearch.service.SearcherService;
import org.json.JSONArray;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SearcherController {

	private SearcherService searcherService;

	public SearcherController(SearcherService searcherService) {
		this.searcherService = searcherService;
	}

	@RequestMapping(value = "search")
	public String search(@RequestParam("query") String query) throws IllegalRelationException, InputParseException, IncorrectCoreAttributeTypeException {
		query = "{\"entityName\":\"group\",\"attributes\":[{\"name\":\"urn:perun:group:attribute-def:core:vo_id\",\"type\":\"INTEGER\",\"value\":62}],\"attributeNames\":[]}";
		//TODO: delete hardcoded input
		JSONArray result = searcherService.performSearch(query);
		return result.toString(4);
	}
}
