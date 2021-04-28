package com.fdmgroup.fdmcmsgroupproject.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.fdmgroup.fdmcmsgroupproject.dao.DepartmentDao;
import com.fdmgroup.fdmcmsgroupproject.dao.UserDao;
import com.fdmgroup.fdmcmsgroupproject.model.Department;
import com.fdmgroup.fdmcmsgroupproject.model.Issue;
import com.fdmgroup.fdmcmsgroupproject.model.Role;
import com.fdmgroup.fdmcmsgroupproject.model.User;

/**
 *	HomeController: Default controller to handle request through out whole application 
 * 
 *
 */
@Controller
public class HomeController implements ApplicationContextAware{
	
	private ApplicationContext context;
	private static Logger logger = LogManager.getLogger();
	
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}
	
	@RequestMapping(value = { "/", "/index", "/home" })
	public String goToHomePage(HttpServletRequest request) {
		if(request.getSession().getAttribute("user") == null) {
			logger.info("Entering home page");
			return "home";
		} else {
			logger.info("Session already exist, redirecting to user account home page");
			return "accountHome";
		}
	}

	@RequestMapping(value = "/register")
	public String goToRegistrationPage(Model model) {
		logger.info("Entering registration page");
		model.addAttribute("user", new User());
		return "registerUser";
	}
	
	@RequestMapping(value = "/login")
	public String goToLoginPage(Model model) {
		logger.info("Entering login page");
		return "login";
	}
	
	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public String addUserDao(HttpServletRequest request) {
		logger.info("Attempting to process registration request");
		UserDao userDao = context.getBean("userDao", UserDao.class);
		List<String> usernameList = userDao.listOfNames();
		List<String> emailList = userDao.listOfEmails();
		int errorPassword = 0;
		int errorUsername = 0;
		int errorEmail = 0;

		String confirmPassword = request.getParameter("confirmPassword");
		String firstName = request.getParameter("firstName");
		String lastName = request.getParameter("lastName");
		String email = request.getParameter("email");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		User user = new User(username, password, firstName, lastName, email);
		
		if(!user.getPassword().equals(confirmPassword)) {
			logger.warn("User entered non matching passwords");
			errorPassword = 1;
		}

		if(usernameList.contains(user.getUsername())) {
			logger.warn("Username entered already exists in the database");
			errorUsername = 1;
		}

		if(emailList.contains(user.getEmail())) {
			logger.warn("Email entered already exists in the database");
			errorEmail = 1;
		}
		
		if(errorPassword == 1 || errorUsername == 1 || errorEmail == 1) {
			logger.warn("Issues were found while processing registration request, redirecting back to registration page");
			request.setAttribute("errorPassword", errorPassword);
			request.setAttribute("errorUsername", errorUsername);
			request.setAttribute("errorEmail", errorEmail);
			user.setPassword(null);
			return "registerUser";
		}		
		//if no department selected, user dept. persist unassigned dept.
		DepartmentDao departmentDao =  context.getBean("departmentDao", DepartmentDao.class);
		String deptName = request.getParameter("departmentName");
		user.setDepartment(departmentDao.get(departmentDao.getDepartmentId(deptName)));
		//default user role when user registers
		Role defaultUserRole = userDao.getRoleFromDB(1);
		user.addRole(defaultUserRole);
		boolean added = userDao.add(user);
		if(added) {
			logger.info("New account has been added to our records, redirecting to login page");
			return "login";
		} else {
			logger.warn("Unable to add new account to our records, redirecting back to registration page");
			request.setAttribute("errorAdded", 1);
			return "registerUser";
		}
	}

	@RequestMapping(value = "/loginUser", method = RequestMethod.POST)
	public String loginUser(HttpServletRequest request){
		logger.info("Attempting to filter login request");
		int errorLogin = 0;
		String username = request.getParameter("loginUsername");
		String password = request.getParameter("loginPassword");

		if(request.getSession(false).getAttribute("user") != null) {
			logger.info("Session already active, redirecting user to account home page");
			return "accountHome";
		}
		
		if(username == null || password == null) {
			logger.warn("Fields were left empty");
			errorLogin = 1;
		} else {
			request.setAttribute("loginUsername", username.trim());
			request.setAttribute("loginPassword", password);
		}

		if(errorLogin == 0) {
			logger.info("Passing credentials to process login request");
			
			return "forward:/loginUserDao";
		} else {
			logger.warn("Issues were found while filtering, redirecting back to login page");
			request.setAttribute("errorLogin", errorLogin);
			return "login";
		}
	}
	
	@RequestMapping(value = "/loginUserDao", method = RequestMethod.POST)
	public String loginUserDao(HttpServletRequest request) {
		logger.info("Attempting to process login request");
		UserDao userDao = context.getBean("userDao", UserDao.class);
		List<String> usernameList = userDao.listOfNames();
		int errorUsername = 0;
		int errorPassword = 0;

		String username = request.getParameter("loginUsername");
		String password = request.getParameter("loginPassword");
		
		if(usernameList.contains(username)) {
			logger.info("Username passed is found in our records, checking password...");
			User user = userDao.getUserByName(username);
			if(user.getPassword().equals(password)) {
				logger.info("Password entered matches up with our records, creating session and redirecting to user account home page");
				HttpSession session = request.getSession();
				user.setPassword("");
				session.setAttribute("user", user);
				session.setAttribute("roleIdsSum", user.getRoleIdsSum());
				session.setAttribute("deptId", user.getDepartment().getDepartmentId());
				session.setAttribute("userId", user.getId());
				return "accountHome";
			} else {
				logger.warn("Username was not found in our records, redirecting back to login page");
				errorPassword = 1;
				request.setAttribute("errorPassword", errorPassword);
			}
		} else {
			logger.warn("Password was not found in our records, redirecting back to login page");
			errorUsername = 1;
			request.setAttribute("errorUsername", errorUsername);
		}
		
		return "login";
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	public String logout(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("user") != null) {
			session.invalidate();
		}
		logger.info("Invalidating current session, redirecting back to home page");
		return "home";
	}

	@RequestMapping(value = "/submitIssue", method = RequestMethod.POST)
	public String issuePage(Model model) {
		logger.info("Entering submit issue page");
		model.addAttribute("issue", new Issue());
		return "registerIssue";
	}
	
	@RequestMapping(value = "/issueConfirmation", method = RequestMethod.POST)
	public String issueConfirmation(HttpServletRequest request) {
		logger.info("Issue registration process successful, redirecting back to account home page");
		Issue issue = (Issue) request.getAttribute("issue");
		request.setAttribute("Issue", issue);
		
		//TODO CREATE ISSUECONFIRMATION JSP
		return "accountHome";
	}
	
	
	@RequestMapping(value = "/basicUserList", method = RequestMethod.POST)
	public String goToBasicUserList(HttpServletRequest request, HttpSession session) {
		logger.info("Entering issue list page with basic user properties");
		request.setAttribute("listType", 1);
		session.setAttribute("viewAsRole", 1);
		return "forward:/issueList";
	}
	
	@RequestMapping(value = "/departmentAdminList", method = RequestMethod.POST)
	public String goToDepartmentAdminList(HttpServletRequest request, HttpSession session) {		
		logger.info("Entering issue list page with department admin properties");
		request.setAttribute("listType", 3);
		session.setAttribute("viewAsRole", 3);
		return "forward:/issueList";
	}
	
	@RequestMapping(value = "/generalAdminList", method = RequestMethod.POST)
	public String goToGeneralAdminList(HttpServletRequest request, HttpSession session) {
		logger.info("Entering issue list page with general admin properties");
		request.setAttribute("listType", 5);
		session.setAttribute("viewAsRole", 5);
		return "forward:/issueList";
}
	}