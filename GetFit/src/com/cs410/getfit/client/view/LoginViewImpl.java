package com.cs410.getfit.client.view;

import com.cs410.getfit.client.event.HistoryValues;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class LoginViewImpl extends Composite implements LoginView {
	@UiField static TextBox usernameBox;
	@UiField static PasswordTextBox pwdBox;
	@UiField static Hyperlink registerLink;
	
	private Presenter presenter;
	
	@UiTemplate("Login.ui.xml") 
	interface Binder extends UiBinder<Widget, LoginViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public LoginViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		// set hyperlink target for history and navigation
		registerLink.setTargetHistoryToken(HistoryValues.REGISTER.toString());
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@Override
	public String getUsername(){
		return usernameBox.getText();
	}
	
	@Override
	public String getPassword(){
		return pwdBox.getText();
	}
	
	@UiHandler("loginBtn")
	void onLoginClicked(ClickEvent event) {
		if (presenter != null) {
			presenter.onLoginButtonClicked();
		}
	}
}
