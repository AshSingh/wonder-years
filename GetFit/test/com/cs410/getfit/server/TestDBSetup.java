package com.cs410.getfit.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.cs410.getfit.shared.Challenge;
import com.cs410.getfit.shared.ChallengeImpl;
import com.cs410.getfit.shared.ChallengeUsers;
import com.cs410.getfit.shared.ChallengeUsersImpl;
import com.cs410.getfit.shared.User;
import com.cs410.getfit.shared.UserImpl;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.spring.TableCreator;

public class TestDBSetup {

	private static String[] configNames = new String[] {
			"classpath:/com/cs410/getfit/server/testContexts/dbtest-context.xml",
			"classpath:/com/cs410/getfit/server/testContexts/dao-test-context.xml",
			"classpath:/com/cs410/getfit/server/testContexts/test-data-context.xml"};
	private static ApplicationContext ctx;
	private TableCreator tc = null;

	public TestDBSetup() {
		System.setProperty(TableCreator.AUTO_CREATE_TABLES,Boolean.toString(true));
		System.setProperty(TableCreator.AUTO_DROP_TABLES,Boolean.toString(true));
		ctx = new ClassPathXmlApplicationContext(configNames);
		tc = (TableCreator) ctx.getBean("tableCreator");
	}
	/**
	 * This method will auto instantiate all tables placed into spring configs.
	 */
	public void setUpTestSchema() {
		try {
			tc.initialize();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	/**
	 * This method will clean all tables from the database that are in the spring config
	 */
	public void cleanTestData() {
		tc.destroy();
	}
	/**
	 * This method will populate the database with test data
	 */
	public void setUpTestData() {
		createUsers();
		createChallenges();
		addUsersToChallenges();
	}

	private void addUsersToChallenges() {
		ctx = new ClassPathXmlApplicationContext(configNames);
		Dao<ChallengeUsers, Long> challengeUsersDao = (Dao<ChallengeUsers, Long>) ctx.getBean("challengeUsersDao");
		Dao<Challenge, Long> challengeDao = (Dao<Challenge, Long>) ctx.getBean("challengeDao");
		Dao<User, String> userDao = (Dao<User, String>) ctx.getBean("userDao");
		
		
		try {
			Resource resource = new ClassPathResource((String) ctx.getBean("testChallengesUsersFilePath"));
			InputStream in = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {

				ArrayList<String> challengeUsersProperties = new ArrayList<String>();
				challengeUsersProperties.addAll(Arrays.asList(strLine.split(",")));
				String username = challengeUsersProperties.get(0);
				String challengeTitle = challengeUsersProperties.get(1);
				boolean isAdmin = Boolean.valueOf(challengeUsersProperties.get(2));
				boolean isSubscribed = Boolean.valueOf(challengeUsersProperties.get(3));
				long creationDate = Long.decode(challengeUsersProperties.get(4));
				UserImpl user = (UserImpl) userDao.queryForId(username);		
				ChallengeImpl challenge = (ChallengeImpl) challengeDao.queryForEq("title", challengeTitle).get(0);
				
				ChallengeUsers challengeUser = new ChallengeUsersImpl(user, challenge, isAdmin, isSubscribed, creationDate);
				challengeUsersDao.create(challengeUser);
			}
			

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	//extremely messy way to populate a table. I think we can think of something better here.
	//will refactor once we start needing more test data
	@SuppressWarnings("unchecked")
	private void createUsers() {
		ctx = new ClassPathXmlApplicationContext(configNames);
		Dao<User, String> userDao = (Dao<User, String>) ctx.getBean("userDao");
		try {
			Resource resource = new ClassPathResource((String) ctx.getBean("testUsersFilePath"));
			InputStream in = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {

				ArrayList<String> userProperties = new ArrayList<String>();
				userProperties.addAll(Arrays.asList(strLine.split(",")));
				String username = userProperties.get(0);
				String password = userProperties.get(1);
				String firstname = userProperties.get(2);
				String lastname = userProperties.get(3);
				User user = new UserImpl(username, password, firstname, lastname);
				userDao.create(user);
			}

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@SuppressWarnings("unchecked")
	private void createChallenges() {
		ctx = new ClassPathXmlApplicationContext(configNames);
		Dao<Challenge, Long> challengesDao = (Dao<Challenge, Long>) ctx.getBean("challengeDao");
		try {
			Resource resource = new ClassPathResource((String) ctx.getBean("testChallengesFilePath"));
			InputStream in = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {

				ArrayList<String> challengeProperties = new ArrayList<String>();
				challengeProperties.addAll(Arrays.asList(strLine.split(",")));
				
				String title = challengeProperties.get(0);
				boolean isPrivate = Boolean.valueOf(challengeProperties.get(1));
				String location = challengeProperties.get(2);
				Long startDate = Long.decode(challengeProperties.get(3));
				Long endDate = Long.decode(challengeProperties.get(4));
				
				Challenge challenge = new ChallengeImpl(title, isPrivate, location, startDate, endDate);

				challengesDao.create(challenge);
			}

			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * This is an example of how to use this class
	 * @param args
	 */
	public static void main(String[] args) {
		TestDBSetup setup = new TestDBSetup();
		
		setup.setUpTestSchema();
		setup.setUpTestData();
		//setup.cleanTestData();
	}

}