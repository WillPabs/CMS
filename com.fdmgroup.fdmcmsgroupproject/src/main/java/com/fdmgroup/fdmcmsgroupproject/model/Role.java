package com.fdmgroup.fdmcmsgroupproject.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Role: POJO to model a Role object to database
 * 
 * @version 1.0
 */
@Entity
@Table(name="ROLE")
public class Role {
	
	@Id
	@Column(name="ROLE_ID", length = 4, nullable = false, updatable = false)
//	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "role_id_gen")
//	@SequenceGenerator(name = "role_id_gen", sequenceName = "role_id_seq", allocationSize = 1)
	private int roleId;
	
	@Column(name="ROLE_NAME", length = 30, nullable = false, unique = true)
	private String roleName;
	
	@ManyToMany(mappedBy="roles")
	private List<User> userList;
	
	public Role() {
		super();
		this.userList = new ArrayList<>();
	}
	
	/**
	 * Role
	 * 
	 * @param roleId
	 * @param roleName
	 */
	public Role(int roleId, String roleName) {
		this();
		this.roleId = roleId;
		this.roleName = roleName;
	}
	
	public int getRoleId() {
		return roleId;
	}
	
	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}
	
	public String getRoleName() {
		return roleName;
	}
	
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
		
}
