package com.cs410.getfit.server.challenges;

import java.util.Observable;

/**
 * Represents an observable feature of challenges
 * @author kiramccoan
 *
 */
public class ChallengeObservable extends Observable {

	private static ChallengeObservable instance = null;
	
	private ChallengeObservable() {
		//singleton
	}
	/**
	 * Notify all registered observers of the time change
	 * @param modifiedTime current time of modification
	 */
	public void notifyMyObservers(long modifiedTime) {
		setChanged();
        notifyObservers(modifiedTime);
	}
	public static ChallengeObservable getInstance() {
		if (instance == null) 
			instance = new ChallengeObservable();
		return instance;
	}
}
