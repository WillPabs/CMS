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

import com.fdmgroup.fdmcmsgroupproject.dao.DepartmentDao;
import com.fdmgroup.fdmcmsgroupproject.dao.GenericDao;
import com.fdmgroup.fdmcmsgroupproject.model.Department;
import com.fdmgroup.fdmcmsgroupproject.model.User;

import junit.extensions.TestDecorator;

public class DepartmentDaoTest {
	private static ApplicationContext context;
	private EntityManager em;
	private static DepartmentDao dao;
	private Department testDept;
	private Department testDept2;
	private Department testDept3;
	
	@Before
	public void setUp() throws Exception {
		em = GenericDao.getEmf().createEntityManager();
		testDept = new Department();
		testDept2 = new Department();
		testDept3 = new Department();
		testDept.setDepartmentName("TESTDEPT");
		testDept2.setDepartmentName("TESTDEPT2");
		testDept3.setDepartmentName("TESTDEPT3");
	}

	@After
	public void tearDown() throws Exception {
		if(dao.get(testDept.getDepartmentId()) != null) {
			dao.remove(testDept.getDepartmentId());
		}
		if(dao.get(testDept2.getDepartmentId()) != null) {
			dao.remove(testDept2.getDepartmentId());
		}
		if(dao.get(testDept3.getDepartmentId()) != null) {
			dao.remove(testDept3.getDepartmentId());
		}
		if(em.isOpen()) {
			em.close();
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("/WEB-INF/beans.xml");
		dao = context.getBean("departmentDao", DepartmentDao.class);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		GenericDao.closeEMF();
	}
	
	@Test
	public void testAdd() {
		dao.add(testDept);
		
		Department actualResult = null;
		
		try {
			actualResult = em.find(Department.class, testDept.getDepartmentId());
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertEquals(testDept, actualResult);
	}
	
	@Test
	public void testGet() {
		try {
			em.getTransaction().begin();
			em.persist(testDept);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		Department actualResult = dao.get(testDept.getDepartmentId());

		assertEquals(testDept, actualResult);
	}
	
	@Test
	public void testUpdate() {
		try {
			em.getTransaction().begin();
			em.persist(testDept);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		testDept.setDepartmentName("TESTDEPTALT");

		dao.update(testDept);

		Department actualResult = null;

		try {
			actualResult = em.find(Department.class, testDept.getDepartmentId());
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertEquals(testDept, actualResult);
	}
	
	@Test
	public void testRemove() {
		int id = testDept.getDepartmentId();
		try {
			em.getTransaction().begin();
			em.persist(testDept);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		dao.remove(testDept.getDepartmentId());

		Department result = null;

		try {
			result = em.find(Department.class, id);
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertNull(result);
	}
	
	@Test
	public void testList() {
		List<Department> expectedResult = new ArrayList<>();
		expectedResult.add(testDept);
		expectedResult.add(testDept2);
		expectedResult.add(testDept3);
		
		try {
			em.getTransaction().begin();
			em.persist(testDept);
			em.persist(testDept2);
			em.persist(testDept3);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}
		
		List<Department> actualResult = dao.list();

		assertEquals(expectedResult, actualResult);
	}
	
	@Test
	public void testGetDepartmentId() {
		try {
			em.getTransaction().begin();
			em.persist(testDept);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}
		
		int expectedValue = testDept.getDepartmentId();
		int actualValue = dao.getDepartmentId(testDept.getDepartmentName());
		
		assertEquals(expectedValue, actualValue);
	}

}
