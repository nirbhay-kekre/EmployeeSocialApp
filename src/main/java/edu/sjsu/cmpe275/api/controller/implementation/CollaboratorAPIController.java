package edu.sjsu.cmpe275.api.controller.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.ICollaborationAPI;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.ResponseMessage;
import edu.sjsu.cmpe275.api.service.implementation.EmployeeManagementService;
import edu.sjsu.cmpe275.api.service.intefaces.ICollaboratorManagementService;

/**
 * Controller for Collaborator
 *
 * @author nirbhaykekre
 */
@Controller
public class CollaboratorAPIController implements ICollaborationAPI {

	@Autowired
	private EmployeeManagementService employeeService;

	@Autowired
	private ICollaboratorManagementService collaboratorManagementService;

	/**
	 * This makes the two employees with the given IDs collaborators with each
	 * other. <br>
	 * <br>
	 * If either employee does not exist, return 404. <br>
	 * <br>
	 * If the two employees are already collaborators, do nothing, just return
	 * 200.<br>
	 * <br>
	 * Otherwise, Record this collaboration relation. If all is successful, return
	 * HTTP code 200 and any informative text message in the given format in the
	 * HTTP payload.
	 *
	 * @param id1: Employee id
	 * @param id2: Employee id
	 * @param format: output format possible values are json or xml.
	 * @return ResponseEntity<ResponseMessage> with response message indicating
	 *         collaborator added
	 */
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

	/**
	 * This request removes the collaboration relation between the two employees
	 * passed as id1 and id2.<br>
	 * <br>
	 * If either employee does not exist, return 404.<br>
	 * <br>
	 * If the two employees are not collaborators, return 404.<br>
	 * <br>
	 * Otherwise, Remove this collaboration relation. Return HTTP code 200 and a
	 * meaningful text message if all is successful.
	 *
	 * @param id1: Employee id
	 * @param id2: Employee id
	 * @param format: output format possible values are json or xml.
	 * @return ResponseEntity<ResponseMessage> with response message indicating
	 *         collaborator removed
	 */
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
