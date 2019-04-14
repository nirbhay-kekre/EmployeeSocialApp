package edu.sjsu.cmpe275.api.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.ICollaborationAPI;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.ResponseMessage;
import edu.sjsu.cmpe275.api.repository.EmployeeManagementService;
import edu.sjsu.cmpe275.api.service.intefaces.ICollaboratorManagementService;

@Controller
public class CollaboratorAPIController implements ICollaborationAPI {

	@Autowired
	private EmployeeManagementService employeeService;

	@Autowired
	private ICollaboratorManagementService collaboratorManagementService;

	@Override
	public ResponseEntity<ResponseMessage> addCollaborator(Long id1, Long id2, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		Employee employee1 = employeeService.getEmployee(id1);
		Employee employee2 = employeeService.getEmployee(id2);
		if (employee1 == null || employee2 == null) {
			return new ResponseEntity<ResponseMessage>(headers, HttpStatus.NOT_FOUND);
		}

		collaboratorManagementService.addCollaborator(employee1, employee2);

		return new ResponseEntity<ResponseMessage>(
				new ResponseMessage("Collaborators " + id1 + ", " + id2 + " are added"), headers, HttpStatus.OK);
	}

	@Override
	public ResponseEntity<ResponseMessage> removeCollaborator(Long id1, Long id2, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		Employee employee1 = employeeService.getEmployee(id1);
		Employee employee2 = employeeService.getEmployee(id2);
		if (employee1 == null || employee2 == null) {
			return new ResponseEntity<ResponseMessage>(headers, HttpStatus.NOT_FOUND);
		}

		if (!collaboratorManagementService.removeCollaborator(employee1, employee2)) {
			return new ResponseEntity<ResponseMessage>(headers, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<ResponseMessage>(
				new ResponseMessage("Collaborators " + id1 + ", " + id2 + " are removed"), headers, HttpStatus.OK);
	}

}
