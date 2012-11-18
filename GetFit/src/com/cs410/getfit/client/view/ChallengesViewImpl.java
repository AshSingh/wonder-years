package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChallengesViewImpl extends Composite implements ChallengesView {
	@UiField static VerticalPanel userChallengesPanel;
	@UiField static VerticalPanel challengesPanel;

	private Presenter presenter;
	private MenuBarView menuBar;
	
	@UiTemplate("Challenges.ui.xml") 
	interface Binder extends UiBinder<Widget, ChallengesViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	/**
	 * Initializes and binds widgets from xml 
	 */
	public ChallengesViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	/**
	 * Create challenge button clicked - call presenter to handle
	 * 
	 * @param event - click event
	 */
	@UiHandler("newChallengeBtn")
	void onCreateChallengeClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onNewChallengeButtonClicked();			
		}
	}

	/**
	 * Sort by location clicked - call presenter to handle
	 * 
	 * @param event - click event
	 */
	@UiHandler("sortByLocationBtn")
	void onSortByLocationClicked(ClickEvent event) {
		if(presenter != null) {
			presenter.onSortByLocationButtonClicked();
		}
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
	 * Get the User Challenges Panel
	 * 
	 * @return the panel widget for the user's challenges
	 */
	@Override
	public VerticalPanel getUserChallengesPanel() {
		return userChallengesPanel;
	}
	
	/**
	 * Get the Public Challenges panel
	 * 
	 * @return the panel widget for the list of public challenges
	 */
	@Override
	public VerticalPanel getChallengesPanel() {
		return challengesPanel;
	}
}
