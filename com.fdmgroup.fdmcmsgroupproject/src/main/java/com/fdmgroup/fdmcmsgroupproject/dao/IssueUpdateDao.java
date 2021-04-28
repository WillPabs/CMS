package com.fdmgroup.fdmcmsgroupproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.fdmcmsgroupproject.model.IssueUpdate;

/**
 * IssueUpdateDao: Dao object to access the IssueUpdate table in the database 
 *
 * @version 1.0
 */
public class IssueUpdateDao extends GenericDao<IssueUpdate> {

	private static Logger logger = LogManager.getLogger();
	
	private EntityManager em;
	
	public IssueUpdateDao(EntityManagerFactory emf) {
		super(emf);
		logger.info("IssueUpdateDao created");

	}

	@Override
	public boolean add(IssueUpdate t) {
		logger.info("Attempting to add new issue update");
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			logger.error("Error: Unable to add new issue update", e);
		} finally {
			em.close();
		}
		logger.info("New Issue Update has been added");
		return true;
	}

	@Override
	public IssueUpdate get(int id) {
		logger.info("Attempting to retrieve issue update");
		em = getEmf().createEntityManager();
		IssueUpdate issueUpdate = null;
		try {
			issueUpdate = em.find(IssueUpdate.class, id);
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve issue update", e);
		} finally {
			em.close();
		}
		logger.info("Issue update has been retrieved");
		return issueUpdate;
	}

	@Override
	public boolean update(IssueUpdate t) {
		logger.info("Attempting to update issue update");
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			logger.error("Error: Unable to update issue update", e);
		} finally {
			em.close();
		}
		logger.info("Issue update has been updated");
		return true;
	}

	@Override
	public boolean remove(int id) {
		logger.info("Attempting to remove issue update");
		em = getEmf().createEntityManager();
		IssueUpdate issueUpdate = em.find(IssueUpdate.class, id);
		try {
			em.getTransaction().begin();
			em.remove(issueUpdate);
			em.getTransaction().commit();
		} catch (NoResultException e) {
			logger.error("Error: Unable to remove issue update", e);
		} finally {
			em.close();
		}
		logger.info("Issue update has been removed");
		return true;
	}

	@Override
	public List<IssueUpdate> list() {
		logger.info("Attempting to retrieve list of issue updates");
		em = getEmf().createEntityManager();
		TypedQuery<IssueUpdate> query = em.createNamedQuery("findAllIssueUpdate", IssueUpdate.class);
		List<IssueUpdate> list = query.getResultList();
		em.close();
		logger.info("Issue update List has been retrieved");
		return list;
	}

}
