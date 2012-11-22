package com.cs410.getfit.server.models;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UserImplTest {

	String FB_ID = "abcd";
	String firstname = "John";
	String lastname = "Doe";
	boolean isPrivate = true;
	long guid = 2;
	
	User user = new UserImpl(FB_ID, firstname, lastname, isPrivate) ;
	
	@Test
	public void setFB_IDTest() {
		user.setFB_ID(FB_ID);
		assertEquals(user.getFB_ID(), FB_ID);
	}

	@Test
	public void setFirstNameTest() {
		user.setFirstName(firstname);
		assertEquals(user.getFirstName(), firstname);
	}
	
	@Test
	public void setLastNameTest() {
		user.setLastName(lastname);
		assertEquals(user.getLastName(), lastname);
	}
	
	@Test
	public void setIsPrivateTest() {
		user.setIsPrivate(isPrivate);
		assertEquals(user.getIsPrivate(), isPrivate);
	}
	
	@Test
	public void setGuidTest() {
		user.setGuid(guid);
		assertEquals((long) user.getGuid(), guid);
	}

	@Test
	public void toStringTest() {
		String test = "FB_ID: " + FB_ID + " Name: " + firstname + "  lastname: " + lastname + " isPrivate: " + isPrivate;
		assertEquals(user.toString(), test);
	}
}
