package edu.sjsu.cmpe275.api.service.implementation;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;
import edu.sjsu.cmpe275.api.service.intefaces.ICollaboratorManagementService;

/**
 * Collaborator Services
 *
 * @author nirbhaykekre
 */
@Service
public class CollaboratorManagementService implements ICollaboratorManagementService {

	private EmployeeRepository employeeRepository;

	@Autowired
	public CollaboratorManagementService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

	/**
	 * Makes given employee1 and employee2 collaborator
	 *
	 * @param employee1 employee instance
	 * @param employee2 employee instance
	 */
	@Override
	@Transactional
	public void addCollaborator(Employee employee1, Employee employee2) {
		List<Employee> collaborators = employee1.getCollaborators();
		if (collaborators == null) {
			collaborators = new ArrayList<>();
			employee1.setCollaborators(collaborators);
		}
		if (!collaborators.contains(employee2)) {
			collaborators.add(employee2);
			employeeRepository.save(employee1);
		}

		collaborators = employee2.getCollaborators();
		if (collaborators == null) {
			collaborators = new ArrayList<>();
			employee2.setCollaborators(collaborators);
		}
		if (!collaborators.contains(employee1)) {
			collaborators.add(employee1);
			employeeRepository.save(employee2);
		}
	}

	/**
	 * Removes given employee1 and employee2 collaboration.
	 *
	 * @param employee1 employee instance
	 * @param employee2 employee instance
	 * @return boolean, returns true if success
	 */
	@Override
	@Transactional
	public boolean removeCollaborator(Employee employee1, Employee employee2) {
		List<Employee> collaborators = employee1.getCollaborators();
		if (collaborators == null || !collaborators.contains(employee2)) {
			return false;
		}
		collaborators.remove(employee2);
		collaborators = employee2.getCollaborators();
		if (collaborators == null || !collaborators.contains(employee1)) {
			return false;
		}
		collaborators.remove(employee1);
		employeeRepository.save(employee1);
		employeeRepository.save(employee2);
		return true;

	}
}
