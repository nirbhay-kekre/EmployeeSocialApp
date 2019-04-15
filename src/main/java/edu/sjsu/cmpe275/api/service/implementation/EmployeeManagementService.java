package edu.sjsu.cmpe275.api.service.implementation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Address;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;
import edu.sjsu.cmpe275.api.repository.EmployerRepository;
import edu.sjsu.cmpe275.api.service.intefaces.IEmployeeManagementService;
import edu.sjsu.cmpe275.utils.EmployeeUtils;
import edu.sjsu.cmpe275.utils.EmployerUtils;

/**
 * Employee Services
 *
 * @author nirbhaykekre
 */
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
	 * @param email      email to be updated
	 * @param title      title of the employee
	 * @param street     address street
	 * @param city       address city
	 * @param state      address state
	 * @param zip        address zip
	 * @param employerId employer's id
	 * @param managerId  manager's id
	 * @return boolean, true if update is successful
	 */
	@Override
	@Transactional
	public boolean updateEmployee(Employee employee, String name, String email, String title, String street,
			String city, String state, String zip, Long employerId, Long managerId) {
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
		employee.setEmail(email);
		employee.setName(name);
		employee.setTitle(title);
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		employee.setAddress(address);
		employeeRepository.save(employee);
		return true;
	}

	/**
	 * Deletes the given employee, if the employee has no reports.
	 *
	 * @param employee target employee object to be deleted
	 * @return boolean, true if delete is successful
	 */
	@Override
	@Transactional
	public boolean deleteEmployee(Employee employee) {
		if (employee.getReports() == null || employee.getReports().isEmpty()) {
			EmployeeUtils.fetchLazyAttributeFromEmployee(employee);
			for (Employee collaborator : employee.getCollaborators()) {
				collaborator.getCollaborators().remove(employee);
				employeeRepository.save(collaborator);
			}
			employeeRepository.delete(employee);
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Gets Employee object from the repository for the given employeeId, this
	 * method returns null if employee does not exist
	 *
	 * @param employeeId target employee id
	 * @return Employee object, returns null if employee does not exist
	 */
	@Override
	@Transactional(readOnly = true)
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

	/**
	 * Creates and stores the new employee into the repository
	 *
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
	@Override
	@Transactional
	public Employee createEmployee(String name, String email, String title, String street, String city, String state,
			String zip, Long employerId, Long managerId) {

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

	/**
	 * Gets Employee object from the repository for the given email, this method
	 * returns null if employee does not exist
	 *
	 * @param email target employee email
	 * @return Employee object, returns null if employee does not exist
	 */
	@Override
	@Transactional(readOnly = true)
	public Employee getEmployeeByEmail(String email) {
		Optional<Employee> employeeByMail = employeeRepository.findByEmail(email);
		if (employeeByMail.isPresent()) {
			return employeeByMail.get();
		}
		return null;
	}
}
