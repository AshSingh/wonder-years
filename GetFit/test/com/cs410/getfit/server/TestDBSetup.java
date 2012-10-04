package com.cs410.getfit.server;

import java.io.BufferedReader;
import java.io.FileInputStream;
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

import com.cs410.getfit.shared.User;
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
	}

	//extremely messy way to populate a table. I think we can think of something better here.
	//will refactor once we start needing more test data
	private void createUsers() {
		ctx = new ClassPathXmlApplicationContext(configNames);
		Dao<User, String> userDao = (Dao<User, String>) ctx.getBean("userDao");
		FileInputStream fstream;
		try {
			Resource resource = new ClassPathResource((String) ctx.getBean("testUsersFilePath"));
			InputStream in = resource.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;

			while ((strLine = br.readLine()) != null) {

				ArrayList<String> userProperties = new ArrayList<String>();
				userProperties.addAll(Arrays.asList(strLine.split(",")));
				userDao.updateRaw("INSERT INTO users (username, password, firstname, lastname) "
						+ "VALUES ("
						+ userProperties.get(0)
						+ ","
						+ userProperties.get(1)
						+ ","
						+ userProperties.get(2)
						+ "," 
						+ userProperties.get(3) + ")");
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
		//setup.cleanTestData(); //uncomment this line to keep test data around.
	}

}