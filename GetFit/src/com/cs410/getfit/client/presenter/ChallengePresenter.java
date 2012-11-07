package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.client.AuthResponse;
import com.cs410.getfit.client.event.GoToEditChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.CompletedChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.json.ParticipantsJsonFormatter;
import com.cs410.getfit.client.view.ChallengeView;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.CompletedChallengeInfoJsonModel;
import com.cs410.getfit.shared.IncomingCompletedChallengeJsonModel;
import com.cs410.getfit.shared.IncomingParticipantJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingCompletedChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingParticipantJsonModel;
import com.cs410.getfit.shared.ParticipantInfoJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
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
	// should not be called without challengeId parameter, redirect to error page
	public void go(HasWidgets container) {
		eventBus.fireEvent(new GoToErrorEvent());
	}

	public void go(HasWidgets container, String challengeUri) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		// clear subpanel
		view.getChallengeInfoPanel().clear();
		// fill view with challenge info
		populateMainPanel(challengeUri);
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
								eventBus.fireEvent(new GoToErrorEvent());
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

	private void displayChallenge(OutgoingChallengeJsonModel model) {
		// clear subpanel
		view.getChallengeInfoPanel().clear();
		// challenge info
		ChallengeInfoJsonModel info = model.getInfo();
		view.getTitleLabel().setText("Challenge: " + info.getTitle());
		VerticalPanel infoPanel = view.getChallengeInfoPanel();
		infoPanel.addStyleName("text-panel");
		// only display location if set
		if (info.getLocation() != null) {
			Label locationLabel = new Label("Location: ");
			Label locationText = new Label(info.getLocation());
			locationLabel.addStyleName("details-label");
			locationText.addStyleName("details-text");
			HorizontalPanel locationPanel = new HorizontalPanel();
			locationPanel.add(locationLabel);
			locationPanel.add(locationText);
			infoPanel.add(locationPanel);
		}
		// total participants
		Label participantsLabel = new Label("Total Participants: ");
		Label participantsText = new Label();
		displayTotalParticipants(model, participantsText);
		participantsLabel.addStyleName("details-label");
		participantsText.addStyleName("details-text");
		HorizontalPanel participantsPanel = new HorizontalPanel();
		participantsPanel.add(participantsLabel);
		participantsPanel.add(participantsText);
		infoPanel.add(participantsPanel);
		// description
		Label descriptionLabel = new Label("Description: ");
		Label descriptionText = new Label(info.getDescription());
		descriptionLabel.addStyleName("details-label");
		descriptionText.addStyleName("details-text");
		infoPanel.add(descriptionLabel);
		infoPanel.add(descriptionText);
		// action button (join, complete(d), edit)
		displayActionButtons(model, infoPanel);
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
							eventBus.fireEvent(new GoToErrorEvent());
						}
					});
				} catch (RequestException e) {
					eventBus.fireEvent(new GoToErrorEvent());
				}
			}
		}
	}

	/* scenarios: 
	 * user is a nonparticipant - display join button
	 * user is admin - display an edit button
	 * user is participant - display complete button
	 */
	private void displayActionButtons(final OutgoingChallengeJsonModel model, final VerticalPanel panel){
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
									long currentUser = AuthResponse.getInstance().getGuid();
									for (OutgoingParticipantJsonModel participantModel : models) {
										if (participantModel.getInfo().getUserId() == currentUser) {
											participating = true;
											// user is participant, add complete button
											Button completeBtn = new Button("Mark Complete");
											completeBtn.setStyleName("btn btn-primary");
											completeBtn.addStyleName("complete-btn");
											// hide button until check done if challenge has already been completed
											completeBtn.setVisible(false);
											panel.add(completeBtn);
											addCompleteBtnFunctionality(completeBtn, model);
											// displays appropriate type of complete button for user
											displayCompleteButton(model, completeBtn);
											// check is user is admin - if true, add edit button
											if (participantModel.getInfo().isAdmin()) {
												Button editBtn = new Button("Edit");
												editBtn.setStyleName("btn btn-primary");
												editBtn.addStyleName("edit-btn");
												panel.add(editBtn);
												addEditBtnFunctionality(editBtn, model);
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
										addJoinBtnFunctionality(joinBtn, model);
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
	}

	/* scenarios: 
	 * user is participant and hasn't completed challenge -	display mark complete button
	 * user is participant and completed challenge - display disabled complete button with "Completed" text
	 */
	private void displayCompleteButton(OutgoingChallengeJsonModel model, final Button completeBtn){
		List<ResourceLink> links = model.getLinks();
		for (ResourceLink link : links) {
			if (link.getType().equals(LinkTypes.COMPLETEDCHALLENGES.toString())) {
				RequestBuilder builder = HTTPRequestBuilder.getGetRequest(link.getRel() + link.getUri()); 
				try {
					builder.sendRequest(null, new RequestCallback() {
						@Override
						public void onResponseReceived(Request request, Response response) {
							if (response.getStatusCode() == 200) {
								List<OutgoingCompletedChallengeJsonModel> models = CompletedChallengesJsonFormatter.parseCompletedChallengesJsonInfo(response.getText());
								if (models.size() > 0) {
									Boolean completed = false;
									long currentUser = AuthResponse.getInstance().getGuid();
									for (OutgoingCompletedChallengeJsonModel model : models) {
										if (model.getInfo().getUserId() == currentUser) {
											completed = true;
											completeBtn.setEnabled(false);
											completeBtn.setText("Completed");
											completeBtn.removeStyleName("btn-primary");
											completeBtn.addStyleName("btn-success");
										}
										break;
									}
									if (!completed) {
										completeBtn.setEnabled(true);
									}
									completeBtn.setVisible(true);
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
	}

	// challenge is private, check if user is a participant before displaying content
	private void displayPrivateChallenge(final OutgoingChallengeJsonModel model){
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
									long currentUser = AuthResponse.getInstance().getGuid();
									for (OutgoingParticipantJsonModel participantModel : models) {
										if (participantModel.getInfo().getUserId() == currentUser) {
											participating = true;
											displayChallenge(model);
											break;
										}
									}
									// user is not a participant - display an error message
									if (!participating) {
										view.getTitleLabel().setText(privateChallengeMsg);
									}
								}
								else {
									view.getTitleLabel().setText(privateChallengeMsg);
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
	}

	// "Mark Complete" button functionality
	private void addCompleteBtnFunctionality(final Button completeBtn, final OutgoingChallengeJsonModel model) {
		completeBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				List<ResourceLink> links = model.getLinks();
				for (ResourceLink link : links) {
					if (link.getType().equals(LinkTypes.COMPLETEDCHALLENGES.toString())) {
						RequestBuilder builder = HTTPRequestBuilder.getPostRequest(link.getRel() + link.getUri()); 
						
						// info for POST body
						CompletedChallengeInfoJsonModel info = new CompletedChallengeInfoJsonModel();
						info.setUserId(AuthResponse.getInstance().getGuid());
						
						IncomingCompletedChallengeJsonModel requestModel = new IncomingCompletedChallengeJsonModel();
						requestModel.setCompletedChallengeInfoJsonModel(info);
						
						List<IncomingCompletedChallengeJsonModel> models = new ArrayList<IncomingCompletedChallengeJsonModel>();
						models.add(requestModel);
						
						// get formatted json
						String requestJson = CompletedChallengesJsonFormatter.formatCompletedChallengeJsonInfo(models);
						
						try {
							builder.sendRequest(requestJson, new RequestCallback() {
								@Override
								public void onResponseReceived(Request request, Response response) {
									if (response.getStatusCode() == 200) {
										// update view
										displayChallenge(model);
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
			}
		});
	}

	// "Join Challenge" button functionality
	private void addJoinBtnFunctionality(final Button joinBtn, final OutgoingChallengeJsonModel model) {
		joinBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				List<ResourceLink> links = model.getLinks();
				for (ResourceLink link : links) {
					if (link.getType().equals(LinkTypes.PARTICIPANTS.toString())) {
						RequestBuilder builder = HTTPRequestBuilder.getPostRequest(link.getRel() + link.getUri()); 
						
						// info for POST body
						ParticipantInfoJsonModel info = new ParticipantInfoJsonModel();
						info.setUserId(AuthResponse.getInstance().getGuid());
						info.setAdmin(false);
						
						IncomingParticipantJsonModel requestModel = new IncomingParticipantJsonModel();
						requestModel.setParticipantInfoJsonModel(info);
						
						List<IncomingParticipantJsonModel> models = new ArrayList<IncomingParticipantJsonModel>();
						models.add(requestModel);
						
						// get formatted json
						String requestJson = ParticipantsJsonFormatter.formatParticipantsJsonInfo(models);
						
						try {
							builder.sendRequest(requestJson, new RequestCallback() {
								@Override
								public void onResponseReceived(Request request, Response response) {
									if (response.getStatusCode() == 200) {
										// update view
										displayChallenge(model);
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
			}
		});
	}
	
	// "Edit" button functionality
	private void addEditBtnFunctionality(final Button editBtn, final OutgoingChallengeJsonModel model) {
		editBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				List<ResourceLink> links = model.getLinks();
				Boolean linkFound = false;
				for (ResourceLink link : links) {
					if (link.getType().equals(LinkTypes.CHALLENGE.toString())) {
						linkFound = true;
						eventBus.fireEvent(new GoToEditChallengeEvent(link.getUri()));
					}
				}
				if (!linkFound) {
					eventBus.fireEvent(new GoToErrorEvent());
				}
			}
		});
	}
}
