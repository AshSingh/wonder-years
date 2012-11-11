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
	
	public ChallengesViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@UiHandler("newChallengeBtn")
	void onCreateChallengeClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onNewChallengeButtonClicked();			
		}
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
	public VerticalPanel getUserChallengesPanel() {
		return userChallengesPanel;
	}
	
	@Override
	public VerticalPanel getChallengesPanel() {
		return challengesPanel;
	}
}
