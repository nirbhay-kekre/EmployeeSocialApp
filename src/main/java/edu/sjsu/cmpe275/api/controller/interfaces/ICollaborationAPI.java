package edu.sjsu.cmpe275.api.controller.interfaces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.cmpe275.api.model.ResponseMessage;

public interface ICollaborationAPI {
	@RequestMapping(value = "/collaborators/{id1}/{id2}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.PUT)
	ResponseEntity<ResponseMessage> addCollaborator(@PathVariable(value = "id1", required = true) Long id1,
			@PathVariable(value = "id2", required = true) Long id2,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);
	
	@RequestMapping(value = "/collaborators/{id1}/{id2}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.DELETE)
	ResponseEntity<ResponseMessage> removeCollaborator(@PathVariable(value = "id1", required = true) Long id1,
			@PathVariable(value = "id2", required = true) Long id2,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);
}
