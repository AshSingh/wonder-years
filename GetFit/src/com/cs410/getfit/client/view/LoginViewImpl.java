package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class LoginViewImpl extends Composite implements LoginView {
	
	private Presenter presenter;
	
	@UiTemplate("Login.ui.xml") 
	interface Binder extends UiBinder<Widget, LoginViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	/**
	 * Initializes and binds widgets from xml 
	 */
	public LoginViewImpl() {
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
	 * Login button clicked - call presenter to handle
	 * 
	 * @param event - click event
	 */
	@UiHandler("loginBtn")
	void addClickHandler(ClickEvent event) {
		event.preventDefault();
		if (presenter != null) {
			presenter.onLoginButtonClicked();
		}
	}
}
