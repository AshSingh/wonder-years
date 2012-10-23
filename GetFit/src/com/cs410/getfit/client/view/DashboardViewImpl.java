package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DashboardViewImpl extends Composite implements DashboardView {
	@UiField static Label createChallengeLink;
	
	private Presenter presenter;
	private MenuBarView menuBar;
	
	@UiTemplate("Dashboard.ui.xml") 
	interface Binder extends UiBinder<Widget, DashboardViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public DashboardViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		createChallengeLink.addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				if (presenter != null) {
					presenter.onCreateChallengeLinkClicked();
				}
			}
		});
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
