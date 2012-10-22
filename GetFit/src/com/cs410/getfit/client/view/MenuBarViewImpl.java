package com.cs410.getfit.client.view;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class MenuBarViewImpl extends Composite implements MenuBarView {
	
	private Presenter presenter;
	
	@UiTemplate("MenuBar.ui.xml") 
	interface Binder extends UiBinder<Widget, MenuBarViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);
	
	public MenuBarViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
	}

	@Override
	public void setPresenter(Presenter presenter) {
		this.presenter = presenter;
	}
	

}
