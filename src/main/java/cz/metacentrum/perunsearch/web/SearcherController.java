package cz.metacentrum.perunsearch.web;

import cz.metacentrum.perunsearch.persistence.exceptions.IllegalRelationException;
import cz.metacentrum.perunsearch.persistence.exceptions.IncorrectCoreAttributeTypeException;
import cz.metacentrum.perunsearch.persistence.models.entities.PerunEntity;
import cz.metacentrum.perunsearch.service.InputParseException;
import cz.metacentrum.perunsearch.service.SearcherService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class SearcherController {

	private SearcherService searcherService;

	public SearcherController(SearcherService searcherService) {
		this.searcherService = searcherService;
	}

	@RequestMapping(value = "search")
	public List<PerunEntity> search(@RequestParam("query") String query) throws IllegalRelationException, InputParseException, IncorrectCoreAttributeTypeException {
		query = "{\"entityName\":\"group_resource\", \"attributes\": [{ \"name\" : \"urn:perun:group:attribute-def:def:groupExtSource\", \"value\" : \"PERUN\"}], \"relations\" : [{ \"entityName\" : \"group\", \"id\" : 62 }]}";
		//TODO: delete hardcoded input
		//TODO: serialization for skipping foreignId etc...
		return searcherService.performSearch(query);
	}
}
