package edu.sjsu.cmpe275.api.controller.interfaces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.cmpe275.api.model.Employer;

/**
 * Employer API interface, defines request mapping for the Employer routes
 *
 * @author nirbhaykekre
 */
public interface IEmployerAPI {

	/**
	 * This method looks-up an employer corresponding to the ID provided. returns
	 * 404 if no such employer exists.
	 *
	 * @param id: employer-id of the employer to be fetched.
	 * @param format: JSON/XML
	 * @return: The object of the employer in the mentioned format or error code.
	 */
	@RequestMapping(value = "/employer/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
	ResponseEntity<Employer> getEmployer(@PathVariable(value = "id", required = true) Long id,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	/**
	 * This method deletes an employer corresponding to the ID provided. returns 404
	 * if no employer corresponding to the ID is found. returns 400 if the employer
	 * has any employees.
	 *
	 * @param id: employer-id of the employer to be deleted.
	 * @param format: JSON/XML
	 * @return: The object of the deleted employer in the mentioned format or error
	 *          code.
	 */
	@RequestMapping(value = "/employer/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.DELETE)
	ResponseEntity<Employer> deleteEmployer(@PathVariable(value = "id", required = true) Long id,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	/**
	 * This method creates an employer entry. returns 400 if an employer with the
	 * same name already exists. returns 400 if employer's name is not mentioned in
	 * the query.
	 *
	 * @param name: Name of the employer.
	 * @param description: Details related to a particular employer.
	 * @param street: street on which the employer is present.
	 * @param city: city on which the employer is present.
	 * @param state: state on which the employer is present.
	 * @param zip: ZIP code of the employer.
	 * @param format: JSON/XML
	 * @return: Object of the created employer or error code.
	 */
	@RequestMapping(value = "/employer", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.POST)
	ResponseEntity<Employer> createEmployer(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "description", required = false) String description,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	/**
	 * This method will edit an employer corresponding to the ID provided. returns
	 * 404 if an employer corresponding to the ID is not found. returns 400 if name
	 * provided is not unique or belongs to another employer. returns 400 if the
	 * name is not provided in the query.
	 *
	 * @param id: ID of the employer to be edited.
	 * @param name: Name of the employer.
	 * @param description: Details related to a particular employer.
	 * @param street: street on which the employer is present.
	 * @param city: city on which the employer is present.
	 * @param state: state on which the employer is present.
	 * @param zip: ZIP code of the employer.
	 * @param format: JSON/XML
	 * @return: The object of the edited employer or error code.
	 */
	@RequestMapping(value = "/employer/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
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
