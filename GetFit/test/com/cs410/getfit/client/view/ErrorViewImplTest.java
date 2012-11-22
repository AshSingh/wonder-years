package com.cs410.getfit.client.view;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.client.presenter.ErrorPresenter;
import com.cs410.getfit.client.presenter.MenuBarPresenter;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

public class ErrorViewImplTest extends GWTTestCase{

	ErrorView view; 
	MenuBarView menubar;
	ErrorPresenter presenter;
	HandlerManager eventBus;
	MenuBarPresenter menuPresenter;
	
	@Override
	@Before
	public void gwtSetUp(){
		view = new ErrorViewImpl();
		menubar = new MenuBarViewImpl();
		presenter = new ErrorPresenter(eventBus, view);
		menuPresenter = new MenuBarPresenter(eventBus, menubar);
	}

	@Test
	public void testSetMenuBar() {
		view.setMenuBar(menubar);
		assertEquals(view.getMenuBar(), menubar);
	}
	
	@Test
	public void testGetErrorLabel() {
		assertTrue(view.getErrorLabel().getClass().toString().contains("Label"));
	}

	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}
