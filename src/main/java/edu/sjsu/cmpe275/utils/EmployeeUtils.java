package edu.sjsu.cmpe275.utils;

import java.util.Optional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;

public class EmployeeUtils {
	
	
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
		employee.getCollaborators().size();
	}
}
