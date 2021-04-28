package com.fdmgroup.fdmcmsgroupproject;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.fdmgroup.fdmcmsgroupproject.dao.GenericDao;
import com.fdmgroup.fdmcmsgroupproject.dao.UserDao;
import com.fdmgroup.fdmcmsgroupproject.model.User;

public class UserDaoTest {

	private static ApplicationContext context;
	private EntityManager em;
	private static UserDao dao;
	private User testUser;
	private User testUser2;
	private User testUser3;

	@Before
	public void setUp() throws Exception {
		em = GenericDao.getEmf().createEntityManager();
		testUser = new User();
		testUser2 = new User();
		testUser3 = new User();
		testUser.setUsername("TESTUSERNAME");
		testUser.setPassword("TESTPASS");
		testUser2.setUsername("TESTUSERNAME2");
		testUser2.setPassword("TESTPASS2");
		testUser3.setUsername("TESTUSERNAME3");
		testUser3.setPassword("TESTPASS3");
	}

	@After
	public void tearDown() throws Exception {
		if(dao.get(testUser.getId()) != null) {
			dao.remove(testUser.getId());
		}
		if(dao.get(testUser2.getId()) != null) {
			dao.remove(testUser2.getId());
		}
		if(dao.get(testUser3.getId()) != null) {
			dao.remove(testUser3.getId());
		}
		if(em.isOpen()) {
			em.close();
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("/WEB-INF/beans.xml");
		dao = context.getBean("userDao", UserDao.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		GenericDao.closeEMF();
	}

	@Test
	public void testAdd() {
		dao.add(testUser);

		User actualResult = null;

		try {
			actualResult = em.find(User.class, testUser.getId());
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertEquals(testUser, actualResult);
	}

	@Test
	public void testGet() {
		try {
			em.getTransaction().begin();
			em.persist(testUser);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		User actualResult = dao.get(testUser.getId());

		assertEquals(testUser, actualResult);
	}

	@Test
	public void testUpdate() {
		try {
			em.getTransaction().begin();
			em.persist(testUser);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		testUser.setUsername("TESTUSERNAME2");

		dao.update(testUser);

		User actualResult = null;

		try {
			actualResult = em.find(User.class, testUser.getId());
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertEquals(testUser, actualResult);
	}

	@Test
	public void testRemove() {
		int id = testUser.getId();
		try {
			em.getTransaction().begin();
			em.persist(testUser);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		dao.remove(testUser.getId());

		User result = null;

		try {
			result = em.find(User.class, id);
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertNull(result);
	}

	@Test
	public void testList() {
		List<User> expectedResult = new ArrayList<>();
		expectedResult.add(testUser);
		expectedResult.add(testUser2);
		expectedResult.add(testUser3);
		
		try {
			em.getTransaction().begin();
			em.persist(testUser);
			em.persist(testUser2);
			em.persist(testUser3);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}
		
		List<User> actualResult = dao.list();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testListOfNames() {
		List<String> expectedResult = new ArrayList<>();
		expectedResult.add(testUser.getUsername());
		expectedResult.add(testUser2.getUsername());
		expectedResult.add(testUser3.getUsername());
		
		try {
			em.getTransaction().begin();
			em.persist(testUser);
			em.persist(testUser2);
			em.persist(testUser3);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}
		
		List<String> actualResult = dao.listOfNames();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testListOfEmails() {
		List<String> expectedResult = new ArrayList<>();
		expectedResult.add(testUser.getEmail());
		expectedResult.add(testUser2.getEmail());
		expectedResult.add(testUser3.getEmail());
		
		try {
			em.getTransaction().begin();
			em.persist(testUser);
			em.persist(testUser2);
			em.persist(testUser3);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}
		
		List<String> actualResult = dao.listOfEmails();

		assertEquals(expectedResult, actualResult);
	}

	@Test
	public void testGetUserByName() {
		try {
			em.getTransaction().begin();
			em.persist(testUser);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		User actualResult = dao.getUserByName(testUser.getUsername());

		assertEquals(testUser, actualResult);
	}

}
