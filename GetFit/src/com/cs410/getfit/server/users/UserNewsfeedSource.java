package com.cs410.getfit.server.users;

import java.util.Observable;

public class UserNewsfeedSource extends Observable {

	private static UserNewsfeedSource instance = null;
	
	private UserNewsfeedSource() {
		//singleton
	}
	
	public void notifyHistorySource(long modifiedTime) {
		setChanged();
        notifyObservers(modifiedTime);
	}
	public static UserNewsfeedSource getInstance() {
		if (instance == null) 
			instance = new UserNewsfeedSource();
		return instance;
	}
}
