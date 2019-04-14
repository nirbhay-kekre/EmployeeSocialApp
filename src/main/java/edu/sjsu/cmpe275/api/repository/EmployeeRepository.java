package edu.sjsu.cmpe275.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	@Transactional(readOnly = true)
	Optional<Employee> findByEmail(String email);

	@Transactional(readOnly = true)
	List<Employee> findByEmployer(Employer employer);

}