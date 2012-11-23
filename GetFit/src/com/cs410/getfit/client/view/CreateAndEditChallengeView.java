package com.cs410.getfit.client.view;

import com.google.gwt.dom.client.Element;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public interface CreateAndEditChallengeView {
	
	public interface Presenter {
		void onSaveChallengeButtonClicked();
		void onChangeLocationPreference();
		void onSearchAddressButtonClicked();
	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	MenuBarView getMenuBar();
	Widget asWidget();
	String getChallengeName();
	Label getChallengeNameLabel();
	String getLocation();
	String getAddress();
	String getDescription();
	Label getDescriptionLabel();
	boolean getIsPrivate();
	TextBox getChallengeNameBox();
	Hidden getLocationBox();
	TextArea getDescriptionBox();
	RadioButton getPrivacyPrivateRadioButton();
	RadioButton getPrivacyPublicRadioButton();
	RadioButton getLocationYesRadioButton();
	RadioButton getLocationNoRadioButton();
	void createMap();
	Element getLocationDiv();
	public HorizontalPanel getMap();
	public void setSearchedAddress(LatLng address);
	TextBox getSearchTextBox();
}
