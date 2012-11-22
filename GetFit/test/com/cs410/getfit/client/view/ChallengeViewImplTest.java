package com.cs410.getfit.client.view;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.client.presenter.ChallengePresenter;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

public class ChallengeViewImplTest extends GWTTestCase{

	ChallengeView view; 
	MenuBarView menubar;
	ChallengePresenter presenter;
	HandlerManager eventBus;

	@Before
	public void gwtSetUp(){
		view = new ChallengeViewImpl();
		menubar = new MenuBarViewImpl();
		presenter = new ChallengePresenter(eventBus, view);
	}

	@Test
	public void testSetMenuBar() {
		view.setMenuBar(menubar);
		assertEquals(view.getMenuBar(), menubar);
	}
	
	@Test
	public void testGetChallengeInfoPanel() {
		assertTrue(view.getChallengeInfoPanel().getClass().toString().contains("VerticalPanel"));
	}

	@Test
	public void testGetTitleLabel() {
		assertTrue(view.getTitleLabel().getClass().toString().contains("Label"));
	}

	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}
