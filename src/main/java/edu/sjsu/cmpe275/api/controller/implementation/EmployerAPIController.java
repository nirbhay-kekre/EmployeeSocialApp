package edu.sjsu.cmpe275.api.controller.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.api.controller.interfaces.IEmployerAPI;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.model.Address;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;
import edu.sjsu.cmpe275.api.repository.EmployerRepository;

@Controller
public class EmployerAPIController implements IEmployerAPI {
	
	@Autowired
	private EmployerRepository employerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	ObjectMapper mapper;

	@Override
	public ResponseEntity<Employer> getEmployer(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		
		Optional<Employer> emplr = employerRepository.findById(id);
		if(emplr.isPresent()) {
			Employer employer = emplr.get();
			return new ResponseEntity<Employer>(employer, headers, HttpStatus.OK);
		}else {
			return new ResponseEntity<Employer>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Employer> deleteEmployer(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		
		Optional<Employer> employerWrapper = employerRepository.findById(id);
		if(employerWrapper.isPresent()) {
			Employer employer = employerWrapper.get();
			List<Employee> employeesByEmployers = employeeRepository.findByEmployer(employer);
			if(employeesByEmployers.isEmpty()) {
				employerRepository.deleteById(id);
				return new ResponseEntity<Employer>(HttpStatus.OK);
			
			}else {
				return new ResponseEntity<Employer>(HttpStatus.BAD_REQUEST);
			}
			
		}else {
			return new ResponseEntity<Employer>(HttpStatus.NOT_FOUND);
		}

	}

	@Override
	public ResponseEntity<Employer> createEmployer(String name, String description, String street, String city,
			String state, String zip, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		
		if(name == null)
			return new ResponseEntity<Employer>(HttpStatus.BAD_REQUEST);
		
		Optional<Employer> employerWrapper = employerRepository.findByName(name);
		if(employerWrapper.isPresent())
			return new ResponseEntity<Employer>(HttpStatus.BAD_REQUEST); 

		Employer employer = new Employer();
		employer.setName(name);
		employer.setDescription(description);
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		employer.setAddress(address);
		
		employerRepository.save(employer);
		return new ResponseEntity<Employer>(employer, headers, HttpStatus.OK);
		
	}

	@Override
	public ResponseEntity<Employer> updateEmployer(Long id, String name, String description, String street, String city,
			String state, String zip, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		if(name == null) 
			return new ResponseEntity<Employer>(HttpStatus.BAD_REQUEST);
		
		
		Optional<Employer> emplr = employerRepository.findById(id);
		if(emplr.isPresent()) {
			Employer employer = emplr.get();
			employer.setName(name);
			employer.setDescription(description);
			Address address = new Address();
			address.setStreet(street);
			address.setCity(city);
			address.setState(state);
			address.setZip(zip);
			employer.setAddress(address);
			employerRepository.save(employer);
			return new ResponseEntity<Employer>(employer, headers, HttpStatus.OK);
//			Optional<Employer> emplr_check = employerRepository.findById(id);
//			if(emplr_check.isPresent()) {
//				return new ResponseEntity<Employer>(emplr_check.get(), headers, HttpStatus.OK);
//			}else {
//				return new ResponseEntity<Employer>(HttpStatus.BAD_REQUEST);
//			}
			
		}else {
			return new ResponseEntity<Employer>(HttpStatus.NOT_FOUND);
		}
	}

}
