package com.cs410.getfit.client.view;

import com.google.gwt.core.client.Callback;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.geolocation.client.Position;
import com.google.gwt.geolocation.client.PositionError;
import com.google.gwt.maps.client.MapOptions;
import com.google.gwt.maps.client.MapTypeId;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class CreateAndEditChallengeViewImpl extends Composite implements CreateAndEditChallengeView {
	@UiField static TextBox challengeNameBox;
	@UiField static TextBox locationBox;
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

	public CreateAndEditChallengeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}

	@Override
	public void setMenuBar(MenuBarView menuBar) {
		this.menuBar = menuBar;
	}

	@Override
	public MenuBarView getMenuBar() {
		return menuBar;
	}
	
	@UiHandler("saveChallengeBtn")
	void onCreateChallengeClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onSaveChallengeButtonClicked();			
		}
	}

	@Override
	public String getChallengeName() {
		return challengeNameBox.getText().toString();
	}

	@Override
	public String getLocation() {
		return locationBox.getText().toString();
	}

	@Override
	public boolean getIsPrivate() {
		return privacyPrivate.getValue();
	}

	@Override
	public Label getChallengeNameLabel() {
		return challengeLabel;
	}

	@Override
	public String getDescription() {
		return descriptionBox.getText();
	}

	@Override
	public Label getDescriptionLabel() {
		return descriptionLabel;
	}

	
	/*
	 * 	field getters for edit view
	 */
	
	@Override
	public TextBox getChallengeNameBox() {
		return challengeNameBox;
	}

	@Override
	public TextBox getLocationBox() {
		return locationBox;
	}

	@Override
	public TextArea getDescriptionBox() {
		return descriptionBox;
	}

	@Override
	public RadioButton getPrivacyPrivateRadioButton() {
		return privacyPrivate;
	}

	@Override
	public RadioButton getPrivacyPublicRadioButton() {
		return privacyPublic;
	}
	
	@Override
	public void createMap() {
		Geolocation userLocation = Geolocation.getIfSupported();
		if(userLocation != null) {
			PositionCallback posCallback = new PositionCallback(mapWidget, gMapsPanel, locationBox);
			userLocation.getCurrentPosition((Callback) posCallback);
		}
	}
	
	@Override
	public HorizontalPanel getMap() {
		return this.gMapsPanel;
	} 
}
