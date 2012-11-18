package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.view.CreateAndEditChallengeView;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;

/**
 * Helper class for field verification methods common to both 
 * CreateChallengePresenter and EditChallengePresenter
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

	/**
	 * Verifies the form input when user tries to save challenge
	 * 
	 * @param view - the view containing the create/edit challenge form
	 */
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

	/**
	 * Helper method - checks that the mandatory fields have input 
	 * 
	 * @param view - the view containing the create/edit challenge form
	 * @return true - if all mandatory fields entered 
	 * 		   false - if any mandatory fields are missing
	 */
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

	/**
	 * Helper method - checks that the description length does not exceed length restriction
	 * 
	 * @param view - the view containing the create/edit challenge form
	 * @return true - if description length does not exceed max 
	 * 		   false - if length exceeds max 
	 */
	private static Boolean checkDescriptionLength(CreateAndEditChallengeView view) {
		if (view.getDescription().trim().length() > VARCHARMAX) {
			view.getDescriptionLabel().addStyleName("error-label");
			return false;
		}
		return true;
	}

	/**
	 * Helper method - checks that the name length does not exceed length restriction
	 * 
	 * @param view - the view containing the create/edit challenge form
	 * @return true - if name length does not exceed max 
	 * 		   false - if length exceeds max 
	 */
	private static Boolean checkNameLength(CreateAndEditChallengeView view) {
		if (view.getChallengeName().trim().length() > VARCHARMAX) {
			view.getChallengeNameLabel().addStyleName("error-label");
			return false;
		}
		return true;
	}

	/**
	 * Helper method - resets any label colours that were changed previously to indicate error
	 * 
	 * @param view - the view containing the create/edit challenge form
	 */
	private static void resetLabelColours(CreateAndEditChallengeView view) {
		view.getChallengeNameLabel().removeStyleName("error-label");
		view.getDescriptionLabel().removeStyleName("error-label");
	}

	/**
	 * Helper method - displays a popup for any errors
	 * 
	 * @param view - the view containing the create/edit challenge form
	 * @param msg - the msg to display in the popup
	 */
	private static void displayPopup(CreateAndEditChallengeView view, String msg) {
		errorPopup = new PopupPanel(true);
		errorPopup.setWidget(new Label(msg));
		errorPopup.setStyleName("error-popup");
		errorPopup.setPopupPosition(popupXPosition, popupYPosition);
		errorPopup.setVisible(true);
		errorPopup.show();
	}
}
