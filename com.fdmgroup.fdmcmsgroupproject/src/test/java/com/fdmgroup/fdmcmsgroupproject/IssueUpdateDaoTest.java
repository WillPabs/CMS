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
import com.fdmgroup.fdmcmsgroupproject.dao.IssueUpdateDao;
import com.fdmgroup.fdmcmsgroupproject.dao.IssuesDao;
import com.fdmgroup.fdmcmsgroupproject.model.Department;
import com.fdmgroup.fdmcmsgroupproject.model.Issue;
import com.fdmgroup.fdmcmsgroupproject.model.IssueUpdate;
import com.fdmgroup.fdmcmsgroupproject.model.User;

public class IssueUpdateDaoTest {

	private static ApplicationContext context;
	private EntityManager em;
	private static IssueUpdateDao dao;
	private static IssuesDao issuesDao;
	private static Issue testIssue;
	private IssueUpdate testIssueUpdate;
	private IssueUpdate testIssueUpdate2;
	private IssueUpdate testIssueUpdate3;

	@Before
	public void setUp() throws Exception {
		em = GenericDao.getEmf().createEntityManager();

		testIssueUpdate = new IssueUpdate();
		testIssueUpdate2 = new IssueUpdate();
		testIssueUpdate3 = new IssueUpdate();
		testIssueUpdate.setAuthor(1);
		testIssueUpdate.setUpdate("one");
		testIssueUpdate.setIssue(testIssue);
		testIssueUpdate2.setAuthor(1);
		testIssueUpdate2.setUpdate("two");
		testIssueUpdate2.setIssue(testIssue);
		testIssueUpdate3.setAuthor(1);
		testIssueUpdate3.setUpdate("three");
		testIssueUpdate3.setIssue(testIssue);
	}

	@After
	public void tearDown() throws Exception {
		if(dao.get(testIssueUpdate.getIssueUpdateId()) != null) {
			dao.remove(testIssueUpdate.getIssueUpdateId());
		}
		if(dao.get(testIssueUpdate2.getIssueUpdateId()) != null) {
			dao.remove(testIssueUpdate2.getIssueUpdateId());
		}
		if(dao.get(testIssueUpdate3.getIssueUpdateId()) != null) {
			dao.remove(testIssueUpdate3.getIssueUpdateId());
		}
		if(em.isOpen()) {
			em.close();
		}
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		context = new ClassPathXmlApplicationContext("/WEB-INF/beans.xml");
		issuesDao = context.getBean("issuesDao", IssuesDao.class);
		dao = context.getBean("issueUpdateDao", IssueUpdateDao.class);
		testIssue = new Issue();
		testIssue.setIssueDesc("Stub");
		testIssue.setAuthor(0);
		testIssue.setTitle("stub");
		issuesDao.add(testIssue);
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		if(issuesDao.get(testIssue.getIssueId()) != null) {
			issuesDao.remove(testIssue.getIssueId());
		}
		GenericDao.closeEMF();
	}

	@Test
	public void testAdd() {
		dao.add(testIssueUpdate);

		IssueUpdate actualResult = null;

		try {
			actualResult = em.find(IssueUpdate.class, testIssueUpdate.getIssueUpdateId());
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertEquals(testIssueUpdate, actualResult);
	}

	@Test
	public void testGet() {
		try {
			em.getTransaction().begin();
			em.persist(testIssueUpdate);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		IssueUpdate actualResult = dao.get(testIssueUpdate.getIssueUpdateId());

		assertEquals(testIssueUpdate, actualResult);
	}

	@Test
	public void testUpdate() {
		try {
			em.getTransaction().begin();
			em.persist(testIssueUpdate);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		testIssueUpdate.setUpdate("ONEALT");

		dao.update(testIssueUpdate);

		IssueUpdate actualResult = null;

		try {
			actualResult = em.find(IssueUpdate.class, testIssueUpdate.getIssueUpdateId());
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertEquals(testIssueUpdate, actualResult);
	}

	@Test
	public void testRemove() {
		int id = testIssueUpdate.getIssueUpdateId();
		try {
			em.getTransaction().begin();
			em.persist(testIssueUpdate);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}

		dao.remove(testIssueUpdate.getIssueUpdateId());

		IssueUpdate result = null;

		try {
			result = em.find(IssueUpdate.class, id);
		} catch(NoResultException e) {
			e.printStackTrace();
		}

		assertNull(result);
	}

	@Test
	public void testList() {
		List<IssueUpdate> expectedResult = new ArrayList<>();
		expectedResult.add(testIssueUpdate);
		expectedResult.add(testIssueUpdate2);
		expectedResult.add(testIssueUpdate3);
		
		try {
			em.getTransaction().begin();
			em.persist(testIssueUpdate);
			em.persist(testIssueUpdate2);
			em.persist(testIssueUpdate3);
			em.getTransaction().commit();
		} catch(RollbackException e) {
			e.printStackTrace();
		}
		
		List<IssueUpdate> actualResult = dao.list();

		assertEquals(expectedResult, actualResult);
	}

}
