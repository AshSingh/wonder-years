package com.cs410.getfit.client;

public enum HistoryValues {
	LOGIN,
	REGISTER,
	DASHBOARD,
	CREATECHALLENGE,
	ERROR,
	EDIT;
	
	 @Override 
	 public String toString() {
		  //lowercase
		   return super.toString().toLowerCase();
	}
}
