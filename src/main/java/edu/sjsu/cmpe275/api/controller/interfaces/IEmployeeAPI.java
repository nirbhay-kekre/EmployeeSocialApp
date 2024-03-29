package edu.sjsu.cmpe275.api.controller.interfaces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.cmpe275.api.model.Employee;

/**
 * Employee API interface, defines request mapping for the Employee routes
 *
 * @author nirbhaykekre
 */
public interface IEmployeeAPI {

	/**
	 * This returns a full employee object with the given ID in the given format in
	 * its HTTP payload.<br>
	 * <br>
	 * All existing fields, including the optional employer and list of
	 * collaborators should be returned. Unless otherwise specified, the required
	 * attributes are the same as CREATE. <br>
	 * For each report, include only the id, name, and tile.<br>
	 * For each collaborator, include only the id, name, title, and employer with ID
	 * and name.<br>
	 * If the employee of the given user ID does not exist, the HTTP return code
	 * should be 404; 400 for other error, or 200 if successful.
	 *
	 * @param id     Employee id
	 * @param format output format possible values are json or xml.
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/employee/{id}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.GET)
	ResponseEntity<Employee> getEmployee(@PathVariable(value = "id", required = true) Long id,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	/**
	 * This API deletes the employee object with the given ID.<br>
	 * <br>
	 * If the employee with the given ID does not exist, return 404.<br>
	 * <br>
	 * If the employee still has a report, deletion is not allowed, hence return
	 * 400.<br>
	 * <br>
	 * Otherwise, delete the employee within a transaction<br>
	 * Remove any reference of this employee from your persistence of collaboration
	 * relations<br>
	 * If successful, HTTP status code 200 and the deleted employee in the given
	 * format, with all values prior to the deletion.<br>
	 * Please follow the JSON format given above; i.e., all the fields, if present,
	 * are required.
	 *
	 *
	 * @param id     Employee id
	 * @param format output format possible values are json or xml.
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/employee/{id}", produces = { "application/xml",
			"application/json" }, method = RequestMethod.DELETE)
	ResponseEntity<Employee> deleteEmployee(@PathVariable(value = "id", required = true) Long id,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	/**
	 * This API creates a employee object.<br>
	 * <br>
	 * For simplicity, all the employee fields (name, email, street, city, employer,
	 * etc), except ID and collaborators, are passed in as query parameters. Only
	 * the name, employer ID, and email are required. Anything else is optional.<br>
	 * <br>
	 * Collaborators or reports are not allowed to be passed in as a parameter.<br>
	 * <br>
	 * If the employee has a manager, only specify the manager’s ID using the query
	 * parameter managerId. The manager entity must be created already. <br>
	 * <br>
	 * The employer’s ID must be specified using the employerId query parameter. The
	 * employer entity must be created before creating this employee.<br>
	 * <br>
	 *
	 * If the request is invalid, e.g., missing required parameters, the HTTP status
	 * code should be 400; otherwise 200.The request returns the newly created
	 * employee object in the requested format in its HTTP payload, including all
	 * attributes.<br>
	 * For employer, only include the ID and name attributes.<br>
	 * For manager, only include the ID, name, and title attributes.
	 *
	 * @param name       name of the employee
	 * @param email      email of the employee, must be unique
	 * @param title      title of the employee
	 * @param street     address street
	 * @param city       address city
	 * @param state      address state
	 * @param zip        address zip
	 * @param employerId employer id of the employee, required
	 * @param managerId  Manager id of the employee
	 * @param format     output format possible values are json or xml.
	 *
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/employee", produces = { "application/xml",
			"application/json" }, method = RequestMethod.POST)
	ResponseEntity<Employee> createEmployee(@RequestParam(value = "name", required = true) String name,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "employerId", required = true) Long employerId,
			@RequestParam(value = "managerId", required = false) Long managerId,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

	/**
	 * This API updates a employee object. <br>
	 * <br>
	 * For simplicity, all employee fields (name, email, street, city, employer,
	 * etc) that have non-employ value(s), except collaborators, should be passed in
	 * as query parameters. Required fields like email must be present. The object
	 * constructed from the parameters will completely replace the existing object
	 * in the server, except that it does not change the employee’s list of
	 * collaborators.<br>
	 * <br>
	 * Changing an employee’s employer through the employId parameter is allowed,
	 * but it involves a complex transaction to implement.<br>
	 * If this person currently has a manager foo, all his/her current reports will
	 * report to foo.<br>
	 * If this person currently does not have a manager, all his/er reports will
	 * changed to not have a manager.<br>
	 * This person’s new manager (changed through the managerId parameter) must
	 * belong to the new company as well, or does not have a new manager.<br>
	 * <br>
	 * It is not allowed for an employee to report to a manager that does not work
	 * for the same employer. Any request that leads to this condition will be
	 * rejected with an 400 error.<br>
	 * <br>
	 * Similar to the get method, the request returns the updated employee object,
	 * including all attributes (id, name, email, title, collaborators, employer,
	 * etc), in the given format. If the employee ID does not exist, 404 should be
	 * returned. If required parameters are missing or run into other errors, return
	 * 400 instead. Otherwise, return 200. It is not allowed to directly change a
	 * person’s reports. Please follow the sample JSON given above.
	 *
	 * <b>NOTE: This method completely replaces the object in the DB </b>
	 *
	 * @param id         id of the employee to be updated.
	 * @param name       name of the employee
	 * @param email      email of the employee, must be unique
	 * @param title      title of the employee
	 * @param street     address street
	 * @param city       address city
	 * @param state      address state
	 * @param zip        address zip
	 * @param employerId employer id of the employee, required
	 * @param managerId  Manager id of the employee
	 * @param format     output format possible values are json or xml.
	 * @return ResponseEntity
	 */
	@RequestMapping(value = "/employee/{id}", produces = { "application/xml",
			"application/json" }, method = RequestMethod.PUT)
	ResponseEntity<Employee> updateEmployee(@PathVariable(value = "id", required = true) Long id,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "email", required = true) String email,
			@RequestParam(value = "title", required = false) String title,
			@RequestParam(value = "street", required = false) String street,
			@RequestParam(value = "city", required = false) String city,
			@RequestParam(value = "state", required = false) String state,
			@RequestParam(value = "zip", required = false) String zip,
			@RequestParam(value = "employerId", required = true) Long employerId,
			@RequestParam(value = "managerId", required = false) Long managerId,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

}
