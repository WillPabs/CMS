	package com.fdmgroup.fdmcmsgroupproject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fdmgroup.fdmcmsgroupproject.model.Department;
import com.fdmgroup.fdmcmsgroupproject.model.Role;
import com.fdmgroup.fdmcmsgroupproject.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
public class UserTest implements ApplicationContextAware {

	private User user;
	private Department mockDepartment;
	private Role mockRole;
	private Role role1;
	private Role role3;
	private Role role5;
	private ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
	
	@Before
	public void setUp() throws Exception {
		user = (User)context.getBean("user");
	}

	@Test
	public void testUser_hasSettersAndGetters_ForId() {
		user.setId(1);
		assertNotNull(user.getId());
	}

	@Test
	public void testUser_hasSettersAndGetters_ForUsername() {
		user.setUsername("jdoe");
		assertNotNull(user.getUsername());
	}
	
	@Test
	public void testUser_hasSettersAndGetters_ForPassword() {
		user.setPassword("qwerty");
		assertNotNull(user.getPassword());
	}
	
	@Test
	public void testUser_hasSettersAndGetters_ForFirstName() {
		user.setFirstName("john");
		assertNotNull(user.getFirstName());
	}
	
	@Test
	public void testUser_hasSettersAndGetters_ForLastName() {
		user.setLastName("doe");
		assertNotNull(user.getLastName());
	}
	
	@Test
	public void testUser_hasSettersAndGetters_ForEmail() {
		user.setEmail("john.doe@some.com");
		assertNotNull(user.getEmail());
	}
	
	@Test
	public void testUser_hasSettersAndGettersForDepartment() {
		mockDepartment = mock(Department.class);
		user.setDepartment(mockDepartment);
		assertNotNull(user.getDepartment());
	}
	
	@Test
	public void testUser_hasDepartmentDependency() {
		mockDepartment = mock(Department.class);
		user.setDepartment(mockDepartment);
		user.getDepartment().getDepartmentName();
		verify(mockDepartment, times(1)).getDepartmentName();
	}
	
	@Test
	public void testUser_hasSettersAndGetters_ForRolesList() {
		mockRole = mock(Role.class);
		List<Role> mockRoles = new ArrayList<Role>();
		mockRoles.add(mockRole);
		
		user.setRoles(mockRoles);
		assertNotNull(user.getRoles());
	}
	
	@Test
	public void testUser_hasSettersAndGetters_ForSingleRole() {
		List<Role> emptyRoles = new ArrayList<Role>();
		List<Role> mockRoles = new ArrayList<Role>();
		mockRole = mock(Role.class);
		user.setRoles(mockRoles);
		user.addRole(mockRole);
		assertNotSame(emptyRoles, user.getRoles());
	}
	
	@Test
	public void testUser_CanSumRoleIds() {
		List<Role> mockRoles = new ArrayList<Role>();
		role1 = new Role();
		role3 = new Role();
		role5 = new Role();
		role1.setRoleId(1);
		role3.setRoleId(3);
		role5.setRoleId(5);
		mockRoles.add(role1);
		mockRoles.add(role3);
		mockRoles.add(role5);
		user.setRoles(mockRoles);
		int expectedRoleIdsSum = 9;
		assertEquals(expectedRoleIdsSum, user.getRoleIdsSum());
	}

}
