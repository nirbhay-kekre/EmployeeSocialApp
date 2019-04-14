package edu.sjsu.cmpe275.api.controller.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.IEmployerAPI;
import edu.sjsu.cmpe275.api.model.Address;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;
import edu.sjsu.cmpe275.api.repository.EmployerRepository;
import edu.sjsu.cmpe275.api.service.implementation.EmployerManagementService;

@Controller
public class EmployerAPIController implements IEmployerAPI {
	
	@Autowired
	private EmployerRepository employerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired EmployerManagementService employerService;

	@Override
	public ResponseEntity<Employer> getEmployer(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		
		Employer employerById = employerService.getEmployer(id);
		if(employerById!=null) {
			return new ResponseEntity<Employer>(employerById, headers, HttpStatus.OK);
		}else {
			return new ResponseEntity<Employer>(headers,HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Employer> deleteEmployer(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		
		Employer employerById = employerService.getEmployer(id);
		if(employerById!=null) {
			if(employerService.deleteEmployer(employerById)) {
				return new ResponseEntity<Employer>(employerById,headers,HttpStatus.OK);
			}else {
				return new ResponseEntity<Employer>(headers,HttpStatus.BAD_REQUEST);
			}
			
		}else {
			return new ResponseEntity<Employer>(headers,HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public ResponseEntity<Employer> createEmployer(String name, String description, String street, String city,
			String state, String zip, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		if(name == null){
			return new ResponseEntity<Employer>(headers,HttpStatus.BAD_REQUEST);
		}
		Employer employer=employerService.createEmployer(name, description, street, city, state, zip);
		if(employer==null){
			return new ResponseEntity<Employer>(headers,HttpStatus.BAD_REQUEST); 
		}
		return new ResponseEntity<Employer>(employer, headers, HttpStatus.OK);
	
	}

	@Override
	public ResponseEntity<Employer> updateEmployer(Long id, String name, String description, String street, String city,
			String state, String zip, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		//check if name is present
		if(name == null){
			return new ResponseEntity<Employer>(headers,HttpStatus.BAD_REQUEST);
		}
		
		//check if an employer already exists by the new name
		Employer employerById = employerService.getEmployer(id);
	
		if(employerById !=null) {
			employerById=employerService.updateEmployer(id,name,description,street,city,
					state,zip, employerById);
			return new ResponseEntity<Employer>(employerById, headers, HttpStatus.OK);
		}else {
			return new ResponseEntity<Employer>(headers,HttpStatus.NOT_FOUND);
		}
	}

}
