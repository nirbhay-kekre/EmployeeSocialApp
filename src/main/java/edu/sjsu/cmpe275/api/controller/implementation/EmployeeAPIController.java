package edu.sjsu.cmpe275.api.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.api.controller.interfaces.IEmployeeAPI;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;

@Controller
public class EmployeeAPIController implements IEmployeeAPI {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	ObjectMapper mapper;

	@Override
	public ResponseEntity<Employee> getEmployee(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isPresent()) {
			Employee employee = emp.get();
			//lazy fetch manager
			Employee man = employee.getManager();
			if (man != null) {
				man.getEmail();
			}
			//lazy fetch reports
			employee.getReports().size();
			return new ResponseEntity<Employee>(employee, headers, HttpStatus.NOT_IMPLEMENTED);
		} else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Employee> deleteEmployee(Long id, String format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Employee> createEmployee(String name, String email, String title, String street, String city,
			String state, String zip, String employerId, String managerId, String format) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponseEntity<Employee> updateEmployee(String name, String email, String title, String street, String city,
			String state, String zip, String employerId, String managerId, String format) {
		// TODO Auto-generated method stub
		return null;
	}

}
