package edu.sjsu.cmpe275.utils;

import java.util.Optional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;

/**
 * Employee Utilities
 *
 * @author nirbhaykekre
 */
public class EmployeeUtils {

	/**
	 * Assigns Manager with managerId to an employee.<br>
	 * <br>
	 * If managerId is null, method returns true.<br>
	 * If Manager with managerId doesn't exist, method returns false indicating
	 * failure<br>
	 * If Manager's employer is not same as employee, method returns false
	 * indicating failure<br>
	 * otherwise, assigns the manager and returns true.<br>
	 * <br>
	 *
	 * <b>Note: this utility won't save employee into database, all changes are
	 * in-memory</b>
	 *
	 * @param employeeRepository CRUD repository for employee
	 * @param managerId          Manager id to be assigned to employee
	 * @param employee           Target employee
	 * @return boolean, true if manager is assigned successfully.
	 */
	public static boolean assignManager(EmployeeRepository employeeRepository, Long managerId, Employee employee) {
		if (managerId == null) {
			employee.setManager(null);
			return true;
		}
		Optional<Employee> managerWrapper = employeeRepository.findById(managerId);
		if (managerWrapper.isPresent()) {
			Employee manager = managerWrapper.get();
			if (!employee.getEmployer().equals(manager.getEmployer())) {
				return false;
			}
			employee.setManager(manager);
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Fetches all the lazy attributes of current employee
	 *
	 * @param employee target employee
	 */
	public static void fetchLazyAttributeFromEmployee(Employee employee) {
		// lazy fetch manager
		Employee manager = employee.getManager();
		if (manager != null) {
			manager.getEmail();
		}
		// lazy fetch reports
		employee.getReports().size();
		// lazy fetch employer
		employee.getEmployer().getName();
		// lazy fetch collaborators
		employee.getCollaborators().size();
	}
}
