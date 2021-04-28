package com.fdmgroup.fdmcmsgroupproject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Department: POJO to model a Department object to database
 * 
 * @version 1.0
 */
@Entity
@Table(name="DEPARTMENT")
@NamedQueries({
	@NamedQuery(name="findDeptIdByDeptName", query="SELECT d FROM Department d WHERE d.departmentName =:departmentName"),
	@NamedQuery(name="departmentFindAll", query="SELECT d FROM Department d")
})

public class Department {

	@Id
	@Column(name = "DEPARTMENT_ID", length = 4, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_gen")
	@SequenceGenerator(name = "department_id_gen", sequenceName = "department_id_seq", allocationSize = 1)
	private int departmentId;
	
	@Column(name = "DEPARTMENT_NAME", length = 30, nullable = false, unique = true)
	private String departmentName;
	
	@OneToMany(mappedBy  = "department")
	private List<User> listOfUsersPerDepartment;
	
	public Department() {
		super();
		this.listOfUsersPerDepartment = new ArrayList<>();
	}
	
	public Department(String departmentName) {
		this();
		this.departmentName=departmentName;
	}
	
	public Department(int departmentId, String departmentName) {
		this(departmentName);
		this.departmentId = departmentId;
	}

	public int getDepartmentId() {
		return departmentId;
	}

	public void setDepartmentId(int departmentId) {
		this.departmentId = departmentId;
	}

	public String getDepartmentName() {
		return departmentName;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	@Override
	public String toString() {
		return "Department [departmentId=" + departmentId + ", departmentName=" + departmentName + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + departmentId;
		result = prime * result + ((departmentName == null) ? 0 : departmentName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if(this == obj)
			return true;
		if(obj == null)
			return false;
		if(getClass() != obj.getClass())
			return false;
		Department other = (Department) obj;
		if(departmentId != other.departmentId)
			return false;
		if(departmentName == null) {
			if(other.departmentName != null)
				return false;
		} else if(!departmentName.equals(other.departmentName))
			return false;
		return true;
	}

}
