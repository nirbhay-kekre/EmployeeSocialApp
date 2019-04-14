package edu.sjsu.cmpe275.api.service.intefaces;

import edu.sjsu.cmpe275.api.model.Employee;

public interface IEmployeeManagementService {

	/**
	 * @param employee
	 * @param employerId
	 * @param managerId
	 * @return
	 */
	public boolean updateEmployee(Employee employee, Long employerId, Long managerId);

	public boolean deleteEmployee(Employee employee);

	public Employee getEmployee(Long employeeId);

	public Employee createEmployee(String name, String email, String title, String street, String city, String state,
			String zip, Long employerId, Long managerId);

	public Employee getEmployeeByEmail(String email);
}