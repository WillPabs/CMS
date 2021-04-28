package com.fdmgroup.fdmcmsgroupproject.services;

import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import com.fdmgroup.fdmcmsgroupproject.dao.IssuesDao;
import com.fdmgroup.fdmcmsgroupproject.model.Issue;

/**
 * ViewService: Class to provide service in retrieving the information from the Issue table
 * 
 * @version 1.0
 */
public class ViewService implements ApplicationContextAware {
	
	private static Logger logger = LogManager.getLogger();
	
	private ApplicationContext context;
	private IssuesDao iDao;  
	
	public ViewService() {
		super();
		iDao = new IssuesDao(context.getBean("emfactory", EntityManagerFactory.class));
	}
	
	public ViewService(IssuesDao iDao) {
		super();
		this.iDao = iDao;
	}

	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
	
	public ApplicationContext getApplicationContext() {
		return context;
	}
	
	public IssuesDao getiDao() {
		return iDao;
	}

	public Issue viewDetails(int id) {
		logger.info("Getting issue details");
		Issue result = iDao.get(id);
		return result;
	}
	
	public List<Issue> viewUnassigned() {
		logger.info("Getting list of unassigned issues");
		return iDao.listUnassigned();
	}
	
	public List<Issue> viewAllIssues() {
		logger.info("Getting all issues");
		List<Issue> result = iDao.list();
		return result;
	}
	
	public List<Issue> listAssigned(int depId) {
		logger.info("Getting list of all assigned issues");
		return iDao.listAssigned(depId);
	}
}
