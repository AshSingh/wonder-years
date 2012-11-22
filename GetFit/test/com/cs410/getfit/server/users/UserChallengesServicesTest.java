package com.cs410.getfit.server.users;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.users.services.UserChallengesServices;
import com.j256.ormlite.dao.Dao;

@SuppressWarnings("unchecked")
public class UserChallengesServicesTest {
	
	private static String[] configNames = new String[] {
		"classpath:/com/cs410/getfit/server/testContexts/dbtest-context.xml",
		"classpath:/com/cs410/getfit/server/testContexts/dao-test-context.xml",
		"classpath:/com/cs410/getfit/server/testContexts/test-data-context.xml"};
	ApplicationContext ctx = new ClassPathXmlApplicationContext(configNames);

	Dao<ChallengeUser, Long> challengeUserDao = (Dao<ChallengeUser, Long>) ctx.getBean("challengeUsersDao");
	Dao<Challenge, Long> challengeDao = (Dao<Challenge, Long>) ctx.getBean("challengeDao");
	UserChallengesServices ucService = new UserChallengesServices();

	@Test
	public void setChallengeDaoTest() {
		ucService.setChallengeDao(challengeDao);
		assertEquals(ucService.getChallengeDao(), challengeDao);
	}
	
	@Test
	public void setChallengeUserDaoTest() {
		ucService.setChallengeUserDao(challengeUserDao);
		assertEquals(ucService.getChallengeUserDao(), challengeUserDao);
	}	

}
