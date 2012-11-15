package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;

public class UserSettingsViewImpl extends Composite implements UserSettingsView {
	@UiField static Label nameLabel;
	@UiField static RadioButton privacyPrivate;
	@UiField static RadioButton privacyPublic;
	
	private Presenter presenter;
	private MenuBarView menuBar;

	@UiTemplate("UserSettings.ui.xml") 
	interface Binder extends UiBinder<Widget, UserSettingsViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);

	public UserSettingsViewImpl() {
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
	
	@UiHandler("saveSettingsBtn")
	void onCreateChallengeClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onSaveSettingsButtonClicked();			
		}
	}
	
	@Override
	public boolean getIsPrivate() {
		return privacyPrivate.getValue();
	}

	@Override
	public Label getNameLabel() {
		return nameLabel;
	}

	@Override
	public RadioButton getPrivacyPrivateRadioButton() {
		return privacyPrivate;
	}

	@Override
	public RadioButton getPrivacyPublicRadioButton() {
		return privacyPublic;
	}
	
}
