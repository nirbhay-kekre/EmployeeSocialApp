package edu.sjsu.cmpe275.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import edu.sjsu.cmpe275.api.model.Employer;

/**
 * CRUD repository for employer, all methods are transactional by default.
 * 
 * @author nirbhaykekre
 */
public interface EmployerRepository extends CrudRepository<Employer, Long> {

	/**
	 * Transactional method for retrieving Employer by name
	 * 
	 * @param name name of the employer
	 * @return Employer object wrapped in Optional
	 */
	@Transactional(readOnly = true)
	Optional<Employer> findByName(String name);

}
