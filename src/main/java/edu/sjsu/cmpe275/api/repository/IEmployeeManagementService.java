package edu.sjsu.cmpe275.api.repository;

import edu.sjsu.cmpe275.api.model.Employee;

public interface IEmployeeManagementService {
	public boolean updateEmployee(Employee employee, Long employerId, Long managerId);
	public boolean deleteEmployee(Employee employee);
}