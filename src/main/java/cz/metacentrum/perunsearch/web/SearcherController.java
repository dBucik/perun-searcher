package cz.metacentrum.perunsearch.web;

import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.service.InputParseException;
import cz.metacentrum.perunsearch.service.SearcherService;
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
	public PerunEntity[] search(@RequestParam("query") String query) throws IllegalRelationException, InputParseException, IncorrectCoreAttributeTypeException {
		query = "{\"entityName\":\"group\", \"voId\": 62}";
		//TODO: delete hardcoded input
		return searcherService.performSearch(query).toArray(new PerunEntity[]{});
	}
}
