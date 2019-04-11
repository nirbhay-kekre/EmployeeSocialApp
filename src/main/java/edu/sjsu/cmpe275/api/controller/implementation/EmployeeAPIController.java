package edu.sjsu.cmpe275.api.controller.implementation;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.MappingJackson2XmlHttpMessageConverter;
import org.springframework.stereotype.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import edu.sjsu.cmpe275.api.controller.interfaces.IEmployeeAPI;
import edu.sjsu.cmpe275.api.model.Address;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;
import edu.sjsu.cmpe275.api.repository.EmployerRepository;

@Controller
public class EmployeeAPIController implements IEmployeeAPI {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	ObjectMapper mapper;

	@Autowired
	private MappingJackson2XmlHttpMessageConverter xmlConverter;

	@Override
	public ResponseEntity<Employee> getEmployee(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		Optional<Employee> emp = employeeRepository.findById(id);
		if (emp.isPresent()) {
			Employee employee = emp.get();
			// lazy fetch manager
			Employee man = employee.getManager();
			if (man != null) {
				man.getEmail();
			}
			// lazy fetch reports
			employee.getReports().size();
			// lazy fetch employer
			employee.getEmployer().getName();
			employee.getCollaborators().size();
			return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);

		} else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Employee> deleteEmployee(Long id, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		// TODO: API logic

		return new ResponseEntity<Employee>(new Employee(), headers, HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<Employee> createEmployee(String name, String email, String title, String street, String city,
			String state, String zip, Long employerId, Long managerId, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		Optional<Employee> employeeByMail = employeeRepository.findByEmail(email);
		if (employeeByMail.isPresent()) {
			return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
		}

		Employee employee = new Employee();
		employee.setName(name);
		employee.setEmail(email);
		employee.setTitle(title);
		Address addr = new Address();
		addr.setStreet(street);
		addr.setCity(city);
		addr.setState(state);
		addr.setZip(zip);
		employee.setAddress(addr);
		Optional<Employer> employerWrapper = employerRepository.findById(employerId);

		if (employerWrapper.isPresent()) {
			Employer employer = employerWrapper.get();
			employee.setEmployer(employer);
		} else {
			return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
		}
		if (managerId != null) {
			Optional<Employee> managerWrapper = employeeRepository.findById(managerId);
			if (managerWrapper.isPresent()) {
				Employee manager = managerWrapper.get();
				if (!employee.getEmployer().equals(manager.getEmployer())) {
					return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
				}
				employee.setManager(manager);
			} else {
				return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
			}
		}
		employeeRepository.save(employee);

		return new ResponseEntity<Employee>(employee, headers, HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<Employee> updateEmployee(Long id, String name, String email, String title, String street,
			String city, String state, String zip, Long employerId, Long managerId, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		// TODO: API logic

		return new ResponseEntity<Employee>(new Employee(), headers, HttpStatus.NOT_IMPLEMENTED);
	}

}
