package com.cs410.getfit.shared;

public enum LinkTypes {
	CHALLENGE,
	PARTICIPANT,
	PARTICIPANTS,
	USER,
	COMPLETEDCHALLENGE,
	COMPLETEDCHALLENGES, 
	NEWSFEED, 
	USERCHALLENGES;
	
	 @Override 
	 public String toString() {
		  //lowercase
		   return super.toString().toLowerCase();
	}
}
