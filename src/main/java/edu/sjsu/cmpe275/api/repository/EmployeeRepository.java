package edu.sjsu.cmpe275.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;

/**
 * CRUD repository for employee, all methods are transactional by default.
 * 
 * @author nirbhaykekre
 */
public interface EmployeeRepository extends CrudRepository<Employee, Long> {

	/**
	 * Transactional method for retrieving Employee by email
	 * 
	 * @param name name of the employer
	 * @return Employee object wrapped in Optional
	 */
	@Transactional(readOnly = true)
	Optional<Employee> findByEmail(String email);

	/**
	 * Transactional method for retrieving all employees by employer
	 * 
	 * @param name name of the employer
	 * @return list of Employee
	 */
	@Transactional(readOnly = true)
	List<Employee> findByEmployer(Employer employer);

}