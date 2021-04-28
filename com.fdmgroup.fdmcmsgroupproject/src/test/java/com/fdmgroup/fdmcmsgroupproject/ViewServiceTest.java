package com.fdmgroup.fdmcmsgroupproject;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fdmgroup.fdmcmsgroupproject.dao.IssuesDao;
import com.fdmgroup.fdmcmsgroupproject.model.Department;
import com.fdmgroup.fdmcmsgroupproject.model.Issue;
import com.fdmgroup.fdmcmsgroupproject.services.ViewService;

public class ViewServiceTest {
	
    private ViewService viewService;
    private IssuesDao mockIssuesDao;
	
    private Issue mockIssue;
    private Department mockDepartment;
	
	@Before
	public void setUp() throws Exception {
//		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
		
		mockIssuesDao = mock(IssuesDao.class);
		viewService = new ViewService(mockIssuesDao);
	}

	@Test
	public void testViewService_HasIssuesDaoDependency() {
		assertNotNull(viewService.getiDao());
	}
	
	@Test
	public void testViewDetails_CallsGetMethod_OnIssuesDaoDependency() {
		mockIssue = mock(Issue.class);
		mockIssue.setIssueId(1);
		int mockId = mockIssue.getIssueId();
		
		viewService.viewDetails(mockId);
		
		verify(mockIssuesDao, times(1)).get(mockId);
	}
	
	@Test
	public void testViewUnassigned_CallsListUnassignedMethod_OnIssuesDaoDependency() {
		viewService.viewUnassigned();
		
		verify(mockIssuesDao, times(1)).listUnassigned();
	}
	
	@Test
	public void testViewAllIssues_CallsListMethod_OnIssuesDaoDependency() {
		viewService.viewAllIssues();
		
		verify(mockIssuesDao, times(1)).list();
	}
	
	@Test
	public void testListAssigned_CallsListAssignedMethod_OnIssuesDaoDependency() {
		mockDepartment = mock(Department.class);
		mockDepartment.setDepartmentId(1);
		int departmentId = mockDepartment.getDepartmentId();
		
		viewService.listAssigned(departmentId);
		
		verify(mockIssuesDao, times(1)).listAssigned(departmentId);
	}

}
