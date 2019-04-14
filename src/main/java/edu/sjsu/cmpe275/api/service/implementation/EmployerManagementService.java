package edu.sjsu.cmpe275.api.service.implementation;

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
import edu.sjsu.cmpe275.api.service.intefaces.IEmployerManagementService;

@Service
public class EmployerManagementService implements IEmployerManagementService {

	@Autowired
	private EmployerRepository employerRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	/**
	 * Gets Employee with the given id<br>
	 * <br>
	 * If Employer with employerId doesn't exist, method returns null
	 * <br>
	 * 
	 * 
	 * @return Employee, if the corresponding employee is present with the given id.
	 */
	@Override
	@Transactional(readOnly = true)
	public Employer getEmployer(Long id) {
		// TODO Auto-generated method stub
		Optional<Employer> optionalEmployer = employerRepository.findById(id);
		if (optionalEmployer.isPresent()) {
			return optionalEmployer.get();
		} else
			return null;

	}

	/**
	 * Delete's the employer corresponding to the given employer object<br>
	 * <br>
	 * If Employer with given employer object doesn't exist, method returns false indicating
	 * <br>
	 * @param employer
	 * @return boolean, true if employer is deleted successfully.
	 */
	@Override
	@Transactional
	public boolean deleteEmployer(Employer employer) {

		if (employer != null) {
			List<Employee> employeesByEmployers = employeeRepository.findByEmployer(employer);
			if (employeesByEmployers.isEmpty()) {
				employerRepository.deleteById(employer.getId());
				return true;

			} else {
				return false;
			}

		}
		return false;
	}

	/**
	 * Creates Employer with the given parameters.<br>
	 * <br>
	 * if there exists an employer with the given name the method returns null<br>
	 * @param name
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return Employer, if the employer is created successfully.
	 */
	@Override
	@Transactional
	public Employer createEmployer(String name, String description, String street, String city, String state,
			String zip) {

		if (name == null) {
			return null;
		}

		Optional<Employer> employerWrapper = employerRepository.findByName(name);
		if (employerWrapper.isPresent()) {
			return null;
		}

		Employer employer = new Employer();
		employer.setName(name);
		employer.setDescription(description);
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		employer.setAddress(address);

		employer = employerRepository.save(employer);
		return employer;
	}

	/**
	 * Updates Employer with the given parameters, totally replacing the older values<br>
	 * <br>
	 * if there exists any other employer with the given name the method returns null<br>
	 * @param name
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param employer
	 * @return Employer, if the employer is created successfully.
	 */
	@Override
	@Transactional
	public Employer updateEmployer(String name, String description, String street, String city, String state,
			String zip, Employer employer) {

		if (!name.equals(employer.getName())) {
			Optional<Employer> employerByNewNameWrapper = employerRepository.findByName(name);
			if (employerByNewNameWrapper.isPresent()) {
				return null;
			}
		}
		employer.setName(name);
		employer.setDescription(description);
		Address address = new Address();
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setZip(zip);
		employer.setAddress(address);
		employer = employerRepository.save(employer);
		return employer;
	}

}
