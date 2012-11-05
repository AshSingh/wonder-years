package com.cs410.getfit.server.json;

public enum LinkTypes {
	CHALLENGE,
	PARTICIPANT,
	PARTICIPANTS,
	USER,
	COMPLETEDCHALLENGE;
	
	 @Override 
	 public String toString() {
		  //lowercase
		   return super.toString().toLowerCase();
	}
}
