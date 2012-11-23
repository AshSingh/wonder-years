package com.cs410.getfit.client.view;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

public class DashboardViewImplTest extends GWTTestCase{

	DashboardView view; 
	MenuBarView menubar;
	HandlerManager eventBus;

	@Override
	@Before
	public void gwtSetUp(){
		view = new DashboardViewImpl();
		menubar = new MenuBarViewImpl();
	}

	@Test
	public void testSetMenuBar() {
		view.setMenuBar(menubar);
		assertEquals(view.getMenuBar(), menubar);
	}
	
	@Test
	public void testGetNameLabel() {
		assertTrue(view.getNameLabel().getClass().toString().contains("Label"));
	}

	@Test
	public void testGetNewsFeedPanel() {
		assertTrue(view.getNewsFeedPanel().getClass().toString().contains("VerticalPanel"));
	}

	@Test
	public void testGetUserChallengesPanel() {
		assertTrue(view.getUserChallengesPanel().getClass().toString().contains("VerticalPanel"));
	}
	
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}
