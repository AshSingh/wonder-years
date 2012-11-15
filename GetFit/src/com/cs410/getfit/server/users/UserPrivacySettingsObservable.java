package com.cs410.getfit.server.users;

import java.util.Observable;

public class UserPrivacySettingsObservable extends Observable {

	private static UserPrivacySettingsObservable instance = null;
	
	private UserPrivacySettingsObservable() {
		//singleton
	}
	
	public void notifyMyObservers(long modifiedTime) {
		setChanged();
        notifyObservers(modifiedTime);
	}
	public static UserPrivacySettingsObservable getInstance() {
		if (instance == null) 
			instance = new UserPrivacySettingsObservable();
		return instance;
	}
}
