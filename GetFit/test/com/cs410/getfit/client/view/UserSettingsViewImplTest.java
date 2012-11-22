package com.cs410.getfit.client.view;

import org.junit.Before;
import org.junit.Test;

import com.cs410.getfit.client.presenter.ErrorPresenter;
import com.cs410.getfit.client.presenter.LoginPresenter;
import com.cs410.getfit.client.presenter.UserSettingsPresenter;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

public class UserSettingsViewImplTest extends GWTTestCase{

	UserSettingsView view; 
	MenuBarView menubar;
	UserSettingsPresenter presenter;
	HandlerManager eventBus;

	@Before
	public void gwtSetUp(){
		view = new UserSettingsViewImpl();
		menubar = new MenuBarViewImpl();
		presenter = new UserSettingsPresenter(eventBus, view);
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
