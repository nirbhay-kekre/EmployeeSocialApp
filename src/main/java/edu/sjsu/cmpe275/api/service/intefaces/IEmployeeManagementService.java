package edu.sjsu.cmpe275.api.service.intefaces;

import edu.sjsu.cmpe275.api.model.Employee;

/**
 * Employee Management Service interface
 *
 * @author nirbhaykekre
 */
public interface IEmployeeManagementService {

	/**
	 * updates the given Employee object, returns true if update is successful.<br>
	 * Transactional method Changing an employee’s employer through the employerId
	 * follows following steps.<br>
	 * <br>
	 * If this person currently has a manager foo, all his/her current reports will
	 * report to foo.<br>
	 * If this person currently does not have a manager, all his/er reports will
	 * changed to not have a manager.<br>
	 * This person’s new manager (changed through the managerId parameter) must
	 * belong to the new company as well, or does not have a new manager.
	 *
	 * @param employee   Target employee object
	 * @param name       name to be updated
	 * @param email      email to be updated, must be unique
	 * @param title      title of the employee
	 * @param street     address street
	 * @param city       address city
	 * @param state      address state
	 * @param zip        address zip
	 * @param employerId employer's id
	 * @param managerId  manager's id
	 * @return boolean, true if update is successful
	 */
	public boolean updateEmployee(Employee employee, String name, String email, String title, String street,
			String city, String state, String zip, Long employerId, Long managerId);

	/**
	 * Deletes the given employee, if the employee has no reports.
	 *
	 * @param employee
	 * @return boolean, true if delete is successful
	 */
	public boolean deleteEmployee(Employee employee);

	/**
	 * Gets Employee object from the repository for the given employeeId, this
	 * method returns null if employee does not exist
	 *
	 * @param employeeId target employee id
	 * @return Employee object, returns null if employee does not exist
	 */
	public Employee getEmployee(Long employeeId);

	/**
	 * Creates and stores the new employee into the repository
	 *
	 * @param employee   Target employee object
	 * @param name       name to be updated
	 * @param email      email to be updated, must be unique
	 * @param title      title of the employee
	 * @param street     address street
	 * @param city       address city
	 * @param state      address state
	 * @param zip        address zip
	 * @param employerId employer's id
	 * @param managerId  manager's id
	 * @return created Employee object, returns null if bad request
	 */
	public Employee createEmployee(String name, String email, String title, String street, String city, String state,
			String zip, Long employerId, Long managerId);

	/**
	 * Gets Employee object from the repository for the given email, this method
	 * returns null if employee does not exist
	 *
	 * @param email target employee email
	 * @return Employee object, returns null if employee does not exist
	 */
	public Employee getEmployeeByEmail(String email);
}