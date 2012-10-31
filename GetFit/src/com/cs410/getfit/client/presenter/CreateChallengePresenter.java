package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.view.CreateChallengeView;
import com.cs410.getfit.server.challenges.json.ChallengeInfoJsonModel;
import com.cs410.getfit.server.challenges.json.IncomingChallengeJsonModel;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

public class CreateChallengePresenter implements Presenter, CreateChallengeView.Presenter{

	// max characters that can be stored in DB varchar type column
	private final int VARCHARMAX = 255;
	
	// error strings and variables
	private final String mandatoryVerificationError = "Please fill in all required fields.";
	private final String descLengthVerificationError = "Description cannot exceed 255 characters.";
	private final int popupXPosition = 400;
	private final int popupYPosition = 55;
	private PopupPanel errorPopup;
	
	private final HandlerManager eventBus;
	private final CreateChallengeView view;

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
	public void onCreateChallengeButtonClicked() {
		// revert label colours
		resetLabelColours();
		
		// input checks
		Boolean mandatoryVerification = checkMandatoryFields();
		Boolean descriptionVerification = checkDescriptionLength();
		if (!mandatoryVerification) {
			displayPopup(mandatoryVerificationError);
		}
		else if (!descriptionVerification) {
			displayPopup(descLengthVerificationError);
		}
		else {
			RequestBuilder builder = HTTPRequestBuilder.getPostRequest("/challenges"); 

			// gather challenge info from view
			ChallengeInfoJsonModel info = new ChallengeInfoJsonModel();
			info.setTitle(view.getChallengeName());
			info.setIsprivate(view.isPrivate());
			info.setLocation(view.getLocation());
			// TODO: description for info

			IncomingChallengeJsonModel model = new IncomingChallengeJsonModel();
			// TODO: retrive current user's ID
			// temp hard-coded value
			model.setChallengeJsonModel(info);
			model.setAdminId((long) 1);
			
			List<IncomingChallengeJsonModel> models = new ArrayList<IncomingChallengeJsonModel>();
			models.add(model);
			
			// get formatted challenge json
			String requestJson = ChallengesJsonFormatter.formatChallengeJsonInfo(models);

			try {
				builder.sendRequest(requestJson, new RequestCallback() {
					@Override
					public void onResponseReceived(Request request, Response response) {
						if (response.getStatusCode() == 200) {
							ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
							Window.alert("Challenge created");
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

	// check user's form input - return true if all mandatory fields are entered and no invalid chars/length exist
	private Boolean checkMandatoryFields() {
		Boolean pass = true;
		// check if challenge name is entered
		if (view.getChallengeName().trim().length() == 0) {
			view.getChallengeNameLabel().addStyleName("error-label");
			pass = false;
		}
		// check if description is entered
		if (view.getDescription().trim().length() == 0) {
			view.getDescriptionLabel().addStyleName("error-label");
			pass = false;
		}
		return pass;
	}
	
	// check user's description input - return true if length <
	private Boolean checkDescriptionLength() {
		if (view.getDescription().trim().length() > VARCHARMAX) {
			view.getDescriptionLabel().addStyleName("error-label");
			return false;
		}
		return true;
	}
	
	// reset any label colours that were changed due to previous errors
	private void resetLabelColours() {
		view.getChallengeNameLabel().removeStyleName("error-label");
		view.getDescriptionLabel().removeStyleName("error-label");
	}
	
	// displays a popup for errors
	private void displayPopup(String msg) {
		errorPopup = new PopupPanel(true);
		errorPopup.setWidget(new Label(msg));
		errorPopup.setStyleName("error-popup");
		errorPopup.setPopupPosition(popupXPosition, popupYPosition);
		errorPopup.setVisible(true);
		errorPopup.show();
	}
}
