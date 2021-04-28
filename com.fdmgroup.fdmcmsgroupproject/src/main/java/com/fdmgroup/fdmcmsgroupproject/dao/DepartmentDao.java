package com.fdmgroup.fdmcmsgroupproject.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.RollbackException;
import javax.persistence.TypedQuery;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.annotations.common.util.impl.LoggerFactory;

import com.fdmgroup.fdmcmsgroupproject.model.Department;

/**
 * DepartmentDao: Dao object that access the Department table in the database
 * 
 * @version 1.0
 */
public class DepartmentDao extends GenericDao<Department> {

	private static Logger logger = LogManager.getLogger();
	
	private EntityManager em;
	
	public DepartmentDao(EntityManagerFactory emf) {
		super(emf);
		logger.info("DepartmentDao created");
	}

	@Override
	public boolean add(Department t) {
		logger.info("Attempting to add new Department");
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.persist(t);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			logger.error("Error: Unable to add new Department", e);
		} finally {
			em.close();
		}
		logger.info("New Department has been added");
		return true;
	}

	@Override
	public Department get(int id) {
		logger.info("Attempting to retrieve Department");
		em = getEmf().createEntityManager();
		Department department = null;
		try {
			department = em.find(Department.class, id);
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve Department", e);
		} finally {
			em.close();
		}
		logger.info("Department has been retrieved");
		return department;
	}

	@Override
	public boolean update(Department t) {
		logger.info("Attempting to update Department");
		em = getEmf().createEntityManager();
		try {
			em.getTransaction().begin();
			em.merge(t);
			em.getTransaction().commit();
		} catch (RollbackException e) {
			logger.error("Error: Unable to update Department", e);
		} finally {
			em.close();
		}
		logger.info("Department has been updated");
		return true;
	}

	@Override
	public boolean remove(int id) {
		logger.info("Attempting to remove Department");
		em = getEmf().createEntityManager();
		Department department = em.find(Department.class, id);
		try {
			em.getTransaction().begin();
			em.remove(department);
			em.getTransaction().commit();
		} catch (NoResultException e) {
			logger.error("Error: Unable to remove Department", e);
		} finally {
			em.close();
		}
		logger.info("Department has been removed");
		return true;
	}

	@Override
	public List<Department> list() {
		logger.info("Attempting to retrieve list of Department");
		em = getEmf().createEntityManager();
		List<Department> list = null;
		try {
			TypedQuery<Department> query = em.createNamedQuery("departmentFindAll", Department.class);
			list = query.getResultList();
		} catch (NoResultException e) {
			logger.error("Error: Unable to list Department", e);
		} finally {
			em.close();
		}
		logger.info("Department List has been retrieved");
		return list;
	}

	public int getDepartmentId(String departmentName) {
		logger.info("Attempting to retrieve Department id by name");
		em = getEmf().createEntityManager();
		int deptId = 0;
		try {
			TypedQuery<Department> query = em.createNamedQuery("findDeptIdByDeptName", Department.class);
			query.setParameter("departmentName", departmentName);
			Department department = query.getSingleResult();
			deptId = department.getDepartmentId();
		} catch (NoResultException e) {
			logger.error("Error: Unable to retrieve Department id", e);
		} finally {
			em.close();
		}
		logger.info("Department id has been retrieved");
		return deptId;
	}
}
