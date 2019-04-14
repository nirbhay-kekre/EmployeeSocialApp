package edu.sjsu.cmpe275.api.service.intefaces;

import edu.sjsu.cmpe275.api.model.Employee;

public interface ICollaboratorManagementService {

	/**
	 * makes given employee1 and employee2 collaborator
	 * 
	 * @param employee1 employee instance
	 * @param employee2 employee instance
	 */
	void addCollaborator(Employee employee1, Employee employee2);

	/**
	 * Removes given employee1 and employee2 collaboration.
	 * 
	 * @param employee1 employee instance
	 * @param employee2 employee instance
	 * @return boolean, returns true if success
	 */
	boolean removeCollaborator(Employee employee1, Employee employee2);

}