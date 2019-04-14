package edu.sjsu.cmpe275.api.service.implementation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import edu.sjsu.cmpe275.api.model.Address;
import edu.sjsu.cmpe275.api.model.Employee;
import edu.sjsu.cmpe275.api.model.Employer;
import edu.sjsu.cmpe275.api.repository.EmployeeRepository;
import edu.sjsu.cmpe275.api.repository.EmployerRepository;
import edu.sjsu.cmpe275.api.service.intefaces.IEmployerManagementService;


@Service
public class EmployerManagementService implements IEmployerManagementService {

	@Autowired
	private EmployerRepository employerRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	/**
	 * @author Rajat
	 * @return the employer object if found else return null
	 */
	@Override
	public Employer getEmployer(Long id) {
		// TODO Auto-generated method stub
		Optional<Employer> optionalEmployer =employerRepository.findById(id);
		if (optionalEmployer.isPresent()) {
			return optionalEmployer.get();
		}
		else return null;
		
	}

	/**
	 * @author Yash
	 * @return true if delete is success else false
	 * 
	 */
	@Override
	public boolean deleteEmployer(Employer employer) {
	
		if(employer!=null) {
			List<Employee> employeesByEmployers = employeeRepository.findByEmployer(employer);
			if(employeesByEmployers.isEmpty()) {
				employerRepository.deleteById(employer.getId());
				return true;
			
			}else {
				return false;
			}
			
		}
		return false;
	}

	/**
	 * @author Nirbhay
	 * 
	 */
	@Override
	public Employer createEmployer(String name, String description, String street, String city,
		String state, String zip) {
		
		if(name == null){
			return null;
		}
		
		Optional<Employer> employerWrapper = employerRepository.findByName(name);
		if(employerWrapper.isPresent()){
			return null; 
		}
		
		Employer employer = new Employer();
		employer.setName(name);
		employer.setDescription(description);
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		employer.setAddress(address);
		
		employer=employerRepository.save(employer);
		return employer;
	}

	@Override
	public Employer updateEmployer(Long id, String name, String description, String street, String city,
			String state, String zip, Employer employer) {
		
		if(!name.equals(employer.getName())){
			Optional<Employer> employerByNewNameWrapper = employerRepository.findByName(name);
			if(employerByNewNameWrapper.isPresent()) {
				return null;
			}
		}
		employer.setName(name);
		employer.setDescription(description);
		Address address = new Address();
		address.setStreet(street);
		address.setCity(city);
		address.setState(state);
		address.setZip(zip);
		employer.setAddress(address);
		employer=employerRepository.save(employer);
		return employer;
	}

}
