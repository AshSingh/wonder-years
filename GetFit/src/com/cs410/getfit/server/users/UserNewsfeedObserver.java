package com.cs410.getfit.server.users;

import java.util.Observable;
import java.util.Observer;

public class UserNewsfeedObserver implements Observer {
		private static UserNewsfeedObserver instance = null;
		private UserNewsfeedObserver() {
			//singleton
		}
	    private long lastModifiedAt = -1;
	    public void update(Observable obj, Object arg) {
	    	lastModifiedAt = (Long) arg;
	    }
	    public long getLastModified() {
	    	return lastModifiedAt;
	    }
	    
	    public static UserNewsfeedObserver getInstance() {
	    	if(instance == null)
	    		instance = new UserNewsfeedObserver();
	    	return instance;
	    }
}
