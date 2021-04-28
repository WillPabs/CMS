package com.fdmgroup.fdmcmsgroupproject.model;

import java.util.*;

import javax.persistence.*;


/**
 * User: POJO to model a User object and map it to the database
 *
 * @Version 1.0
 */
@Entity
@Table(name = "CMS_USER")
@NamedQueries({ @NamedQuery(name = "userFindAll", query = "SELECT u FROM User u"),
	@NamedQuery(name = "userFindByUsername", query = "SELECT u FROM User u WHERE u.username = :username"),
	@NamedQuery(name = "userListUsernames", query = "SELECT u.username FROM User u"),
	@NamedQuery(name = "userListEmails", query = "SELECT u.email FROM User u") })

public class User {

	@Id
	@Column(name = "USER_ID", length = 4, nullable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_gen")
	@SequenceGenerator(name = "user_id_gen", sequenceName = "user_id_seq", allocationSize = 1)
	private int userId;

	@Column(name = "USERNAME", length = 30, nullable = false, unique = true)
	private String username;

	@Column(name = "PASSWORD", length = 30, nullable = false)
	private String password;

	@Column(name = "FIRST_NAME", length = 30)
	private String firstName;

	@Column(name = "LAST_NAME", length = 30)
	private String lastName;

	@Column(name = "EMAIL", length = 30, nullable = false, unique = true)
	private String email;

	@ManyToOne
	@JoinColumn(name = "fk_DEPARTMENT_ID")
	private Department department; 

	@ManyToMany(fetch = FetchType.EAGER, cascade=CascadeType.MERGE)
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;

	/**
	 * User constructor for testing purposes
	 * 
	 * @param userId
	 * @param username
	 * @param password
	 * @param firstName
	 * @param lastName
	 * @param email
	 */
	public User(int userId, String username, String password, String firstName, String lastName, String email) {
		this(username, password, firstName, lastName, email);
		this.userId = userId;
	}
	
	public User(String username, String password, String firstName, String lastName, String email) {
		this(username, password, email);
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public User(String username, String password, String email) {
		this();
		this.username = username;
		this.password = password;
		this.email = email;
	}

	public User() {
		super();
		this.roles = new ArrayList<>();
	}

	public int getId() {
		return userId;
	}

	public void setId(int id) {
		this.userId = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Department getDepartment() {
		return department;
	}

	public void setDepartment(Department department) {
		this.department = department;
	}
	
	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	
	public void addRole(Role role) {
		roles.add(role);
		role.getUserList().add(this);
	}
	
	public void removeRole(Role role) {
		roles.remove(role);
		role.getUserList().remove(this);
	}
	
	public int getRoleIdsSum() {
		int roleIdsSum = 0;
		for(int i = 0; i < roles.size(); i++) {
			roleIdsSum += roles.get(i).getRoleId();
		}
		return roleIdsSum;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", firstName="
				+ firstName + ", lastName=" + lastName + ", email=" + email + ", department=" + department + ", roles="
				+ roles + "]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		result = prime * result + userId;
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		User other = (User) obj;
		if(email == null) {
			if(other.email != null)
				return false;
		} else if(!email.equals(other.email))
			return false;
		if(firstName == null) {
			if(other.firstName != null)
				return false;
		} else if(!firstName.equals(other.firstName))
			return false;
		if(lastName == null) {
			if(other.lastName != null)
				return false;
		} else if(!lastName.equals(other.lastName))
			return false;
		if(password == null) {
			if(other.password != null)
				return false;
		} else if(!password.equals(other.password))
			return false;
		if(userId != other.userId)
			return false;
		if(username == null) {
			if(other.username != null)
				return false;
		} else if(!username.equals(other.username))
			return false;
		return true;
	}

}
