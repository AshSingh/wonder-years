package com.cs410.getfit.client.view;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class CreateChallengeViewImpl extends Composite implements CreateChallengeView {
	@UiField static DateBox startDateBox;
	@UiField static DateBox endDateBox;
	@UiField static Button addActivityBtn;
	@UiField static ListBox activitiesList;
	@UiField static VerticalPanel activitiesPanel;

	private final String dateFormat = "MM-dd-yyyy";
	private Presenter presenter;
	private MenuBarView menuBar;

	@UiTemplate("CreateChallenge.ui.xml") 
	interface Binder extends UiBinder<Widget, CreateChallengeViewImpl> {}
	private static final Binder uiBinder = GWT.create(Binder.class);

	public CreateChallengeViewImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		activitiesList.addItem("ActivityA");
		activitiesList.addItem("ActivityB");
		activitiesList.addItem("ActivityC");
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
	public String getDateFormat() {
		return dateFormat;
	}
	
	@Override
	public VerticalPanel getActivitiesPanel() {
		return activitiesPanel;
	}
	
	@UiHandler("startDateBox")
	void onStartValueChange(ValueChangeEvent<Date> event) {
		Date date = event.getValue();
		String dateString = DateTimeFormat.getFormat(dateFormat).format(date);
		startDateBox.getTextBox().setText(dateString);
	}

	@UiHandler("endDateBox")
	void onEndValueChange(ValueChangeEvent<Date> event) {
		Date date = event.getValue();
		String dateString = DateTimeFormat.getFormat(dateFormat).format(date);
		endDateBox.getTextBox().setText(dateString);
	}

	@UiHandler("addActivityBtn")
	void onAddActivityClicked(ClickEvent event) {
		if (presenter != null) {
			int index = activitiesList.getSelectedIndex();
			presenter.onAddActivityButtonClicked(activitiesList.getValue(index));
		}
	}
}
