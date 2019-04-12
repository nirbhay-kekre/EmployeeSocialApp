package edu.sjsu.cmpe275.api.controller.interfaces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.cmpe275.api.model.Employer;

public interface IEmployerAPI {

	@RequestMapping(value = "/employer/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
	ResponseEntity<Employer> getEmployer(@PathVariable(value = "id", required = true) Long id,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	@RequestMapping(value = "/employer/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.DELETE)
	ResponseEntity<Employer> deleteEmployer(@PathVariable(value = "id", required = true) Long id,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	@RequestMapping(value = "/employer", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.POST)
	ResponseEntity<Employer> createEmployer(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	@RequestMapping(value = "/employer/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE,
					MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.PUT)
	ResponseEntity<Employer> updateEmployer(@PathVariable(value = "id", required = true) Long id,
			@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);
}
