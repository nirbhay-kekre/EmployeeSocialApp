package edu.sjsu.cmpe275.api.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Employee JPA entity
 *
 * @author nirbhaykekre
 */
@Entity
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	@Column(unique = true, length = 100)
	private String email;

	private String title;

	@Embedded
	private Address address;

	/**
	 * Employee's employer, it is lazy fetched.<br>
	 * <br>
	 * JsonIgnoreProperties added to not include address and description while
	 * accessing Employee entity
	 */
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "employer_id", referencedColumnName = "id")
	@JsonIgnoreProperties(value = { "address", "description" })
	private Employer employer;

	/**
	 * Employee's manager, it is lazy fetched.<br>
	 * <br>
	 * JsonIgnoreProperties added to not include email, manager, reports, employer,
	 * address, collaborators while accessing Employee entity
	 */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	@JsonIgnoreProperties(value = { "email", "manager", "reports", "employer", "address", "collaborators" })
	private Employee manager;

	/**
	 * Employee's reports, it is lazy fetched.<br>
	 * <br>
	 * JsonIgnoreProperties added to not include email, manager, reports, employer,
	 * address, collaborators while accessing Employee entity
	 */
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
	@JsonIgnoreProperties(value = { "email", "manager", "reports", "employer", "address", "collaborators" })
	private List<Employee> reports = new ArrayList<Employee>();

	/**
	 * Employee's collaborators, it is lazy fetched.<br>
	 * <br>
	 * JsonIgnoreProperties added to not include email, manager, reports, employer,
	 * address, collaborators while accessing Employee entity
	 */
	@ManyToMany
	@JoinTable(name = "COLLABORATION", joinColumns = {
			@JoinColumn(name = "collaboratingFrom", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "collaboratingTo", referencedColumnName = "id") })
	@JsonIgnoreProperties(value = { "email", "manager", "reports", "address", "collaborators" })
	private List<Employee> collaborators = new ArrayList<Employee>();

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Employer getEmployer() {
		return employer;
	}

	public void setEmployer(Employer employer) {
		this.employer = employer;
	}

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public List<Employee> getReports() {
		return reports;
	}

	public void setReports(List<Employee> reports) {
		this.reports = reports;
	}

	public List<Employee> getCollaborators() {
		return collaborators;
	}

	public void setCollaborators(List<Employee> collaborators) {
		this.collaborators = collaborators;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Employee))
			return false;
		Employee other = (Employee) obj;
		if (id != other.getId())
			return false;
		return true;
	}
}
