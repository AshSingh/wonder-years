package com.cs410.getfit.server.users;

import java.util.Observable;
/**
 * represents an observable feature of users
 * @author kiramccoan
 *
 */
public class UserPrivacySettingsObservable extends Observable {

	private static UserPrivacySettingsObservable instance = null;
	
	private UserPrivacySettingsObservable() {
		//singleton
	}
	/**
	 * notify registered observers of last modified time.
	 * @param modifiedTime
	 */
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
