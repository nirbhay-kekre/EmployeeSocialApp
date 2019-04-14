package edu.sjsu.cmpe275.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.service.intefaces.ICollaboratorManagementService;

@Service
public class CollaboratorManagementService implements ICollaboratorManagementService {

	private EmployeeRepository employeeRepository;

	@Autowired
	public CollaboratorManagementService(EmployeeRepository employeeRepository) {
		this.employeeRepository = employeeRepository;
	}

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
