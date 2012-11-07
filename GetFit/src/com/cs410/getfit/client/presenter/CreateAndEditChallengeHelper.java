package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.client.AuthResponse;
import com.cs410.getfit.client.event.GoToChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.view.CreateAndEditChallengeView;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.IncomingChallengeJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/*
 * Helper class for methods common to both CreateChallengePresenter and EditChallengePresenter
 * - method to verify user entered fields (fields are common to both views)
 * - method to send HTTPrequest and handle response
 */
public class CreateAndEditChallengeHelper {

	// max characters that can be stored in DB varchar type column
	private final static int VARCHARMAX = 255;

	// error strings and variables
	private final static String mandatoryVerificationError = "Please fill in all required fields.";
	private final static String descLengthVerificationError = "Description cannot exceed 255 characters.";
	private final static String nameLengthVerificationError = "Challenge name cannot exceed 255 characters.";
	private final static int popupXPosition = 400;
	private final static int popupYPosition = 55;
	private static PopupPanel errorPopup;

	public static boolean verifyFields(CreateAndEditChallengeView view) {
		// revert label colours
		resetLabelColours(view);

		// input checks
		Boolean mandatoryVerification = checkMandatoryFields(view);
		Boolean descriptionVerification = checkDescriptionLength(view);
		Boolean nameVerification = checkNameLength(view);
		if (!mandatoryVerification) {
			displayPopup(view, mandatoryVerificationError);
			return false;
		}
		else if (!descriptionVerification) {
			displayPopup(view, descLengthVerificationError);
			return false;
		}
		else if (!nameVerification) {
			displayPopup(view, nameLengthVerificationError);
			return false;
		}
		return true;
	}

	// check user's form input - return true if all mandatory fields are entered and no invalid chars/length exist
	private static Boolean checkMandatoryFields(CreateAndEditChallengeView view) {
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

	// check user's description input - return true if length < 256
	private static Boolean checkDescriptionLength(CreateAndEditChallengeView view) {
		if (view.getDescription().trim().length() > VARCHARMAX) {
			view.getDescriptionLabel().addStyleName("error-label");
			return false;
		}
		return true;
	}
	
	// check user's description input - return true if length < 256
	private static Boolean checkNameLength(CreateAndEditChallengeView view) {
		if (view.getChallengeName().trim().length() > VARCHARMAX) {
			view.getChallengeNameLabel().addStyleName("error-label");
			return false;
		}
		return true;
	}

	// reset any label colours that were changed due to previous errors
	private static void resetLabelColours(CreateAndEditChallengeView view) {
		view.getChallengeNameLabel().removeStyleName("error-label");
		view.getDescriptionLabel().removeStyleName("error-label");
	}

	// displays a popup for errors
	private static void displayPopup(CreateAndEditChallengeView view, String msg) {
		errorPopup = new PopupPanel(true);
		errorPopup.setWidget(new Label(msg));
		errorPopup.setStyleName("error-popup");
		errorPopup.setPopupPosition(popupXPosition, popupYPosition);
		errorPopup.setVisible(true);
		errorPopup.show();
	}

	public static void sendRequest(CreateAndEditChallengeView view,
			RequestBuilder builder, final HandlerManager eventBus) {

		// gather challenge info from view
		ChallengeInfoJsonModel info = new ChallengeInfoJsonModel();
		info.setTitle(view.getChallengeName().trim());
		info.setIsprivate(view.getIsPrivate());
		info.setLocation(view.getLocation());
		info.setDescription(view.getDescription().trim());

		IncomingChallengeJsonModel model = new IncomingChallengeJsonModel();
		model.setChallengeInfoJsonModel(info);
		model.setAdminId(AuthResponse.getInstance().getGuid());

		List<IncomingChallengeJsonModel> models = new ArrayList<IncomingChallengeJsonModel>();
		models.add(model);

		// get formatted challenge json
		String requestJson = ChallengesJsonFormatter.formatChallengeJsonInfo(models);

		try {
			builder.sendRequest(requestJson, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
						String challengeUri = null;
						if (models.size() > 0) {
							OutgoingChallengeJsonModel model = models.get(0);
							List<ResourceLink> links = model.getLinks();
							for (ResourceLink link : links) {
								if (link.getType().equals(LinkTypes.CHALLENGE.toString())) {
									challengeUri = link.getUri();
									break;
								}
							}
							eventBus.fireEvent(new GoToChallengeEvent(challengeUri));
						}
						else {
							eventBus.fireEvent(new GoToErrorEvent());
						}
					} else {
						eventBus.fireEvent(new GoToErrorEvent(response.getStatusCode()));
					}
				}
				@Override
				public void onError(Request request, Throwable exception) {
					eventBus.fireEvent(new GoToErrorEvent());
				}
			});
		} catch (RequestException e) {
			eventBus.fireEvent(new GoToErrorEvent());
		}
	}
}
