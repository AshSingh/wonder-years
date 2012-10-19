package com.cs410.getfit.server.challenges;

public enum ChallengeJsonFields {
	GUID,
	TITLE,
	STARTDATE,
	ENDDATE,
	LOCATION,
	ISPRIVATE,
	CHALLENGES;
	
	 @Override 
	 public String toString() {
		  //lowercase
		   return super.toString().toLowerCase();
	}
}
