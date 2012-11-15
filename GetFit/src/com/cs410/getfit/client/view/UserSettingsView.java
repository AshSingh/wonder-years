package com.cs410.getfit.client.view;

import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public interface UserSettingsView {
	
	public interface Presenter {
		void onSaveSettingsButtonClicked();
	}
	
	void setPresenter(Presenter presenter);
	void setMenuBar(MenuBarView menuBar);
	MenuBarView getMenuBar();
	Widget asWidget();
	Label getNameLabel();
	boolean getIsPrivate();
	RadioButton getPrivacyPrivateRadioButton();
	RadioButton getPrivacyPublicRadioButton();
}
