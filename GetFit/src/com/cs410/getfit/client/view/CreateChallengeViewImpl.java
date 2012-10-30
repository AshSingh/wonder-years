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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CreateChallengeViewImpl extends Composite implements CreateChallengeView {
	@UiField static TextBox challengeNameBox;
	@UiField static TextBox locationBox;
	@UiField static TextArea descriptionBox;
	@UiField static RadioButton privacyPrivate;
	@UiField static Label challengeLabel;
	@UiField static Label descriptionLabel;
	
	private Presenter presenter;
	private MenuBarView menuBar;

	@UiTemplate("CreateChallenge.ui.xml") 
	interface Binder extends UiBinder<Widget, CreateChallengeViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);

	public CreateChallengeViewImpl() {
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
	
	@UiHandler("createChallengeBtn")
	void onCreateChallengeClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onCreateChallengeButtonClicked();			
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
	public boolean isPrivate() {
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
	
}
