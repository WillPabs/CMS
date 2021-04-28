package com.fdmgroup.fdmcmsgroupproject.dao;

import java.util.List;

/**
 * IDao: Interface for a Generic Dao with standard CRUD operations
 *
 * @param <T>
 * @version 1.0
 */
public interface IDao<T> {

	boolean add(T t);
	T get(int id);
	boolean update(T t);
	boolean remove(int id);
	public List<T> list();

}
