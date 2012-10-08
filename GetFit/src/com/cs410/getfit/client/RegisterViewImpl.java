package com.cs410.getfit.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class RegisterViewImpl extends Composite implements RegisterView{
	
	private Presenter presenter;
	
	@UiTemplate("Register.ui.xml") 
	interface Binder extends UiBinder<Widget, RegisterViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public RegisterViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}
	
	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	
	@UiHandler("registerBtn")
	void onRegisteredClicked(ClickEvent event) {
		 if(presenter != null){
			 presenter.onRegisterButtonClicked();
		 }
	}

	@UiHandler("cancelBtn")
	void onCancelClicked(ClickEvent event) {
		 if(presenter != null){
			 presenter.onCancelRegisterButtonClicked();
		 }
	}
}
