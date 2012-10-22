package com.cs410.getfit.client.view;

import com.cs410.getfit.client.event.HistoryValues;
import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Widget;

public class DashboardViewImpl extends Composite implements DashboardView {
	@UiField static Hyperlink createChallengeLink;
	
	private Presenter presenter;
	private MenuBarView menuBar;
	
	@UiTemplate("Dashboard.ui.xml") 
	interface Binder extends UiBinder<Widget, DashboardViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public DashboardViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		// set hyperlink target for history and navigation
		createChallengeLink.setTargetHistoryToken(HistoryValues.CREATECHALLENGE.toString());
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
}
