package com.cs410.getfit.client.presenter;

import java.util.List;

import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.CompletedChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.json.ParticipantsJsonFormatter;
import com.cs410.getfit.client.view.ChallengeView;
import com.cs410.getfit.server.challenges.json.ChallengeInfoJsonModel;
import com.cs410.getfit.server.challenges.json.OutgoingChallengeJsonModel;
import com.cs410.getfit.server.challenges.json.OutgoingCompletedChallengeJsonModel;
import com.cs410.getfit.server.challenges.json.OutgoingParticipantJsonModel;
import com.cs410.getfit.server.json.LinkTypes;
import com.cs410.getfit.server.json.ResourceLink;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.DecoratorPanel;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChallengePresenter implements Presenter, ChallengeView.Presenter{

	private final HandlerManager eventBus;
	private final ChallengeView view;

	private final String errorMsg = "Oh no! The challenge you are looking for could not be found.";
	private final String privateChallengeMsg = "Permission denied: The challenge you are trying to view is set to private.";

	public ChallengePresenter(HandlerManager eventBus, ChallengeView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	// should not be called without challengeId parameter, displays an error message in view
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		// clear subpanels
		clearSubPanels();
		// error messgae
		view.getTitleLabel().setText(errorMsg);
	}

	public void go(HasWidgets container, String challengeId) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		// clear subpanels
		clearSubPanels();
		// fill view with challenge info
		populateMainPanel(challengeId);
	}

	/* scenarios: 
	 * challengeId was not set - display error message in view 
	 * challenge is private and user is not participant - display private challenge message
	 * user is a participant of challenge - display challenge 
	 */
	private void populateMainPanel(String challengeUri) {
		if (challengeUri == null) {
			view.getTitleLabel().setText(errorMsg);
		}
		else {
			// GET request on challenge uri
			RequestBuilder builder = HTTPRequestBuilder.getGetRequest(challengeUri); 
			try {
				builder.sendRequest(null, new RequestCallback() {
					@Override
					public void onResponseReceived(Request request, Response response) {
						if (response.getStatusCode() == 200) {
							List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
							if (models.size() > 0) {
								OutgoingChallengeJsonModel model = models.get(0);
								if (model.getInfo().getIsprivate()) {
									// challenge is private, verify if user is participant
									displayPrivateChallenge(model);
								}
								else {
									// challenge is public, display in view
									displayChallenge(model);
								}
							}
							else {
								view.getTitleLabel().setText(errorMsg);
							}
						}
						else {
							view.getTitleLabel().setText(errorMsg);
						}
					}

					@Override
					public void onError(Request request, Throwable exception) {
						// TODO error handling
					}
				});
			} catch (RequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// check participants
			// if participant, display info
			// else display private challenge view
		}
	}

	private void displayChallenge(OutgoingChallengeJsonModel model) {
		// challenge info
		ChallengeInfoJsonModel info = model.getInfo();
		view.getTitleLabel().setText("Challenge: " + info.getTitle());
		VerticalPanel infoPanel = view.getChallengeInfoPanel();
		infoPanel.addStyleName("text-panel");
		// only display location if set
		if (info.getLocation() != null) {
			Label locationLabel = new Label("Location: ");
			Label locationText = new Label(info.getLocation());
			locationLabel.addStyleName("details-label small-indent");
			locationText.addStyleName("details-text location small-indent");
			HorizontalPanel locationPanel = new HorizontalPanel();
			locationPanel.add(locationLabel);
			locationPanel.add(locationText);
			infoPanel.add(locationPanel);
		}
		// total participants
		Label participantsLabel = new Label("Total Participants: ");
		Label participantsText = new Label();
		displayTotalParticipants(model, participantsText);
		participantsLabel.addStyleName("details-label small-indent");
		participantsText.addStyleName("details-text small-indent");
		HorizontalPanel participantsPanel = new HorizontalPanel();
		participantsPanel.add(participantsLabel);
		participantsPanel.add(participantsText);
		infoPanel.add(participantsPanel);
		// description
		Label descriptionLabel = new Label("Description: ");
		Label descriptionText = new Label(info.getDescription());
		descriptionLabel.addStyleName("details-label small-indent");
		descriptionText.addStyleName("details-text description small-indent");
		infoPanel.add(descriptionLabel);
		infoPanel.add(descriptionText);
		// action button (join, complete(d))
		displayActionButtons(model, infoPanel);
		// news feed
		VerticalPanel newsFeedPanel = view.getNewsFeedPanel();
		Label newsFeedLabel = new Label("News Feed: ");
		newsFeedLabel.addStyleName("details-label");	
		newsFeedPanel.add(newsFeedLabel);
		ScrollPanel scroller = new ScrollPanel();
	    scroller.setSize("700px", "400px");
	    DecoratorPanel decPanel = new DecoratorPanel();
	    decPanel.setWidget(scroller);
	    newsFeedPanel.add(decPanel);
	    // TODO: populate news feed
	}

	private void clearSubPanels() {
		view.getChallengeInfoPanel().clear();
		view.getNewsFeedPanel().clear();
	}

	// helper method for http request to get number of participants and set text in view
	private void displayTotalParticipants(OutgoingChallengeJsonModel model, final Label label){
		List<ResourceLink> links = model.getLinks();
		for (ResourceLink link : links) {
			if (link.getType().equals(LinkTypes.PARTICIPANTS.toString())) {
				RequestBuilder builder = HTTPRequestBuilder.getGetRequest(link.getRel() + link.getUri()); 
				try {
					builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								List<OutgoingParticipantJsonModel> models = ParticipantsJsonFormatter.parseParticipantsJsonInfo(response.getText());
								if (models.size() > 0) {
									label.setText(Integer.toString(models.size()));
								}
							}
						}
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO: error handling
						}
					});
				} catch (RequestException e) {
					// TODO: error handling
				}
			}
		}
	}

	// helper method to get correct action button (join/complete(d)/edit)
	private void displayActionButtons(final OutgoingChallengeJsonModel model, final VerticalPanel panel){
		// TODO: replace temp currentUser value
		final long currentUser = new Long(1);
		List<ResourceLink> links = model.getLinks();
		for (ResourceLink link : links) {
			if (link.getType().equals(LinkTypes.PARTICIPANTS.toString())) {
				RequestBuilder builder = HTTPRequestBuilder.getGetRequest(link.getRel() + link.getUri()); 
				try {
					builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								List<OutgoingParticipantJsonModel> models = ParticipantsJsonFormatter.parseParticipantsJsonInfo(response.getText());
								if (models.size() > 0) {
									Boolean participating = false;
									for (OutgoingParticipantJsonModel participantModel : models) {
										if (participantModel.getInfo().getUserId() == currentUser) {
											participating = true;
											// user is participant, add complete button
											Button completeBtn = new Button("Mark Complete");
											completeBtn.setStyleName("btn btn-primary");
											completeBtn.addStyleName("complete-btn");
											panel.add(completeBtn);
											// if user has completed challenge already, disable button
											displayCompleteButton(model, completeBtn);
											// check is user is admin - if true, add edit button
											if (participantModel.getInfo().isAdmin()) {
												Button editBtn = new Button("Edit");
												editBtn.setStyleName("btn btn-primary");
												editBtn.addStyleName("edit-btn");
												panel.add(editBtn);
											}		
											break;
										}
									}
									// user is not a participant - display a join button
									if (!participating) {
										Button joinBtn = new Button("Join Challenge");
										joinBtn.setStyleName("btn btn-primary");
										joinBtn.addStyleName("join-btn");
										panel.add(joinBtn);
									}
								}
								else {
									// error handling: no models returned
								}
							}
							else {
								// TODO: error handling - non 200 response
							}
						}
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO: error handling
						}
					});
				} catch (RequestException e) {
					// TODO: error handling
				}
			}
		}
	}
	
	// helper method for http request to get number of participants and set text in view
	private void displayCompleteButton(OutgoingChallengeJsonModel model, final Button completeBtn){
		// TODO: replace temp currentUser value
		final long currentUser = new Long(1);
		List<ResourceLink> links = model.getLinks();
		for (ResourceLink link : links) {
			if (link.getType().equals(LinkTypes.COMPLETEDCHALLENGE.toString())) {
				RequestBuilder builder = HTTPRequestBuilder.getGetRequest(link.getRel() + link.getUri()); 
				try {
					builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								List<OutgoingCompletedChallengeJsonModel> models = CompletedChallengesJsonFormatter.parseCompletedChallengesJsonInfo(response.getText());
								if (models.size() > 0) {
									Boolean completed = false;
									for (OutgoingCompletedChallengeJsonModel model : models) {
										if (model.getInfo().getUserId() == currentUser) {
											completed = true;
											completeBtn.setEnabled(false);
											completeBtn.setText("Completed");
											completeBtn.removeStyleName("complete-btn");
											completeBtn.addStyleName("completed-btn");
										}
										break;
									}
									if (!completed) {
										completeBtn.setEnabled(true);
									}
								}
								else {
									// TODO: error handling - no models returned
								}
							}
							else {
								// TODO: error handling - non 200 response
							}
						}
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO: error handling
						}
					});
				} catch (RequestException e) {
					// TODO: error handling
				}
			}
		}
	}
	
	// challenge is private, check if user is a participant before displaying content
	private void displayPrivateChallenge(final OutgoingChallengeJsonModel model){
		// TODO: replace temp currentUser value
		final long currentUser = new Long(1);
		List<ResourceLink> links = model.getLinks();
		for (ResourceLink link : links) {
			if (link.getType().equals(LinkTypes.PARTICIPANTS.toString())) {
				RequestBuilder builder = HTTPRequestBuilder.getGetRequest(link.getRel() + link.getUri()); 
				try {
					builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								List<OutgoingParticipantJsonModel> models = ParticipantsJsonFormatter.parseParticipantsJsonInfo(response.getText());
								if (models.size() > 0) {
									Boolean participating = false;
									for (OutgoingParticipantJsonModel participantModel : models) {
										if (participantModel.getInfo().getUserId() == currentUser) {
											participating = true;
											displayChallenge(model);
											break;
										}
									}
									// user is not a participant - display a join button
									if (!participating) {
										view.getTitleLabel().setText(privateChallengeMsg);
									}
								}
								else {
									// TODO: error handling - no models returned
								}
							}
							else {
								// TODO: error handling - non 200 response
							}
						}
						@Override
						public void onError(Request request, Throwable exception) {
							// TODO: error handling
						}
					});
				} catch (RequestException e) {
					// TODO: error handling
				}
			}
		}
	}

}
