package edu.sjsu.cmpe275.api.repository;

import org.springframework.data.repository.CrudRepository;

import edu.sjsu.cmpe275.api.model.Employer;

public interface EmployerRepository extends CrudRepository<Employer, Long> {

}
