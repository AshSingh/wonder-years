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

	/**
	 * Initializes and binds widgets from xml 
	 */
	public UserSettingsViewImpl() {
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
	 * Save settings button clicked - call presenter to handle
	 * 
	 * @param event - click event
	 */
	@UiHandler("saveSettingsBtn")
	void onCreateChallengeClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onSaveSettingsButtonClicked();			
		}
	}
	
	/**
	 * Get the input for privacy setting
	 * 
	 * @return true - setting is private
	 * 		   false - setting is public
	 */
	@Override
	public boolean getIsPrivate() {
		return privacyPrivate.getValue();
	}

	/**
	 * Get the name label 
	 * 
	 * @return name widget
	 */
	@Override
	public Label getNameLabel() {
		return nameLabel;
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
	
}
