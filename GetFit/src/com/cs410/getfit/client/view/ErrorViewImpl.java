package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class ErrorViewImpl extends Composite implements ErrorView {
	@UiField static Label errorLabel;

	private Presenter presenter;
	private MenuBarView menuBar;
	
	@UiTemplate("Error.ui.xml") 
	interface Binder extends UiBinder<Widget, ErrorViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public ErrorViewImpl() {
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
	public Label getErrorLabel() {
		return errorLabel;
	}

}
