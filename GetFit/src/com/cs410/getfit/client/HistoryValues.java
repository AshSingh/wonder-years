package com.cs410.getfit.client;

public enum HistoryValues {
	LOGIN,
	DASHBOARD,
	CREATECHALLENGE,
	ERROR,
	EDIT,
	CHALLENGES,
	SETTINGS;
	
	/**
	 * Converts string value to lowercase
	 * 
	 * @return string in lowercase
	 */
	 @Override 
	 public String toString() {
		  //lowercase
		   return super.toString().toLowerCase();
	}
}
