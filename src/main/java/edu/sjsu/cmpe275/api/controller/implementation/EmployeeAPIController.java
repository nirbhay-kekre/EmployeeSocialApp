package edu.sjsu.cmpe275.api.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.IEmployeeAPI;
import edu.sjsu.cmpe275.api.model.Address;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;
import edu.sjsu.cmpe275.api.repository.EmployerRepository;
import edu.sjsu.cmpe275.api.service.intefaces.IEmployeeManagementService;
import edu.sjsu.cmpe275.utils.EmployeeUtils;
import edu.sjsu.cmpe275.utils.EmployerUtils;

@Controller
public class EmployeeAPIController implements IEmployeeAPI {
	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private EmployerRepository employerRepository;
	
	@Autowired
	private IEmployeeManagementService employeeManagementService;

	@Override
	public ResponseEntity<Employee> getEmployee(Long id, String format) {
		HttpHeaders headers = httpResponseHeaderSetup(format);
		Optional<Employee> employeeWrapper = employeeRepository.findById(id);
		if (employeeWrapper.isPresent()) {
			Employee employee = employeeWrapper.get();
			EmployeeUtils.fetchLazyAttributeFromEmployee(employee);
			return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);

		} else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Employee> deleteEmployee(Long id, String format) {
		HttpHeaders headers = httpResponseHeaderSetup(format);

		Optional<Employee> employeeWrapper = employeeRepository.findById(id);
		if (employeeWrapper.isPresent()) {
			Employee employee = employeeWrapper.get();
			if(employeeManagementService.deleteEmployee(employee)) {
				return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
			}else {
				return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
			}
		} else {
			return new ResponseEntity<Employee>(HttpStatus.NOT_FOUND);
		}
	}

	@Override
	public ResponseEntity<Employee> createEmployee(String name, String email, String title, String street, String city,
			String state, String zip, Long employerId, Long managerId, String format) {
		HttpHeaders headers = httpResponseHeaderSetup(format);
		Optional<Employee> employeeByMail = employeeRepository.findByEmail(email);
		Employee employee = new Employee();

		if (employeeByMail.isPresent() || !EmployerUtils.assignEmployer(employerRepository, employerId, employee)
				|| !EmployeeUtils.assignManager(employeeRepository, managerId, employee)) {
			return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
		}
		employee.setName(name);
		employee.setEmail(email);
		employee.setTitle(title);
		Address addr = new Address();
		addr.setStreet(street);
		addr.setCity(city);
		addr.setState(state);
		addr.setZip(zip);
		employee.setAddress(addr);
		employeeRepository.save(employee);

		return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<Employee> updateEmployee(Long id, String name, String email, String title, String street,
			String city, String state, String zip, Long employerId, Long managerId, String format) {
		HttpHeaders headers = httpResponseHeaderSetup(format);
		if (id == null || email == null || employerId == null || name == null) {
			return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
		}

		Optional<Employee> employeeById = employeeRepository.findById(id);
		if (employeeById.isPresent()) {
			Employee employee = employeeById.get();
			if (!email.equals(employee.getEmail())) {
				Optional<Employee> employeeByMail = employeeRepository.findByEmail(email);
				if (employeeByMail.isPresent()) {
					return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
				}
				employee.setEmail(email);
			}
			employee.setName(name);
			employee.setTitle(title);

			Address address = new Address();
			address.setCity(city);
			address.setState(state);
			address.setStreet(street);
			address.setZip(zip);

			if(!employeeManagementService.updateEmployee(employee, employerId, managerId)) {
				return new ResponseEntity<Employee>(headers, HttpStatus.BAD_REQUEST);
			}
			EmployeeUtils.fetchLazyAttributeFromEmployee(employee);
			return new ResponseEntity<Employee>(employee, headers, HttpStatus.OK);

		} else {
			return new ResponseEntity<Employee>(headers, HttpStatus.NOT_FOUND);
		}
	}

	private HttpHeaders httpResponseHeaderSetup(String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");
		return headers;
	}

}
