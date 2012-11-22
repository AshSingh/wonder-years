package com.cs410.getfit.server.users;

import static org.junit.Assert.*;


import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.users.services.UserNewsfeedServices;
import com.j256.ormlite.dao.Dao;

@SuppressWarnings("unchecked")
public class UserNewsfeedServicesTest {
	
	private static String[] configNames = new String[] {
		"classpath:/com/cs410/getfit/server/testContexts/dbtest-context.xml",
		"classpath:/com/cs410/getfit/server/testContexts/dao-test-context.xml",
		"classpath:/com/cs410/getfit/server/testContexts/test-data-context.xml"};
	ApplicationContext ctx = new ClassPathXmlApplicationContext(configNames);

	Dao<ChallengeHistory, Long> challengeHistoryDao = (Dao<ChallengeHistory, Long>) ctx.getBean("challengeHistoryDao");
	Dao<ChallengeUser, Long> challengeUserDao = (Dao<ChallengeUser, Long>) ctx.getBean("challengeUsersDao");
	Dao<User, Long> userDao = (Dao<User, Long>) ctx.getBean("userDao");
	UserNewsfeedServices nfService = new UserNewsfeedServices();
	
	@Test
	public void setChallengeHistoryDaoTest() {
		nfService.setChallengeHistoryDao(challengeHistoryDao);
		assertEquals(nfService.getChallengeHistoryDao(), challengeHistoryDao);
	}
	
	@Test
	public void setChallengeUserDaoTest() {
		nfService.setChallengeUserDao(challengeUserDao);
		assertEquals(nfService.getChallengeUserDao(), challengeUserDao);
	}	

	@Test
	public void setUserDaoTest() {
		nfService.setUserDao(userDao);
		assertEquals(nfService.getUserDao(), userDao);
	}	

}
