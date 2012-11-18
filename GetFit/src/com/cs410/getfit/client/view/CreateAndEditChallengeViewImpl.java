package com.cs410.getfit.client.view;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateAndEditChallengeViewImpl extends Composite implements CreateAndEditChallengeView {
	@UiField static TextBox challengeNameBox;
	@UiField static Hidden locationBox;
	@UiField static TextArea descriptionBox;
	@UiField static RadioButton privacyPrivate;
	@UiField static RadioButton privacyPublic;
	@UiField static Label challengeLabel;
	@UiField static Label descriptionLabel;
	@UiField static HorizontalPanel gMapsPanel;
	
	private Presenter presenter;
	private MenuBarView menuBar;
	private MapWidget mapWidget;

	@UiTemplate("CreateAndEditChallenge.ui.xml") 
	interface Binder extends UiBinder<Widget, CreateAndEditChallengeViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);

	/**
	 * Initializes and binds widgets from xml 
	 */
	public CreateAndEditChallengeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/** 
	 * Set the presenter for the view
	 */
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	/** 
	 * Set the menu bar for the view
	 */
	@Override
	public void setMenuBar(MenuBarView menuBar) {
		this.menuBar = menuBar;
	}

	/**
	 * Get the menu bar for the view
	 * 
	 * @return menu bar widget
	 */
	@Override
	public MenuBarView getMenuBar() {
		return menuBar;
	}
	
	/**
	 * Save challenge button clicked - call presenter to handle
	 * 
	 * @param event - click event
	 */
	@UiHandler("saveChallengeBtn")
	void onCreateChallengeClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onSaveChallengeButtonClicked();			
		}
	}

	/**
	 * Get the input for challenge name
	 * 
	 * @return challenge name input
	 */
	@Override
	public String getChallengeName() {
		return challengeNameBox.getText().toString();
	}

	/**
	 * Get the input for location
	 * 
	 * @return location input
	 */	
	@Override
	public String getLocation() {
		return locationBox.getValue().toString();
	}

	/**
	 * Get the input for privacy setting
	 * 
	 * @return true - challenge is private
	 * 		   false - challenge is public
	 */
	@Override
	public boolean getIsPrivate() {
		return privacyPrivate.getValue();
	}

	/**
	 * Get the challenge name label 
	 * 
	 * @return challenge name label widget
	 */
	@Override
	public Label getChallengeNameLabel() {
		return challengeLabel;
	}

	/**
	 * Get the input for description
	 * 
	 * @return description input
	 */	
	@Override
	public String getDescription() {
		return descriptionBox.getText();
	}

	/**
	 * Get the description label 
	 * 
	 * @return description label widget
	 */
	@Override
	public Label getDescriptionLabel() {
		return descriptionLabel;
	}

	
	/*
	 * 	field getters for edit view
	 */
	
	/**
	 * Get the challenge name input box 
	 * 
	 * @return challenge name input box widget
	 */	
	@Override
	public TextBox getChallengeNameBox() {
		return challengeNameBox;
	}

	/**
	 * Get the location input box 
	 * 
	 * @return location input box widget
	 */
	@Override
	public Hidden getLocationBox() {
		return locationBox;
	}

	/**
	 * Get the description input box 
	 * 
	 * @return description input box widget
	 */
	@Override
	public TextArea getDescriptionBox() {
		return descriptionBox;
	}

	/**
	 * Get the privacy radio button - private
	 * 
	 * @return radio button for private privacy setting
	 */	
	@Override
	public RadioButton getPrivacyPrivateRadioButton() {
		return privacyPrivate;
	}

	/**
	 * Get the privacy radio button - public
	 * 
	 * @return radio button for public privacy setting
	 */	
	@Override
	public RadioButton getPrivacyPublicRadioButton() {
		return privacyPublic;
	}
	
	/**
	 * Creates map for location setting
	 */
	@Override
	public void createMap() {
		Geolocation userLocation = Geolocation.getIfSupported();
		if(userLocation != null) {
			PositionCallback posCallback = new PositionCallback(mapWidget, gMapsPanel, locationBox);
			userLocation.getCurrentPosition((Callback) posCallback);
		}
	}
	
	/**
	 * Get the panel containing the map for location settings
	 * 
	 * @return panel containing map widget
	 */		
	@Override
	public HorizontalPanel getMap() {
		return this.gMapsPanel;
	} 
}
