package edu.sjsu.cmpe275.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Address;
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

	/**
	 * @param employee
	 * @param employerId
	 * @param managerId
	 * @return
	 */
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
	
	@Transactional(readOnly=true)
	public Employee getEmployee(Long employeeId) {
		Optional<Employee> employeeWrapper = employeeRepository.findById(employeeId);
		if (employeeWrapper.isPresent()) {
			Employee employee = employeeWrapper.get();
			EmployeeUtils.fetchLazyAttributeFromEmployee(employee);
			return employee;

		} else {
			return null;
		}
	}
	
	@Transactional
	public Employee createEmployee(String name, String email, String title, String street, String city,
			String state, String zip, Long employerId, Long managerId) {
		
		Employee employee = new Employee();
		Optional<Employee> employeeByMail = employeeRepository.findByEmail(email);
		if (employeeByMail.isPresent() || !EmployerUtils.assignEmployer(employerRepository, employerId, employee)
				|| !EmployeeUtils.assignManager(employeeRepository, managerId, employee)) {
			return null;
		}
		employee.setName(name);
		employee.setEmail(email);
		employee.setTitle(title);
		Address addr = new Address();
		addr.setStreet(street);
		addr.setCity(city);
		addr.setState(state);
		addr.setZip(zip);
		employee.setAddress(addr);
		return employeeRepository.save(employee);
		
	}

}
