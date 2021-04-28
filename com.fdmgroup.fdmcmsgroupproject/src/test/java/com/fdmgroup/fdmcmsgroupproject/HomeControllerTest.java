package com.fdmgroup.fdmcmsgroupproject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fdmgroup.fdmcmsgroupproject.controllers.HomeController;
import com.fdmgroup.fdmcmsgroupproject.model.User;

import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import javax.net.ssl.SSLEngineResult.Status;
import javax.servlet.http.HttpSession;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@WebAppConfiguration
public class HomeControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private HomeController fc;
	@Autowired
	private WebApplicationContext wac;

	protected MockHttpSession session;

	protected MockHttpServletRequest request;

	protected void startSession() {
		session = new MockHttpSession();
	}

	protected void endSession() {
		session.clearAttributes();
		session = null;
	}

	protected void startRequest() {
		request = new MockHttpServletRequest();
		request.setSession(session);
		RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
	}

	protected void endRequest() {
		request.clearAttributes();
		request = null;
	}

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		startSession();
		startRequest();
	}

	@After
	public void cleanup() {
		endRequest();
		endSession();
	}

	@Test
	public void controllerExists() throws Exception {
		assertNotNull(fc);
	}

	@Test
	public void slashGoesToHomePage() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

	@Test
	public void slashIndexGoesToHomePage() throws Exception {
		mockMvc.perform(get("/index")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

	@Test
	public void slashHomeGoesToHomePage() throws Exception {
		mockMvc.perform(get("/home")).andExpect(status().isOk()).andExpect(view().name("home"));
	}

	@Test
	public void slashGoesToAccountHome() throws Exception {
		mockMvc.perform(get("/").sessionAttr("user", new User())).andExpect(status().isOk()).andExpect(view().name("accountHome"));
	}
	@Test
	public void slashIndexGoesToAccountHome() throws Exception {
		mockMvc.perform(get("/index").sessionAttr("user", new User())).andExpect(status().isOk()).andExpect(view().name("accountHome"));
	}
	@Test
	public void slashHomeGoesToAccountHome() throws Exception {
		mockMvc.perform(get("/home").sessionAttr("user", new User())).andExpect(status().isOk()).andExpect(view().name("accountHome"));
	}

	@Test
	public void slashRegisterGoesToRegisterPage() throws Exception {
		mockMvc.perform(get("/register")).andExpect(status().isOk()).andExpect(model().attributeExists("user"))
				.andExpect(view().name("registerUser"));
	}

	@Test
	public void slashLoginGoesToLoginPage() throws Exception {
		mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	public void slashAddUserForwardsToAddUserDaoMethod() throws Exception {
		mockMvc.perform(post("/addUser").param("firstName", "firstname").param("lastName", "lastname")
				.param("email", "email@email.com").param("username", "username").param("password", "password")
				.param("confirmPassword", "password")).andExpect(status().isOk())
				.andExpect(view().name("registerUser"));
	}

	@Test
	public void slashLoginUserGoesToLoginPageWithoutSessionUsername() throws Exception {
		mockMvc.perform(post("/loginUser")).andExpect(status().isOk()).andExpect(view().name("login"));
	}

	@Test
	public void slashLoginUserGoesToViewAccountWithSessionUsername() throws Exception {
		mockMvc.perform(post("/loginUser").sessionAttr("user", new User()).sessionAttr("username", "username")).andExpect(status().isOk())
				.andExpect(view().name("accountHome"));
	}

	@Test
	public void slashLoginUserForwardsToLoginUserDao() throws Exception {
		mockMvc.perform(
				post("/loginUser").param("loginUsername", "loginUsername").param("loginPassword", "loginPassword"))
				.andExpect(status().isOk()).andExpect(view().name("forward:/loginUserDao"));
	}

	@Test
	public void slashLogoutGoesToHomePage() throws Exception {
		mockMvc.perform(post("/logout")).andExpect(status().isOk()).andExpect(view().name("forward:/"));
	}

	@Test
	public void slashIssuePageGoesToViewIssueList() throws Exception {
		mockMvc.perform(post("/issuePage")).andExpect(status().isOk()).andExpect(view().name("viewIssueList"));
	}

	@Test
	public void slashIssueConfirmationGoesConfirmIssueSubmitted() throws Exception {
		mockMvc.perform(post("/issueConfirmation")).andExpect(status().isOk())
				.andExpect(view().name("confirmIssueSubmitted"));
	}

	@Test
	public void slashBasicUserListForwardsToIssueList() throws Exception {
		mockMvc.perform(post("/basicUserList")).andExpect(status().isOk()).andExpect(view().name("viewIssueList"));
	}

	@Test
	public void slashDepartmentAdminListForwardsToIssueList() throws Exception {
		mockMvc.perform(post("/departmentAdminList")).andExpect(status().isOk())
				.andExpect(view().name("viewIssueList"));
	}

	@Test
	public void slashGeneralAdminstListForwardsToViewIssueList() throws Exception {
		mockMvc.perform(post("/generalAdminList")).andExpect(status().isOk())
				.andExpect(view().name("viewIssueList"));
	}
}