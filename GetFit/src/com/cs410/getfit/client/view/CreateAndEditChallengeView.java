package com.cs410.getfit.client.view;

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
	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	MenuBarView getMenuBar();
	Widget asWidget();
	String getChallengeName();
	Label getChallengeNameLabel();
	String getLocation();
	String getDescription();
	Label getDescriptionLabel();
	boolean getIsPrivate();
	TextBox getChallengeNameBox();
	Hidden getLocationBox();
	TextArea getDescriptionBox();
	RadioButton getPrivacyPrivateRadioButton();
	RadioButton getPrivacyPublicRadioButton();
	void createMap();
	public HorizontalPanel getMap();
}
