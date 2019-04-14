package edu.sjsu.cmpe275.api.service.intefaces;

import edu.sjsu.cmpe275.api.model.Employer;

public interface IEmployerManagementService {

	/**
	 * Gets Employee with the given id<br>
	 * <br>
	 * If Employer with employerId doesn't exist, method returns null
	 * <br>
	 * 
	 * @param id
	 * @return Employee, if the corresponding employee is present with the given id.
	 */
	public Employer getEmployer(Long id);

	/**
	 * Delete's the employer corresponding to the given employer object<br>
	 * <br>
	 * If Employer with given employer object doesn't exist, method returns false indicating
	 * <br>
	 * @param employer
	 * @return boolean, true if employer is deleted successfully.
	 */
	public boolean deleteEmployer(Employer employer);

	/**
	 * Creates Employer with the given parameters.<br>
	 * <br>
	 * if there exists an employer with the given name the method returns null<br>
	 * @param name
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return Employer, if the employer is created successfully.
	 */
	public Employer createEmployer(String name, String description, String street, String city, String state,
			String zip);
	/**
	 * Updates Employer with the given parameters, totally replacing the older values<br>
	 * <br>
	 * if there exists any other employer with the given name the method returns null<br>
	 * @param name
	 * @param description
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param employer
	 * @return Employer, if the employer is created successfully.
	 */
	public Employer updateEmployer(String name, String description, String street, String city, String state,
			String zip, Employer employer);

}
