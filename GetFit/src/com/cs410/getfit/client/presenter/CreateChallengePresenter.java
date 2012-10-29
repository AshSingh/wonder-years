package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.view.CreateChallengeView;
import com.cs410.getfit.server.challenges.json.ChallengeInfoJsonModel;
import com.google.gwt.core.client.GWT;
import com.google.gwt.dev.json.JsonObject;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.datepicker.client.DateBox;

import org.json.JSONException;

import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;

public class CreateChallengePresenter implements Presenter, CreateChallengeView.Presenter{

	private final HandlerManager eventBus;
	private final CreateChallengeView view;

	private enum FrequencyOptions {
		ONCE,
		DAILY,
		WEEKLY,
		MONTHLY;
	}

	public CreateChallengePresenter(HandlerManager eventBus, CreateChallengeView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
	}

	@Override
	public void onAddActivityButtonClicked(String activity) {
		// create an encompassing vertical panel 
		VerticalPanel activitySubPanel = new VerticalPanel();
		// create activity name label
		Label activityLabel = new Label(activity);
		activityLabel.addStyleName("activityAdded-label");
		// create horizontal panel with optional fields
		HorizontalPanel optionFields = new HorizontalPanel();
		// time
		Label timeLabel = new Label("Time:");
		timeLabel.addStyleName("activityField-label");
		TextBox timeBox = new TextBox();
		timeBox.addStyleName("span2");
		// duration
		Label durationLabel = new Label("Duration:");
		durationLabel.addStyleName("activityField-label");	
		TextBox durBox = new TextBox();
		durBox.addStyleName("span2");				
		// quantity
		Label quantityLabel = new Label("Quantity:");
		quantityLabel.addStyleName("activityField-label");	
		TextBox qtyBox = new TextBox();
		qtyBox.addStyleName("span2");	
		// add fields to horizontal panel
		optionFields.add(timeLabel);
		optionFields.add(timeBox);		
		optionFields.add(durationLabel);
		optionFields.add(durBox);	
		optionFields.add(quantityLabel);
		optionFields.add(qtyBox);
		// freq field
		HorizontalPanel freqFields = new HorizontalPanel();		
		// frequency
		Label freqLabel = new Label("Date:");
		freqLabel.addStyleName("activityField-label");	
		final DateBox freqBox = new DateBox();
		// add value change handler to format selected date in text box
		freqBox.addValueChangeHandler(new ValueChangeHandler<Date>(){
			@Override
			public void onValueChange(ValueChangeEvent<Date> event) {
				Date date = event.getValue();
				String dateString = DateTimeFormat.getFormat(view.getDateFormat()).format(date);
				freqBox.getTextBox().setText(dateString);
			}
		});
		freqBox.addStyleName("span2");	
		Label freqRepeatLabel = new Label("Frequency:");
		freqRepeatLabel.addStyleName("activityField-label");	
		ListBox freqList = new ListBox();
		freqList.addStyleName("freqList");
		for (FrequencyOptions freqOpt : FrequencyOptions.values()) {
			freqList.addItem(freqOpt.toString().toLowerCase());
		}
		// add frequency widgets to horizontal panel
		freqFields.add(freqLabel);
		freqFields.add(freqBox);
		freqFields.add(freqRepeatLabel);
		freqFields.add(freqList);		
		// add fields and label to vertical panel
		activitySubPanel.add(activityLabel);
		activitySubPanel.add(optionFields);
		activitySubPanel.add(freqFields);
		// add to view
		view.getActivitiesPanel().add(activitySubPanel);
	}

	@Override
	public void onCreateChallengeButtonClicked() {
		RequestBuilder builder = HTTPRequestBuilder.getPostRequest("/challenges"); 

		// gather challenge info from view
		ChallengeInfoJsonModel info = new ChallengeInfoJsonModel();
		info.setTitle(view.getChallengeName());
		info.setStartdate(view.getStartDate());
		info.setEnddate(view.getEndDate());
		info.setIsprivate(view.isPrivate());
		info.setLocation(view.getLocation());

		List<ChallengeInfoJsonModel> models = new ArrayList<ChallengeInfoJsonModel>();
		models.add(info);

		// get formatted challenge json
		// TODO: retrive current user's ID
		// temp hard-coded value
		int admin = 1;
		String requestJson = ChallengesJsonFormatter.formatChallengeJsonInfo(models, admin);

		try {
			builder.sendRequest(requestJson, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 201) {
						Window.alert("Response 201 - challenge created");
					} else {
						Window.alert("Response " + response.getStatusCode());
					}
				}

				@Override
				public void onError(Request request, Throwable exception) {
					// TODO Auto-generated method stub
					
				}
			});
		} catch (RequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
