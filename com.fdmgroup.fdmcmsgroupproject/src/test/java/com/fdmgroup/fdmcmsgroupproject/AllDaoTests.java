package com.fdmgroup.fdmcmsgroupproject;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;


@RunWith(Suite.class)
@SuiteClasses({ DepartmentDaoTest.class, IssuesDaoTest.class, UserDaoTest.class, IssueUpdateDaoTest.class })
public class AllDaoTests {

}
