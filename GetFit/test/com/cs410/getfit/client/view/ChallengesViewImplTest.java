package com.cs410.getfit.client.view;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;

public class ChallengesViewImplTest extends GWTTestCase{

	ChallengesView view; 
	MenuBarView menubar;

	@Override
	@Before
	public void gwtSetUp(){
		view = new ChallengesViewImpl();
		menubar = new MenuBarViewImpl();
	}

	@Test
	public void testSetMenuBar() {
		view.setMenuBar(menubar);
		assertEquals(view.getMenuBar(), menubar);
	}


	@Test
	public void testGetUserChallengesPanel() {
		assertTrue(view.getUserChallengesPanel().getClass().toString().contains("VerticalPanel"));
	}

	@Test
	public void testGetChallengesPanel() {
		assertTrue(view.getChallengesPanel().getClass().toString().contains("VerticalPanel"));
	}

	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}
