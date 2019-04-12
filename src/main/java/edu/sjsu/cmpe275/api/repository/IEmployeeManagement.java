package edu.sjsu.cmpe275.api.repository;

import edu.sjsu.cmpe275.api.model.Employee;

public interface IEmployeeManagement {
	public boolean updateEmployee(Employee employee, Long employerId, Long managerId);
}