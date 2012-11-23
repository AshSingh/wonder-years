package com.cs410.getfit.client.view;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

public class UserSettingsViewImplTest extends GWTTestCase{

	UserSettingsView view; 
	MenuBarView menubar;
	HandlerManager eventBus;

	@Override
	@Before
	public void gwtSetUp(){
		view = new UserSettingsViewImpl();
		menubar = new MenuBarViewImpl();
	}

	@Test
	public void testGetNameLabel() {
		assertTrue(view.getNameLabel().getClass().toString().contains("Label"));
	}

	@Test
	public void testGetPrivacyPrivateRadioButton() {
		assertTrue(view.getPrivacyPrivateRadioButton().getClass().toString().contains("RadioButton"));
	}
	
	@Test
	public void testGetPrivacyPublicRadioButton() {
		assertTrue(view.getPrivacyPublicRadioButton().getClass().toString().contains("RadioButton"));
	}
	
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}
