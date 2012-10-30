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

public class ChallengeViewImpl extends Composite implements ChallengeView {
	
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
	
}
