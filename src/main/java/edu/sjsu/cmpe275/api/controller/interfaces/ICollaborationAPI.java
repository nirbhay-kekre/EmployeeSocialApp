package edu.sjsu.cmpe275.api.controller.interfaces;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.sjsu.cmpe275.api.model.ResponseMessage;

/**
 * Collaborator API interface, defines request mapping for the collaborator
 * routes
 *
 * @author nirbhaykekre
 */
public interface ICollaborationAPI {

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
	@RequestMapping(value = "/collaborators/{id1}/{id2}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.PUT)
	ResponseEntity<ResponseMessage> addCollaborator(@PathVariable(value = "id1", required = true) Long id1,
			@PathVariable(value = "id2", required = true) Long id2,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);

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
	@RequestMapping(value = "/collaborators/{id1}/{id2}", produces = { MediaType.APPLICATION_JSON_VALUE,
			MediaType.APPLICATION_XML_VALUE }, method = RequestMethod.DELETE)
	ResponseEntity<ResponseMessage> removeCollaborator(@PathVariable(value = "id1", required = true) Long id1,
			@PathVariable(value = "id2", required = true) Long id2,
			@RequestParam(value = "format", defaultValue = "json", required = false) String format);
}
