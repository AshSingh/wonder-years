package com.cs410.getfit.server.users;

import java.util.Observable;

public class UserNewsfeedSource extends Observable implements Runnable {
	private boolean newItemAddedToHistory = false;
	private long modifiedTime = 0;
	private static UserNewsfeedSource instance = null;
	private boolean stopLoop = false;
	
	private UserNewsfeedSource() {
		//singleton
	}
	
	@Override
	public void run() {
        while (true) {
        	if(newItemAddedToHistory) {
	            long response = modifiedTime;
	            setChanged();
	            notifyObservers(response);
	            newItemAddedToHistory = false;
	        }
        	if(stopLoop)
        		break;
        }
	}
	public void notifyHistorySource(long modifiedTime) {
		this.modifiedTime = modifiedTime;
		newItemAddedToHistory = true;
	}
	public static UserNewsfeedSource getInstance() {
		if (instance == null) 
			instance = new UserNewsfeedSource();
		return instance;
	}
	public void stop(){
		stopLoop = true;
	}
}
