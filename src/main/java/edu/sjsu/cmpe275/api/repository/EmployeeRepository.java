package edu.sjsu.cmpe275.api.repository;

import edu.sjsu.cmpe275.api.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {

}