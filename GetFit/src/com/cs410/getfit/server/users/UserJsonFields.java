package com.cs410.getfit.server.users;

public enum UserJsonFields {
	USERS,
	USERNAME,
	PASSWORD,
	FIRSTNAME,
	LASTNAME;
	
	 @Override 
	 public String toString() {
		  //lowercase
		   return super.toString().toLowerCase();
	}
}
