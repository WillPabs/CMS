package com.fdmgroup.fdmcmsgroupproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.fdmcmsgroupproject.model.Issue;

/**
 * IssuesDao: Dao object that access the Issue table in database extends
 * GenericDao
 * 
 * @version 1.0
 */
public class IssuesDao extends GenericDao<Issue> {

	private static Logger logger = LogManager.getLogger();

	private EntityManager em;

	public IssuesDao(EntityManagerFactory emf) {
		super(emf);
		logger.info("IssuesDao created");
	}

	@Override
	public boolean add(Issue t) {
		logger.info("Attempting to add new issue");
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			logger.error("Error: Unable to add new issue", e);
		} finally {
			em.close();
		}
		logger.info("New Issue has been added");
		return true;
	}

	@Override
	public Issue get(int id) {
		logger.info("Attempting to retrieve issue");
		em = getEmf().createEntityManager();
		Issue issue = null;
		try {
			issue = em.find(Issue.class, id);
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve issue", e);
		} finally {
			em.close();
		}
		logger.info("Issue has been retrieved");
		return issue;
	}

	@Override
	public boolean update(Issue t) {
		logger.info("Attempting to update issue");
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			logger.error("Error: Unable to update issue", e);
		} finally {
			em.close();
		}
		logger.info("Issue has been updated");
		return true;
	}

	@Override
	public boolean remove(int id) {
		logger.info("Attempting to remove issue");
		em = getEmf().createEntityManager();
		Issue issue = em.find(Issue.class, id);
		try {
			em.getTransaction().begin();
			em.remove(issue);
			em.getTransaction().commit();
		} catch (NoResultException e) {
			logger.error("Error: Unable to remove issue", e);
		} finally {
			em.close();
		}
		logger.info("Issue has been removed");
		return true;
	}

	@Override
	public List<Issue> list() {
		logger.info("Attempting to retrieve list of issues");
		em = getEmf().createEntityManager();
		TypedQuery<Issue> query = em.createNamedQuery("findAllIssues", Issue.class);
		List<Issue> list = query.getResultList();
		em.close();
		logger.info("Issue List has been retrieved");
		return list;
	}

	public List<Issue> listAssigned(int depId) {
		logger.info("Attempting to retrieve list of assigned issues");
		em = getEmf().createEntityManager();
		TypedQuery<Issue> query = em.createNamedQuery("findByDept", Issue.class);
		query.setParameter("depId", depId);
		List<Issue> list = query.getResultList();
		em.close();
		logger.info("Assigned issue list has been retrieved");
		return list;
	}

	public List<Issue> listUnassigned() {
		logger.info("Attempting to retrieve list of unassigned issues");
		em = getEmf().createEntityManager();
		TypedQuery<Issue> query = em.createNamedQuery("findUnassigned", Issue.class);
		List<Issue> list = query.getResultList();
		em.close();
		logger.info("Unassigned issue list has been retrieved");
		return list;
	}

	public List<Issue> listOfIssueByDepartment(int deptId) {
		logger.info("Attempting to retrieve list of department issues");
		em = getEmf().createEntityManager();
		TypedQuery<Issue> query = em.createNamedQuery("findByDept", Issue.class);
		query.setParameter("depId", deptId);
		List<Issue> list = query.getResultList();
		em.close();
		logger.info("Department issue list has been retrieved");
		return list;
	}

	public List<Issue> listOfIssuesByUser(int userId) {
		logger.info("Attempting to retrieve list of issues by the user");
		em = getEmf().createEntityManager();
		TypedQuery<Issue> query = em.createNamedQuery("findIssuesByUser", Issue.class);
		query.setParameter("userId", userId);
		List<Issue> list = query.getResultList();
		em.close();
		System.out.println(userId);
		logger.info("List of issue created by user has been retrieved");
		return list;

	}
}
