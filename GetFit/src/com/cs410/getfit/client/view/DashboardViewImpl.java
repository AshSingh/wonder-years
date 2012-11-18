package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class DashboardViewImpl extends Composite implements DashboardView {
	@UiField static VerticalPanel newsFeedPanel;
	@UiField static Label nameLabel;
	@UiField static VerticalPanel userChallengesPanel;
	
	private Presenter presenter;
	private MenuBarView menuBar;
	
	@UiTemplate("Dashboard.ui.xml") 
	interface Binder extends UiBinder<Widget, DashboardViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	/**
	 * Initializes and binds widgets from xml 
	 */
	public DashboardViewImpl() {
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
	 * Get the panel containing newsfeed
	 * 
	 * @return newsfeed panel widget
	 */
	@Override
	public VerticalPanel getNewsFeedPanel(){
		return newsFeedPanel;
	}
	
	/**
	 * Get the name label
	 * 
	 * @return name label widget
	 */
	@Override
	public Label getNameLabel(){
		return nameLabel;
	}
	
	/**
	 * Get the panel that contains the user's challenges
	 * 
	 * @return user's challenges panel widget
	 */
	@Override
	public VerticalPanel getUserChallengesPanel(){
		return userChallengesPanel;
	}
	
}
