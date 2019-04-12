package edu.sjsu.cmpe275.api.controller.implementation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.IEmployerAPI;
import edu.sjsu.cmpe275.api.model.Employer;

@Controller
public class EmployerAPIController implements IEmployerAPI {

	@Override
	public ResponseEntity<Employer> getEmployer(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		// TODO: API logic

		return new ResponseEntity<Employer>(new Employer(), headers, HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<Employer> deleteEmployer(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		// TODO: API logic

		return new ResponseEntity<Employer>(new Employer(), headers, HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<Employer> createEmployer(String name, String description, String street, String city,
			String state, String zip, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		// TODO: API logic

		return new ResponseEntity<Employer>(new Employer(), headers, HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<Employer> updateEmployer(Long id, String name, String description, String street, String city,
			String state, String zip, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		// TODO: API logic

		return new ResponseEntity<Employer>(new Employer(), headers, HttpStatus.NOT_IMPLEMENTED);
	}

}
