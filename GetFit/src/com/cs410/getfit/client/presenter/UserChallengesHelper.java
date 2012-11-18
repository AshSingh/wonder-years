package com.cs410.getfit.client.presenter;

import java.util.List;

import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.CompletedChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.json.ParticipantsJsonFormatter;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.CompletedChallengeInfoJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingCompletedChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingParticipantJsonModel;
import com.cs410.getfit.shared.ParticipantInfoJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

/**
 * Helper class for displaying and organizing a list of challenges that the current user belongs to
 * Used by ChallengesPresenter and DashboardPresenter
 */
public class UserChallengesHelper {

	private final static String NO_USERCHALLENGES_MSG = "You have not joined any challenges yet.";
	private static Label currentLabel = new Label("Current Challenges:");
	private static Label completedLabel = new Label("Completed Challenges:");	

	private static HandlerManager eventBus;

	/**
	 * Displays a list of challenges that user is involved 
	 * List formed from hyperlinks that direct to specific challenge page
	 * 
	 * @param userChallengesUri - uri to retrieve all of user's challenges
	 * @param panel - UI panel to display challenges
	 * @param eventsBus - handles redirect of views (in case of error)
	 */
	public static void displayUserChallenges(String userChallengesUri, final VerticalPanel panel, final HandlerManager eventsBus){
		eventBus = eventsBus;
		// clean panel
		panel.clear();
		// create two subpanels: completed challenges, current challenges
		final VerticalPanel completedPanel = new VerticalPanel();
		final VerticalPanel currentPanel = new VerticalPanel();
		panel.add(completedPanel);
		panel.add(currentPanel);
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(userChallengesUri); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
						if (models.size() > 0) {
							for (OutgoingChallengeJsonModel model : models) {
								List<ResourceLink> links = model.getLinks();
								ChallengeInfoJsonModel infoModel = model.getInfo();
								String challengeUri = null;
								String participantUri = null;
								String completedUri = null;
								Widget challengeName;
								HorizontalPanel challengeHorzPanel = new HorizontalPanel();
								for (ResourceLink link : links) {
									if (link.getType().equals(LinkTypes.CHALLENGE.toString())) {
										challengeUri = link.getUri();
									}
									else if (link.getType().equals(LinkTypes.PARTICIPANTS.toString())){
										participantUri = link.getRel() + link.getUri();
									}
									else if (link.getType().equals(LinkTypes.COMPLETEDCHALLENGES.toString())) {
										completedUri = link.getRel() + link.getUri();
									}
								}
								// only create hyperlink if successfully got challenge uri
								if (challengeUri != null) {
									challengeName = new Hyperlink(infoModel.getTitle(), challengeUri);
								}
								// else just display challenge name as a label
								else {
									challengeName = new Label(infoModel.getTitle());
								}
								challengeHorzPanel.add(challengeName);
								// add to correct subpanel
								if (completedUri != null) {
									addToCorrectSubPanel(completedUri, challengeHorzPanel, completedPanel, currentPanel);
								}
								// add admin label if applicable
								if (participantUri != null) {
									addPossibleAdminTag(participantUri, challengeHorzPanel);
								}
							}
						}
						else {
							panel.add(new Label(NO_USERCHALLENGES_MSG));
						}
					}
					else {
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

	/**
	 * Displays user challenge under the appropriate UI heading
	 * 
	 * Scenarios:
	 * User has completed challenge - display in UI under the completed challenges list
	 * User has not completed challenge - display in UI under the current challenges list
	 * 
	 * @param completedUri - the uri of the completed challenges for a certain challenge
	 * @param challengeHorzPanel - the UI panel of the user's challenge
	 * @param completedPanel - the UI panel for the list of completed challenges
	 * @param currentPanel - the UI panel for the list of current challenges
	 */
	private static void addToCorrectSubPanel(String completedUri, final HorizontalPanel challengeHorzPanel, final VerticalPanel completedPanel, final VerticalPanel currentPanel) {
		final long currentUser = Long.parseLong(Cookies.getCookie("guid"));
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(completedUri); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingCompletedChallengeJsonModel> models = CompletedChallengesJsonFormatter.parseCompletedChallengesJsonInfo(response.getText());
						if (models.size() > 0) {
							Boolean completed = false;
							for (OutgoingCompletedChallengeJsonModel model : models) {
								CompletedChallengeInfoJsonModel infoModel = model.getInfo();
								if (infoModel.getUserId() == currentUser) {
									completed = true;
									// check if panel has any widgets
									// add title widget if panel has no widgets
									if (completedPanel.getWidgetCount() == 0) {
										completedLabel.addStyleName("challenges-heading");
										completedPanel.add(completedLabel);
									}
									completedPanel.add(challengeHorzPanel);
									break;
								}
							}
							// add to current challenges panel if not complete
							if (!completed) {
								// check if panel has any widgets
								// add title widget if panel has no widgets
								if (currentPanel.getWidgetCount() == 0) {
									currentLabel.addStyleName("challenges-heading");
									currentPanel.add(currentLabel);
								}
								currentPanel.add(challengeHorzPanel);
							}
						}
						else {
							// default - add to current panel
							// check if panel has any widgets
							// add title widget if panel has no widgets
							if (currentPanel.getWidgetCount() == 0) {
								currentLabel.addStyleName("challenges-heading");
								currentPanel.add(currentLabel);
							}
							currentPanel.add(challengeHorzPanel);
						}
					}
					else {
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

	/**
	 * Displays an indicator label if user is an admin of a challenge 
	 * 
	 * @param participantsUri - uri for participants of a specific challenge
	 * @param challengeHorzPanel - the UI panel of the user's challenge
	 */
	private static void addPossibleAdminTag(String participantsUri, final HorizontalPanel challengeHorzPanel) {
		final long currentUser = Long.parseLong(Cookies.getCookie("guid"));
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(participantsUri); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingParticipantJsonModel> models = ParticipantsJsonFormatter.parseParticipantsJsonInfo(response.getText());
						if (models.size() > 0) {
							for (OutgoingParticipantJsonModel model : models) {
								ParticipantInfoJsonModel infoModel = model.getInfo();
								if (infoModel.getUserId() == currentUser && infoModel.isAdmin()) {
									Label adminLabel = new Label("*admin");	
									adminLabel.addStyleName("admin-label");
									challengeHorzPanel.add(adminLabel);
									break;
								}
							}
						}
					}
					else {
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
