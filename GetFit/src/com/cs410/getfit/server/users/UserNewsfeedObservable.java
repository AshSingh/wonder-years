package com.cs410.getfit.server.users;

import java.util.Observable;

public class UserNewsfeedObservable extends Observable {

	private static UserNewsfeedObservable instance = null;
	
	private UserNewsfeedObservable() {
		//singleton
	}
	
	public void notifyHistorySource(long modifiedTime) {
		setChanged();
        notifyObservers(modifiedTime);
	}
	public static UserNewsfeedObservable getInstance() {
		if (instance == null) 
			instance = new UserNewsfeedObservable();
		return instance;
	}
}
