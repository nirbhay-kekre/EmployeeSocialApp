package edu.sjsu.cmpe275.api.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.service.intefaces.IEmployeeManagementService;
import edu.sjsu.cmpe275.utils.EmployeeUtils;
import edu.sjsu.cmpe275.utils.EmployerUtils;

@Service
public class EmployeeManagementService implements IEmployeeManagementService {

	private EmployeeRepository employeeRepository;
	private EmployerRepository employerRepository;

	@Autowired
	public EmployeeManagementService(EmployeeRepository employeeRepository, EmployerRepository employerRepository) {
		this.employeeRepository = employeeRepository;
		this.employerRepository = employerRepository;
	}

	@Transactional
	public boolean updateEmployee(Employee employee, Long employerId, Long managerId) {
		Employer prevEmployer = employee.getEmployer();
		Employee prevManager = employee.getManager();
		if (employerId != prevEmployer.getId()) {
			if (EmployerUtils.assignEmployer(employerRepository, employerId, employee)
					&& EmployeeUtils.assignManager(employeeRepository, managerId, employee)) {
				List<Employee> reports = employee.getReports();
				employee.setReports(new ArrayList<>());
				if (reports != null) {
					for (Employee report : reports) {
						report.setManager(prevManager);
						employeeRepository.save(report);
					}
				}
			} else {
				return false;
			}
		} else {
			if (!EmployeeUtils.assignManager(employeeRepository, managerId, employee)) {
				return false;
			}
		}
		employeeRepository.save(employee);
		return true;
	}

	@Transactional
	public boolean deleteEmployee(Employee employee) {
		if (employee.getReports() == null || employee.getReports().isEmpty()) {
			EmployeeUtils.fetchLazyAttributeFromEmployee(employee);
			for(Employee collaborator : employee.getCollaborators()) {
				collaborator.getCollaborators().remove(employee);
				employeeRepository.save(collaborator);
			}
			employeeRepository.delete(employee);
			return true;
		} else {
			return false;
		}
	}

}
