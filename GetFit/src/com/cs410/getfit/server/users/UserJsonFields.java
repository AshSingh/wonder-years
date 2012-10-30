package com.cs410.getfit.server.users;

public enum UserJsonFields {
	USERS,
	FB_ID,
	FIRSTNAME,
	LASTNAME,
	ISPRIVATE;
	
	 @Override 
	 public String toString() {
		  //lowercase
		   return super.toString().toLowerCase();
	}
}
