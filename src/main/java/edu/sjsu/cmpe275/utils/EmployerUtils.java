package edu.sjsu.cmpe275.utils;

import java.util.Optional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.repository.EmployerRepository;

/**
 * Employer utilities
 * 
 * @author nirbhaykekre
 */
public class EmployerUtils {

	/**
	 * Assigns Employer with employerId to an Employee.<br>
	 * <br>
	 * If employerId is null, method returns with false because employerId is a
	 * mandatory field for an employee.<br>
	 * If there doesn't exist an employer with employerId, method return false
	 * indicating failure.<br>
	 * <br>
	 * <b>Note: this utility won't save employee into database, all changes are
	 * in-memory</b>
	 * 
	 * @param employerRepository CRUD repository for employer
	 * @param employerId         employer Id to be assigned
	 * @param employee           target employee object
	 * @return boolean, true if employer is assigned successfully.
	 */
	public static boolean assignEmployer(EmployerRepository employerRepository, Long employerId, Employee employee) {
		if (employerId == null) {
			return false;
		}
		Optional<Employer> employerWrapper = employerRepository.findById(employerId);
		if (employerWrapper.isPresent()) {
			Employer employer = employerWrapper.get();
			employee.setEmployer(employer);
		} else {
			return false;
		}
		return true;
	}
}
