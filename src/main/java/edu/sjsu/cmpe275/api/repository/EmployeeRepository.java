package edu.sjsu.cmpe275.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import edu.sjsu.cmpe275.api.model.Employee;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
	Optional<Employee> findByEmail(String email);
}