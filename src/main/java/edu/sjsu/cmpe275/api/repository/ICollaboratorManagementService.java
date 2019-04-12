package edu.sjsu.cmpe275.api.repository;

import edu.sjsu.cmpe275.api.model.Employee;

public interface ICollaboratorManagementService {

	void addCollaborator(Employee employee1, Employee employee2);

	boolean removeCollaborator(Employee employee1, Employee employee2);

}