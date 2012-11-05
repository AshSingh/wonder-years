package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChallengeViewImpl extends Composite implements ChallengeView {
	@UiField static Label titleLabel;
	@UiField static VerticalPanel challengeInfoPanel;
	@UiField static VerticalPanel newsFeedPanel;

	private Presenter presenter;
	private MenuBarView menuBar;
	
	@UiTemplate("Challenge.ui.xml") 
	interface Binder extends UiBinder<Widget, ChallengeViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public ChallengeViewImpl() {
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

	@Override
	public Label getTitleLabel() {
		return titleLabel;
	}

	@Override
	public VerticalPanel getChallengeInfoPanel() {
		return challengeInfoPanel;
	}

	@Override
	public VerticalPanel getNewsFeedPanel() {
		return newsFeedPanel;
	}
}
