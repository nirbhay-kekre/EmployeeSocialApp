package edu.sjsu.cmpe275.api.service.intefaces;

import edu.sjsu.cmpe275.api.model.Employer;

public interface IEmployerManagementService {

	/**
	 * Gets Employee with the given id<br>
	 * <br>
	 * If Employer with employerId doesn't exist, method returns null
	 * <br>
	 * 
	 * @param id employerId for which the employer needs to be fetched.
	 * @return Employee, if the corresponding employee is present with the given id.
	 */
	public Employer getEmployer(Long id);

	/**
	 * Delete's the employer corresponding to the given employer object<br>
	 * <br>
	 * If Employer with given employer object doesn't exist, method returns false indicating
	 * <br>
	 * @param employer object which needs to be deleted.
	 * @return boolean, true if employer is deleted successfully.
	 */
	public boolean deleteEmployer(Employer employer);

	/**
	 * Creates Employer with the given parameters.<br>
	 * <br>
	 * if there exists an employer with the given name the method returns null<br>
	 * @param name name of the new employer
	 * @param description description of the new employer
	 * @param street street of the new employer
	 * @param city city of the new employer
	 * @param state state of the new employer
	 * @param zip zip code of the new employer
	 * @return Employer, if the employer is created successfully.
	 */
	public Employer createEmployer(String name, String description, String street, String city, String state,
			String zip);
	/**
	 * Updates Employer with the given parameters, totally replacing the older values<br>
	 * <br>
	 * if there exists any other employer with the given name the method returns null<br>
	 * @param name desired name for the updated employer
	 * @param description desired description for the updated employer
	 * @param street desired street name for the updated employer
	 * @param city desired city name for the updated employer
	 * @param state desired state for the updated employer
	 * @param zip desired zip code for the updated employer
	 * @param employer the object which needs to be updated
	 * @return Employer, if the employer is created successfully.
	 */
	public Employer updateEmployer(String name, String description, String street, String city, String state,
			String zip, Employer employer);

}
