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

public class LoginView extends Composite implements LoginViewInterface{
	@UiField static TextBox emailBox;
	@UiField static PasswordTextBox pwdBox;
	
	private Presenter presenter;
	
	private static String tempUserName;
	private static String tempPass;
	
	@UiTemplate("Login.ui.xml") 
	interface Binder extends UiBinder<Widget, LoginView> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public LoginView() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
		
	}
	
	public static String getTempUserName(){
		return tempUserName = emailBox.getText();
	}
	
	public static String getTempPass(){
		return tempPass = pwdBox.getText();
	}
	
	@UiHandler("loginBtn")
	void onLoginClicked(ClickEvent event) {
		 tempUserName = emailBox.getText();
		 tempPass = pwdBox.getText();
		 if(presenter != null){
			 presenter.onLoginButtonClicked();
		 }
	}
}
