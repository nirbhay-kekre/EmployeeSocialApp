package edu.sjsu.cmpe275.api.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.IEmployerAPI;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.service.implementation.EmployerManagementService;

/**
 * Controller for Employer API
 * 
 * @author nirbhaykekre
 */
@Controller
public class EmployerAPIController implements IEmployerAPI {

	@Autowired
	private EmployerManagementService employerService;

	/**
	 * This method looks-up an employer corresponding to the ID provided.<br>
	 * returns 404 if no such employer exists.
	 * 
	 * @param id     employer-id of the employer to be fetched.
	 * @param format JSON/XML
	 * @return The object of the employer in the mentioned format or error code.
	 */
	@Override
	public ResponseEntity<Employer> getEmployer(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		Employer employerById = employerService.getEmployer(id);
		if (employerById != null) {
			return new ResponseEntity<Employer>(employerById, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<Employer>(headers, HttpStatus.NOT_FOUND);
		}
	}

	/**
	 * This method deletes an employer corresponding to the ID provided.<br>
	 * <br>
	 * returns 404 if no employer corresponding to the ID is found.<br>
	 * returns 400 if the employer has any employees.
	 * 
	 * @param id     employer-id of the employer to be deleted.
	 * @param format JSON/XML
	 * @return The object of the deleted employer in the mentioned format or error
	 *         code.
	 */
	@Override
	public ResponseEntity<Employer> deleteEmployer(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		Employer employer = employerService.getEmployer(id);
		if (employer != null) {
			if (employerService.deleteEmployer(employer)) {
				return new ResponseEntity<Employer>(employer, headers, HttpStatus.OK);
			} else {
				return new ResponseEntity<Employer>(headers, HttpStatus.BAD_REQUEST);
			}

		} else {
			return new ResponseEntity<Employer>(headers, HttpStatus.NOT_FOUND);
		}

	}

	/**
	 * This method creates an employer entry.<br>
	 * returns 400 if an employer with the same name already exists.<br>
	 * returns 400 if employer's name is not mentioned in the query.
	 * 
	 * @param name        Name of the employer.
	 * @param description Details related to a particular employer.
	 * @param street      street on which the employer is present.
	 * @param city        city on which the employer is present.
	 * @param state       state on which the employer is present.
	 * @param zip         ZIP code of the employer.
	 * @param format      JSON/XML
	 * @return Object of the created employer or error code.
	 */
	@Override
	public ResponseEntity<Employer> createEmployer(String name, String description, String street, String city,
			String state, String zip, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		if (name == null) {
			return new ResponseEntity<Employer>(headers, HttpStatus.BAD_REQUEST);
		}
		Employer employer = employerService.createEmployer(name, description, street, city, state, zip);
		if (employer == null) {
			return new ResponseEntity<Employer>(headers, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<Employer>(employer, headers, HttpStatus.OK);

	}

	/**
	 * This method will edit an employer corresponding to the ID provided.<br>
	 * <br>
	 * returns 404 if an employer corresponding to the ID is not found.<br>
	 * returns 400 if name provided is not unique or belongs to another
	 * employer.<br>
	 * returns 400 if the name is not provided in the query.
	 * 
	 * @param id          ID of the employer to be edited.
	 * @param name        Name of the employer.
	 * @param description Details related to a particular employer.
	 * @param street      street on which the employer is present.
	 * @param city        city on which the employer is present.
	 * @param state       state on which the employer is present.
	 * @param zip         ZIP code of the employer.
	 * @param format      JSON/XML
	 * @return: The object of the edited employer or error code.
	 */
	@Override
	public ResponseEntity<Employer> updateEmployer(Long id, String name, String description, String street, String city,
			String state, String zip, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		// check if name is present
		if (name == null) {
			return new ResponseEntity<Employer>(headers, HttpStatus.BAD_REQUEST);
		}

		// check if an employer already exists by the new name
		Employer employer = employerService.getEmployer(id);

		if (employer != null) {
			employer = employerService.updateEmployer(name, description, street, city, state, zip, employer);
			return new ResponseEntity<Employer>(employer, headers, HttpStatus.OK);
		} else {
			return new ResponseEntity<Employer>(headers, HttpStatus.NOT_FOUND);
		}
	}

}
