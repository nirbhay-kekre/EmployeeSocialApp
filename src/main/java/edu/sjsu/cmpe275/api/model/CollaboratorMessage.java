package edu.sjsu.cmpe275.api.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class CollaboratorMessage {
	private String message;

	public CollaboratorMessage() {
		
	}
	public CollaboratorMessage(String message) {
		super();
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
