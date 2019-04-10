package edu.sjsu.cmpe275.api.controller.implementation;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;

import edu.sjsu.cmpe275.api.controller.interfaces.ICollaborationAPI;
import edu.sjsu.cmpe275.api.model.CollaboratorMessage;


@Controller
public class CollaboratorAPIController implements ICollaborationAPI {

	@Override
	public ResponseEntity<CollaboratorMessage> addCollaborator(Long id1, Long id2, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		// TODO: API logic

		return new ResponseEntity<CollaboratorMessage>(new CollaboratorMessage("adding"), headers, HttpStatus.NOT_IMPLEMENTED);
	}

	@Override
	public ResponseEntity<CollaboratorMessage> removeCollaborator(Long id1, Long id2, String format) {
		String type = "application/" + format.toLowerCase();
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-Type", type + "; charset=UTF-8");

		// TODO: API logic

		return new ResponseEntity<CollaboratorMessage>(new CollaboratorMessage("removing"), headers, HttpStatus.NOT_IMPLEMENTED);
	}

}
