package edu.sjsu.cmpe275.api.service.intefaces;

import java.util.Optional;

import org.springframework.http.ResponseEntity;

import edu.sjsu.cmpe275.api.model.Employer;

public interface IEmployerManagementService {

	public Employer getEmployer(Long id);
	public boolean deleteEmployer(Employer employer);
	public Employer createEmployer(String name, String description, String street, String city,
			String state, String zip);
	public Employer updateEmployer(Long id, String name, String description, String street, String city,
			String state, String zip, Employer employer);
	
	
}
