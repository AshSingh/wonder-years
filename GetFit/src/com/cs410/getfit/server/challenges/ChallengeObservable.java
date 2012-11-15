package com.cs410.getfit.server.challenges;

import java.util.Observable;

public class ChallengeObservable extends Observable {

	private static ChallengeObservable instance = null;
	
	private ChallengeObservable() {
		//singleton
	}
	
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
