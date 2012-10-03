package com.cs410.getfit.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginView extends Composite implements EntryPoint{
	@UiField TextBox emailBox;
	@UiField PasswordTextBox pwdBox;
	
	@UiTemplate("Login.ui.xml") 
	interface Binder extends UiBinder<Widget, LoginView> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@UiHandler("loginBtn")
	void onLoginClicked(ClickEvent event) {
		 System.out.println("login clicked");
		 // pass on to presenter
	}

	@Override
	public void onModuleLoad() {
		LoginView loginView = new LoginView();
		RootPanel.get().add(loginView);
	}

}
