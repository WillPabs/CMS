package com.fdmgroup.fdmcmsgroupproject.dao;

import javax.persistence.EntityManagerFactory;

import com.fdmgroup.fdmcmsgroupproject.model.User;

/**
 * GenericDao: GenericDao that implements the IDao interface, setups the EntityManagerFactory 
 *
 * @param <T>
 * @version 1.0
 */
public abstract class  GenericDao<T> implements IDao<T> {
	
	private static EntityManagerFactory emf;

	public GenericDao(EntityManagerFactory emf) { 
		if (GenericDao.emf == null || !GenericDao.emf.isOpen()) {
			GenericDao.emf = emf;
		}
	}	
	
	public static EntityManagerFactory getEmf() {
		return emf;
	}

	public static void closeEMF() {
		if (emf.isOpen()) {
			emf.close();
		}
	}


}
