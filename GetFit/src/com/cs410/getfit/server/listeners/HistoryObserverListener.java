package com.cs410.getfit.server.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.cs410.getfit.server.users.UserNewsfeedObserver;
import com.cs410.getfit.server.users.UserNewsfeedSource;

public class HistoryObserverListener implements ServletContextListener {
	UserNewsfeedSource source = UserNewsfeedSource.getInstance();

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		source.addObserver(UserNewsfeedObserver.getInstance());
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
		source.deleteObservers();
	}

}
