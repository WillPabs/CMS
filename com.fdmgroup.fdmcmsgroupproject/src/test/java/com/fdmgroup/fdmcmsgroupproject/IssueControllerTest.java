package com.fdmgroup.fdmcmsgroupproject;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.fdmgroup.fdmcmsgroupproject.controllers.IssueController;
import com.fdmgroup.fdmcmsgroupproject.model.User;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/dispatcher-servlet.xml" })
@WebAppConfiguration
public class IssueControllerTest {

	private MockMvc mockMvc;
	@Autowired
	private IssueController ic;
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
		assertNotNull(ic);
	}

	@Test
	public void slashSubmitIssueDaoWithoutIssueGoesToRegisterIssue() throws Exception {
		mockMvc.perform(post("/submitIssueDao")).andExpect(status().isOk()).andExpect(view().name("registerIssue"));
	}

	@Test
	public void slashSubmitIssueDaoWithIssueGoesToRegisterIssue() throws Exception {
		mockMvc.perform(post("/submitIssueDao").sessionAttr("userId", 1).param("title", "title")
				.param("issueDesc", "issueDesc").param("priority", "priority")).andExpect(status().isOk())
				.andExpect(view().name("home"));
	}

	@Test
	public void slashIssueListGoesToViewIssueList() throws Exception {
		User mockUser = Mockito.mock(User.class);
		mockMvc.perform(post("/issueList").sessionAttr("user", mockUser).requestAttr("listType", 5)
				.requestAttr("roleName", "roleName")).andExpect(status().isOk())
				.andExpect(view().name("viewIssueList"));
	}

	@Test
	public void slashViewIssueDetailsGoesToViewIssueDetails() throws Exception {
		mockMvc.perform(post("/viewIssueDetails").param("issueId", "5")).andExpect(status().isOk())
				.andExpect(view().name("viewIssueDetails"));
	}

	@Test
	public void slashUpdateIssueForwardsToUpdateIssueAction() throws Exception {
		mockMvc.perform(post("/updateIssue").param("issueId", "1")).andExpect(status().isOk())
				.andExpect(view().name("forward:/updateIssueAction"));
	}

	@Test
	public void slashSubmitUpdateIssueGoesToViewIssueDetails() throws Exception {
		mockMvc.perform(post("/submitUpdateIssue").requestAttr("approval", "ya").sessionAttr("user", new User())
				.requestAttr("updateDetails", "updateDetails")).andExpect(status().isOk())
				.andExpect(view().name("viewIssueDetails"));
	}
}
