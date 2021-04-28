package com.fdmgroup.fdmcmsgroupproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fdmgroup.fdmcmsgroupproject.model.Role;
import com.fdmgroup.fdmcmsgroupproject.model.User;

/**
 * UserDao: Dao object to access the User table in the database
 *
 * @version 1.0
 */
public class UserDao extends GenericDao<User> {

	private static Logger logger = LogManager.getLogger();

	private EntityManager em;

	public UserDao(EntityManagerFactory emf) {
		super(emf);
		logger.info("UserDao created");
	}

	@Override
	public boolean add(User t) {
		logger.info("Attempting to add new User");
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			logger.error("Error: Unable to add new User", e);
		} finally {
			em.close();
		}
		logger.info("New User has been added");
		return true;
	}

	@Override
	public User get(int id) {
		logger.info("Attempting to retrieve User");
		em = getEmf().createEntityManager();
		User user = null;
		try {
			user = em.find(User.class, id);
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve User", e);
		} finally {
			em.close();
		}
		logger.info("User has been retrieved");
		return user;
	}

	@Override
	public boolean update(User t) {
		logger.info("Attempting to update User");
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			logger.error("Error: Unable to update User", e);
		} finally {
			em.close();
		}
		logger.info("User has been updated");
		return true;
	}

	@Override
	public boolean remove(int id) {
		logger.info("Attempting to remove User");
		em = getEmf().createEntityManager();
		User user = em.find(User.class, id);
		try {
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
		} catch (NoResultException e) {
			logger.error("Error: Unable to remove User", e);
		} finally {
			em.close();
		}
		logger.info("User has been removed");
		return true;
	}

	@Override
	public List<User> list() {
		logger.info("Attempting to retrieve list of User");
		List<User> list = null;
		em = getEmf().createEntityManager();
		try {
			TypedQuery<User> query = em.createNamedQuery("userFindAll", User.class);
			list = query.getResultList();
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve list of users");
			e.printStackTrace();
		} finally {
			em.close();
		}
		logger.info("User List has been retrieved");
		return list;
	}

	public List<String> listOfNames() {
		logger.info("Attempting to get User list");
		List<String> list = null;
		em = getEmf().createEntityManager();
		try {
			TypedQuery<String> query = em.createNamedQuery("userListUsernames", String.class);
			list = query.getResultList();
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve list of usernames");
			e.printStackTrace();
		} finally {
			em.close();
		}
		logger.info("User List of Usernames has been retrieved");
		return list;
	}

	public List<String> listOfEmails() {
		logger.info("Attempting to get User list");
		List<String> list = null;
		em = getEmf().createEntityManager();
		try {
			TypedQuery<String> query = em.createNamedQuery("userListEmails", String.class);
			list = query.getResultList();
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve list of emails");
			e.printStackTrace();
		} finally {
			em.close();
		}
		logger.info("User List of Emails has been retrieved");
		return list;
	}

	public User getUserByName(String username) {
		logger.info("Attempting to get User by username");
		em = getEmf().createEntityManager();
		User user = null;
		try {
			TypedQuery<User> query = em.createNamedQuery("userFindByUsername", User.class);
			query.setParameter("username", username);
			user = query.getSingleResult();
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve list of emails");
			e.printStackTrace();
		} finally {
			em.close();
		}
		logger.info("User has been retrieved");
		return user;
	}
	//method to retrieve a role from the role table of the data base
	public Role getRoleFromDB(int id) {
		logger.info("Attempting to retrieve User");
		em = getEmf().createEntityManager();
		Role role = null;
		try {
			role = em.find(Role.class, id);
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve User", e);
		} finally {
			em.close();
		}
		logger.info("User has been retrieved");
		return role;
	}


}
