package com.cs410.getfit.client;

public enum HistoryValues {
	LOGIN,
	REGISTER,
	DASHBOARD,
	CREATECHALLENGE,
	ERROR,
	EDIT,
	CHALLENGES,
	SETTINGS;
	
	 @Override 
	 public String toString() {
		  //lowercase
		   return super.toString().toLowerCase();
	}
}
