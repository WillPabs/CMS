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
import com.fdmgroup.fdmcmsgroupproject.dao.IssuesDao;
import com.fdmgroup.fdmcmsgroupproject.model.Department;
import com.fdmgroup.fdmcmsgroupproject.model.Issue;
import com.fdmgroup.fdmcmsgroupproject.model.User;

public class IssuesDaoTest {
	private static ApplicationContext context;
	private EntityManager em;
	private static IssuesDao dao;
	private Issue testIssue;
	private User testUser;
	private Department dept;

	@Before
	public void setup() {
		em = IssuesDao.getEmf().createEntityManager();
		testIssue = new Issue();
		testUser = new User();

		testUser.setId(1);
		testIssue.setIssueDesc("Stub");
		testIssue.setAuthor(testUser.getId());
		testIssue.setTitle("stub");

	}

	@BeforeClass
	public static void setupBeforeClass() {
		context = new ClassPathXmlApplicationContext("/WEB-INF/beans.xml");
		dao = context.getBean("issuesDao", IssuesDao.class);
	}

	@After
	public void tearDown() {
		if (dao.get(testIssue.getIssueId()) != null) {
			dao.remove(testIssue.getIssueId());
		}
		if (em.isOpen()) {
			em.close();
		}
	}

	@AfterClass
	public static void cleanup() {
		GenericDao.closeEMF();
	}

	@Test
	public void testAdd() {
		dao.add(testIssue);

		Issue actualIssue = null;
		actualIssue = em.find(Issue.class, testIssue.getIssueId());

		assertEquals(testIssue.getIssueId(), actualIssue.getIssueId());
	}

	@Test
	public void testGet() {

		em.getTransaction().begin();
		em.persist(testIssue);
		em.getTransaction().commit();

		Issue actualIssue = dao.get(testIssue.getIssueId());

		assertEquals(testIssue, actualIssue);
	}

	@Test
	public void testUpdate() {
		em.getTransaction().begin();
		em.persist(testIssue);
		em.getTransaction().commit();
		
		String updateIssue = "nope";
		
		testIssue.setTitle(updateIssue);
		dao.update(testIssue);
		
		Issue actualIssue = em.find(Issue.class, testIssue.getIssueId());
		
		assertEquals(testIssue.getTitle(), actualIssue.getTitle());
	}

	@Test
	public void testRemove() {
		int id = testIssue.getIssueId();
		em.getTransaction().begin();
		em.persist(testIssue);
		em.getTransaction().commit();

		dao.remove(testIssue.getIssueId());
		
		Issue actualIssue = em.find(Issue.class, id);
		
		assertNull(actualIssue);
	}

	@Test
	public void testList() {
		em.getTransaction().begin();
		em.persist(testIssue);
		em.getTransaction().commit();
		
		List<Issue> testList = dao.list();
		
		assertEquals(testList.get(0), testIssue);
	}

	@Test
	public void testListAssigned() {
		testIssue.setAssignedToDeptId(1);
		List<Issue> expectedList = new ArrayList<>();
		expectedList.add(testIssue);
		
		em.getTransaction().begin();
		em.persist(testIssue);
		em.getTransaction().commit();
		
		List<Issue> testList = dao.listAssigned(1);
		
		assertEquals(expectedList, testList);
	}

	@Test
	public void testListUnassigned() {
		List<Issue> expectedList = new ArrayList<>();
		testIssue.setAssignedToDeptId(1);
		expectedList.add(testIssue);
		
		em.getTransaction().begin();
		em.persist(testIssue);
		em.getTransaction().commit();
		
		List<Issue> testList = dao.listUnassigned();
		
		assertEquals(expectedList, testList);
	}

	@Test
	public void testListIssuesByDepartment() {
		testIssue.setAssignedToDeptId(1);
		List<Issue> expectedList = new ArrayList<>();
		expectedList.add(testIssue);
		
		em.getTransaction().begin();
		em.persist(testIssue);
		em.getTransaction().commit();
		
		List<Issue> testList = dao.listOfIssueByDepartment(1);
		
		assertEquals(expectedList, testList);
	}

	@Test
	public void testListIssuesByUser() {
		List<Issue> expectedList = new ArrayList<>();
		expectedList.add(testIssue);
		
		em.getTransaction().begin();
		em.persist(testIssue);
		em.getTransaction().commit();
		
		List<Issue> testList = dao.listOfIssuesByUser(testUser.getId());
		
		assertEquals(expectedList, testList);
	}
}
