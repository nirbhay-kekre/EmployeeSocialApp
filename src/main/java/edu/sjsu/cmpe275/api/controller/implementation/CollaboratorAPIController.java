package edu.sjsu.cmpe275.api.controller.implementation;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.ICollaborationAPI;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.ResponseMessage;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;
import edu.sjsu.cmpe275.api.repository.ICollaboratorManagementService;

@Controller
public class CollaboratorAPIController implements ICollaborationAPI {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ICollaboratorManagementService collaboratorManagementService;

	@Override
	public ResponseEntity<ResponseMessage> addCollaborator(Long id1, Long id2, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		Optional<Employee> employeeById1 = employeeRepository.findById(id1);
		Optional<Employee> employeeById2 = employeeRepository.findById(id2);
		if (!employeeById1.isPresent() || !employeeById2.isPresent()) {
			return new ResponseEntity<ResponseMessage>(headers, HttpStatus.NOT_FOUND);
		}
		Employee employee1 = employeeById1.get();
		Employee employee2 = employeeById2.get();

		collaboratorManagementService.addCollaborator(employee1, employee2);

		return new ResponseEntity<ResponseMessage>(
				new ResponseMessage("Collaborators " + id1 + ", " + id2 + " are added"), headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseMessage> removeCollaborator(Long id1, Long id2, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		Optional<Employee> employeeById1 = employeeRepository.findById(id1);
		Optional<Employee> employeeById2 = employeeRepository.findById(id2);
		if (!employeeById1.isPresent() || !employeeById2.isPresent()) {
			return new ResponseEntity<ResponseMessage>(headers, HttpStatus.NOT_FOUND);
		}
		Employee employee1 = employeeById1.get();
		Employee employee2 = employeeById2.get();

		if (!collaboratorManagementService.removeCollaborator(employee1, employee2)) {
			return new ResponseEntity<ResponseMessage>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ResponseMessage>(
				new ResponseMessage("Collaborators " + id1 + ", " + id2 + " are removed"), headers, HttpStatus.OK);
	}

}
