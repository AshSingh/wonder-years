package com.cs410.getfit.server.users;

import java.util.Observable;
import java.util.Observer;
/**
 * An Observer which watches news feed observable agents
 * @author kiramccoan
 *
 */
public class UserNewsfeedObserver implements Observer {
		private static UserNewsfeedObserver instance = null;
		private UserNewsfeedObserver() {
			//singleton
		}
	    private long lastModifiedAt = 0;
	    public void update(Observable obj, Object arg) {
	    	lastModifiedAt = (Long) arg;
	    }
	    /**
	     * @return the time that the newsfeed was last modified
	     */
	    public long getLastModified() {
	    	return lastModifiedAt;
	    }
	    
	    public static UserNewsfeedObserver getInstance() {
	    	if(instance == null)
	    		instance = new UserNewsfeedObserver();
	    	return instance;
	    }
}
