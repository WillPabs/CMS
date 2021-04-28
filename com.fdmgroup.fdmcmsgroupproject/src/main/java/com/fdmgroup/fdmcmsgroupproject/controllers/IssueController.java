package com.fdmgroup.fdmcmsgroupproject.controllers;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.fdmcmsgroupproject.dao.IssueUpdateDao;
import com.fdmgroup.fdmcmsgroupproject.dao.IssuesDao;
import com.fdmgroup.fdmcmsgroupproject.model.Issue;
import com.fdmgroup.fdmcmsgroupproject.model.IssueUpdate;
import com.fdmgroup.fdmcmsgroupproject.model.Status;
import com.fdmgroup.fdmcmsgroupproject.model.User;

/**
 *	IssueController: Controller designed to handle request related to Issues in database 
 *
 */
@Controller
public class IssueController implements ApplicationContextAware {

	private ApplicationContext context;
	private static Logger logger = LogManager.getLogger();

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

	@RequestMapping(value = "/processIssue", method = RequestMethod.POST)
	public String submitIssueDao(Issue issue, HttpServletRequest req) {
		logger.info("Processing issue submitted");
		IssuesDao issuesDao = context.getBean("issuesDao", IssuesDao.class);

		if(issue == null || issue.getTitle() == null || issue.getIssueDesc() == null || issue.getPriority() == null) {
			logger.warn("Unable to process issue submitted, redirecting back to issue registration page");
			req.setAttribute("errorNull", 1);
			return "registerIssue";
		}
		
		issue.setDateSubmitted(new Date(System.currentTimeMillis()));
		issue.setStatus("OPEN");
		issue.setAssignedToDeptId((int) req.getAttribute("departmentId"));
		
		issue.setAuthor((int) req.getSession().getAttribute("userId"));
		
		logger.info("Adding issue to our records");
		
		issuesDao.add(issue);
		req.setAttribute("confirmIssueSubmitted", 1);
		req.setAttribute("issueId", issue.getIssueId());
		
		logger.info("Redirecting back to home page");
		return "forward:/home";
	}
	
	@RequestMapping(value="/issueList", method = RequestMethod.POST)
	public String viewListOfIssues(HttpServletRequest req, Issue issue, HttpSession session){
		logger.info("Retrieving list of issues");
		IssuesDao issueDao = context.getBean("issuesDao", IssuesDao.class);
		int listType = (int) req.getAttribute("listType");
		System.out.println("list type " + listType);
		User user = (User) session.getAttribute("user");
		List<Issue> issueList = null;

		logger.info("Checking list type to retrieve correct list");
		
		if(listType == 5) {
			logger.info("Retrieving list of unassigned issues");
			issueList = issueDao.listUnassigned();
		} else if(listType == 3) {
			logger.info("Retrieving list of department based issues");
			issueList = issueDao.listOfIssueByDepartment(user.getDepartment().getDepartmentId());
		} else if(listType == 1) {
			logger.info("Retrieving list of user based issue");
			issueList = issueDao.listOfIssuesByUser(user.getId());
		}
		req.setAttribute("issuesList", issueList);
		
		logger.info("Redirecting to issue list page");
		
		return "viewIssueList";
	}
	
	
	@RequestMapping(value="/viewIssueDetails", method=RequestMethod.POST)
	public String viewIssueDetails(HttpServletRequest req) {
		logger.info("Entering issue details");
		IssuesDao issuesDao = context.getBean("issuesDao", IssuesDao.class);
		Issue issue = issuesDao.get(Integer.parseInt(req.getParameter("issueId")));
		req.setAttribute("issue", issue);
		return "viewIssueDetails";
	}
	
	@RequestMapping(value="/updateIssueDetails", method=RequestMethod.POST)
	public String viewUpdateIssue(HttpServletRequest req) {
		logger.info("Entering update issue page");
		IssuesDao issuesDao = context.getBean("issuesDao", IssuesDao.class);
		Issue issue = issuesDao.get(Integer.parseInt(req.getParameter("issueId")));
		req.setAttribute("issue", issue);
		return "updateIssue";
	}
	
	@RequestMapping(value="/updateIssue", method=RequestMethod.POST)
	public String getIssueFromIssueId(HttpServletRequest req, Issue issueFromForm) {
		logger.info("Forwarding update request");
		IssuesDao issuesDao = context.getBean("issuesDao", IssuesDao.class);
		Issue issueFromDB = issuesDao.get(Integer.parseInt(req.getParameter("issueId")));
		
		System.out.println("--------------------------------"+issueFromDB);
		String closed = null;
		if (req.getParameter("closed") != null) {
			closed = req.getParameter("closed");
		}
		
		req.setAttribute("depName", issueFromDB.getAssignedToDeptId());
		req.setAttribute("closed", closed);
		req.setAttribute("status", req.getParameter("status"));
		req.setAttribute("adminComment", req.getParameter("adminComment"));
		req.setAttribute("originalIssue", issueFromDB);
		req.setAttribute("issueFromForm", issueFromForm);
		req.setAttribute("updateDetails", req.getParameter("updateDetails"));
		return "forward:/submitUpdateIssue";
	}

	
	// check request parameter are being passed along
	@RequestMapping(value="/submitUpdateIssue", method=RequestMethod.POST)
	public String updateIssue(HttpServletRequest req, HttpSession session) {
		logger.info("Processing issue update");
		IssuesDao issueDao = context.getBean("issuesDao", IssuesDao.class);
		IssueUpdateDao issueUpdateDao = context.getBean("issueUpdateDao", IssueUpdateDao.class);
		String approval = (String) req.getAttribute("approval");
		
		IssueUpdate issueUpdate = new IssueUpdate();
		issueUpdate.setAuthor(((User) session.getAttribute("user")).getId());
		if(req.getAttribute("updateDetails") == null ){
			issueUpdate.setUpdate("Not Set");
		} else {
			issueUpdate.setUpdate((String)req.getAttribute("updateDetails"));
			
		}
		issueUpdate.setUpdateDate(new Date(System.currentTimeMillis()));
		
		Issue issueFromDB = (Issue)req.getAttribute("originalIssue");
		Issue issueFromForm = (Issue)req.getAttribute("issueFromForm");
		System.out.println("--------------------------------"+req.getAttribute("status"));
		System.out.println("--------------------------------"+issueFromDB);
		String status = null;
		if (req.getAttribute("status") == null) {
			issueFromForm.setStatus(issueFromDB.getStatus());
		} else {
			status = (String)req.getAttribute("status");
			issueFromForm.setStatus(status);
		}
		System.out.println("--------------------------------"+issueFromDB);
		if (req.getAttribute("closed") != null) {
			issueFromForm.setStatus((String)req.getAttribute("closed"));
		} 

		
		issueFromForm.setAdminComment((String)req.getAttribute("adminComment"));
		issueFromForm.setDateSubmitted(issueFromDB.getDateSubmitted());
		if(req.getParameter("departmentName") == null){
			issueFromForm.setAssignedToDeptId(1);
		}else{
			issueFromForm.setAssignedToDeptId(Integer.parseInt(req.getParameter("departmentName")));
		}
		Date date = new Date(System.currentTimeMillis());
		if(issueFromDB.getDateResolved() != null){
			date = issueFromDB.getDateResolved();
		}
		
		if(issueFromForm.getStatus().equals("RESOLVED")) {
			issueFromForm.setDateResolved(date);
		} else if (issueFromForm.getStatus().equals("CLOSED")){
			issueFromForm.setDateResolved(date);
		} else if(issueFromForm.getStatus().equals("PENDING") || issueFromForm.getStatus().equals("OPEN")){
			issueFromForm.setDateResolved(null);
		}
		
		logger.info("Adding updates to our records");
		
		issueUpdate.setIssue(issueFromForm);
		issueUpdateDao.add(issueUpdate);

		issueDao.update(issueFromForm);
	
		req.setAttribute("issue", issueFromForm);
		logger.info("Redirecting to issue details page");
		
		return "viewIssueDetails";
	}
	
}
