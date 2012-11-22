package com.cs410.getfit.client.event;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.client.AppController;
import com.cs410.getfit.client.GetFit;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.user.client.History;

public class AppControllerTest extends GWTTestCase{
	HandlerManager eventBus ;
	AppController appViewer;
	
	@Override
	@Before
	public void gwtSetUp(){
		GetFit getfit = new GetFit();
		getfit.onModuleLoad();
		eventBus = new HandlerManager(null);
	    appViewer = new AppController(eventBus);
	}
	
	@Test
	public void testLoginHistoryToken() {
		assertEquals("login", History.getToken());
	}
	
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}
