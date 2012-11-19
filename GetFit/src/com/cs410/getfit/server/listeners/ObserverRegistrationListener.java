package com.cs410.getfit.server.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cs410.getfit.server.challenges.ChallengeObservable;
import com.cs410.getfit.server.users.UserNewsfeedObserver;
import com.cs410.getfit.server.users.UserPrivacySettingsObservable;
/**
 * Listener to register all of the apps observers
 * @author kiramccoan
 *
 */
public class ObserverRegistrationListener implements ServletContextListener {
	ChallengeObservable source = ChallengeObservable.getInstance();
	UserPrivacySettingsObservable ps_source = UserPrivacySettingsObservable.getInstance();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		registerObservers();
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		unregisterObservers();
	}
	private void registerObservers() {
		source.addObserver(UserNewsfeedObserver.getInstance());
		ps_source.addObserver(UserNewsfeedObserver.getInstance());
	}
	
	private void unregisterObservers() {
		source.deleteObservers();
		ps_source.deleteObservers();
	}
}
