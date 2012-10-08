package com.cs410.getfit.client;

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

public class LoginViewImpl extends Composite implements LoginView{
	@UiField static TextBox usernameBox;
	@UiField static PasswordTextBox pwdBox;
	@UiField static Hyperlink registerLink;
	
	private Presenter presenter;
	
	private static String tempUserName;
	private static String tempPass;
	
	@UiTemplate("Login.ui.xml") 
	interface Binder extends UiBinder<Widget, LoginViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public LoginViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}
	
	public static String getTempUserName(){
		return tempUserName = usernameBox.getText();
	}
	
	public static String getTempPass(){
		return tempPass = pwdBox.getText();
	}
	
	@UiHandler("loginBtn")
	void onLoginClicked(ClickEvent event) {
		 tempUserName = usernameBox.getText();
		 tempPass = pwdBox.getText();
		 if(presenter != null){
			 presenter.onLoginButtonClicked();
		 }
	}
	
	@UiHandler("registerLink")
	void onRegisterClicked(ClickEvent event) {
		 if(presenter != null){
			 presenter.onRegisterLinkClicked();
		 }	
	}
}
