package edu.sjsu.cmpe275.api.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.IEmployeeAPI;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.service.intefaces.IEmployeeManagementService;
import edu.sjsu.cmpe275.utils.EmployeeUtils;

/**
 * Controller for Employee
 *
 * @author nirbhaykekre
 */
@Controller
public class EmployeeAPIController implements IEmployeeAPI {

	@Autowired
	private IEmployeeManagementService employeeManagementService;

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
	@Override
	public ResponseEntity<Employee> getEmployee(Long id, String format) {
		HttpHeaders headers = httpResponseHeaderSetup(format);
		Employee employee = employeeManagementService.getEmployee(id);
		if (employee != null) {
			return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}

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
	 * 
	 */
	@Override
	public ResponseEntity<Employee> deleteEmployee(Long id, String format) {
		HttpHeaders headers = httpResponseHeaderSetup(format);

		Employee employee = employeeManagementService.getEmployee(id);
		if (employee != null) {
			if (employeeManagementService.deleteEmployee(employee)) {
				return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}

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
	 * 
	 */
	@Override
	public ResponseEntity<Employee> createEmployee(String name, String email, String title, String street, String city,
			String state, String zip, Long employerId, Long managerId, String format) {
		HttpHeaders headers = httpResponseHeaderSetup(format);

		Employee employee = employeeManagementService.createEmployee(name, email, title, street, city, state, zip,
				employerId, managerId);
		if (employee == null) {
			return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
	}

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
	 * 
	 */
	@Override
	public ResponseEntity<Employee> updateEmployee(Long id, String name, String email, String title, String street,
			String city, String state, String zip, Long employerId, Long managerId, String format) {
		HttpHeaders headers = httpResponseHeaderSetup(format);
		if (id == null || email == null || employerId == null || name == null) {
			return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
		}

		Employee employee = employeeManagementService.getEmployee(id);
		if (employee != null) {
			if (!email.equals(employee.getEmail())) {
				Employee employeeByMail = employeeManagementService.getEmployeeByEmail(email);
				if (employeeByMail == null) {
					return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
				}
			}
			if (!employeeManagementService.updateEmployee(employee, name, email, title, street, city, state, zip,
					employerId, managerId)) {
				return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
			}
			EmployeeUtils.fetchLazyAttributeFromEmployee(employee);
			return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);

		} else {
			return new ResponseEntity<Employee>(headers, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * Creates httpHeader based on give format
	 *
	 * @param format json/xml
	 * @return HttpHeader with appropriate format
	 */
	private HttpHeaders httpResponseHeaderSetup(String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		return headers;
	}

}
