package edu.sjsu.cmpe275.api.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.jpa.repository.Query;
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