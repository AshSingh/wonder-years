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

	private Presenter presenter;
	private MenuBarView menuBar;
	
	@UiTemplate("Challenge.ui.xml") 
	interface Binder extends UiBinder<Widget, ChallengeViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);

	/**
	 * Initializes and binds widgets from xml 
	 */
	public ChallengeViewImpl() {
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
	 * Get the title label 
	 * 
	 * @return title widget
	 */
	@Override
	public Label getTitleLabel() {
		return titleLabel;
	}

	/**
	 * Get the challenge info panel
	 * 
	 * @return the panel widget for the challenge info
	 */
	@Override
	public VerticalPanel getChallengeInfoPanel() {
		return challengeInfoPanel;
	}
}
