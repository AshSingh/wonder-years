package com.cs410.getfit.client.view;

import org.junit.Before;
import org.junit.Test;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

public class CreateAndEditChallengeViewImplTest extends GWTTestCase{

	CreateAndEditChallengeView view; 
	MenuBarView menubar;
	HandlerManager eventBus;

	@Override
	@Before
	public void gwtSetUp(){
		view = new CreateAndEditChallengeViewImpl();
		menubar = new MenuBarViewImpl();
	}

	@Test
	public void testSetMenuBar() {
		view.setMenuBar(menubar);
		assertEquals(view.getMenuBar(), menubar);
	}
	
	@Test
	public void testGetChallengeNameBox() {
		assertTrue(view.getChallengeNameBox().getClass().toString().contains("TextBox"));
	}

	@Test
	public void testGetChallengeNameLabel() {
		assertTrue(view.getChallengeNameLabel().getClass().toString().contains("Label"));
	}

	@Test
	public void testGetDescriptionBox() {
		assertTrue(view.getDescriptionBox().getClass().toString().contains("TextArea"));
	}
	
	@Test
	public void testGetDescriptionLabel() {
		assertTrue(view.getDescriptionLabel().getClass().toString().contains("Label"));
	}
	
	@Test
	public void testGetLocationBox() {
		assertTrue(view.getLocationBox().getClass().toString().contains("Hidden"));
	}

	@Test
	public void testGetLocationNoRadioButton() {
		assertTrue(view.getLocationNoRadioButton().getClass().toString().contains("RadioButton"));
	}
	
	@Test
	public void testGetLocationYesRadioButton() {
		assertTrue(view.getLocationYesRadioButton().getClass().toString().contains("RadioButton"));
	}
	
	@Test
	public void testGetPrivacyPrivateRadioButton() {
		assertTrue(view.getPrivacyPrivateRadioButton().getClass().toString().contains("RadioButton"));
	}
	
	@Test
	public void testGetPrivacyPublicRadioButton() {
		assertTrue(view.getPrivacyPublicRadioButton().getClass().toString().contains("RadioButton"));
	}

	@Test
	public void testGetMap() {
		assertTrue(view.getMap().getClass().toString().contains("HorizontalPanel"));
	}
	
	@Test
	public void testChallengeName() {
		assertTrue(view.getChallengeName().getClass().toString().contains("String"));
	}
	
	@Test
	public void testDescription() {
		assertTrue(view.getDescription().getClass().toString().contains("String"));
	}
	
	@Test
	public void testLocation() {
		assertTrue(view.getLocation().getClass().toString().contains("String"));
	}
	
	@Test
	public void testPrivacy() {
		assertFalse(view.getIsPrivate());
	}

	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}
