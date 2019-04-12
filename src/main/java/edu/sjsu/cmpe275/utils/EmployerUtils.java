package edu.sjsu.cmpe275.utils;

import java.util.Optional;

import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.repository.EmployerRepository;

public class EmployerUtils {
	public static boolean assignEmployer(EmployerRepository employerRepository, Long employerId, Employee employee) {
		if(employerId == null) {
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
