package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CreateChallengeViewImpl extends Composite implements CreateChallengeView {
	
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
}
