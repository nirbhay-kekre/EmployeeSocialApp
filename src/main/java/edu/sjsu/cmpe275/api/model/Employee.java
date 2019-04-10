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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@XmlRootElement
@Entity
//@JsonInclude(Include.NON_NULL)
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	private String name;

	@Column(unique=true, length=100)
	private String email;

	private String title;

	@Embedded
	private Address address;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	private Employer employer;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "manager_id")
	@JsonIgnoreProperties(value = {"manager", "reports", "employer", "address"})
	private Employee manager;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "manager")
	@JsonIgnoreProperties(value = {"manager", "reports", "employer", "address"})
	private List<Employee> reports = new ArrayList<>();

//	
//	private List<Employee> collaborators;

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
//
//	public List<Employee> getCollaborators() {
//		return collaborators;
//	}
//
//	public void setCollaborators(List<Employee> collaborators) {
//		this.collaborators = collaborators;
//	}

}
