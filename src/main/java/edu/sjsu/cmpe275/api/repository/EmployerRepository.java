package edu.sjsu.cmpe275.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;

public interface EmployerRepository extends CrudRepository<Employer, Long> {
	@Transactional(readOnly = true)
	Optional<Employer> findByName(String name);

}
