package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.core.client.JsArray;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.maps.client.base.LatLng;
import com.google.gwt.maps.client.services.Geocoder;
import com.google.gwt.maps.client.services.GeocoderRequest;
import com.google.gwt.maps.client.services.GeocoderRequestHandler;
import com.google.gwt.maps.client.services.GeocoderResult;
import com.google.gwt.maps.client.services.GeocoderStatus;
import com.google.gwt.regexp.shared.MatchResult;
import com.google.gwt.regexp.shared.RegExp;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class ChallengePresenter implements Presenter, ChallengeView.Presenter{

	private final HandlerManager eventBus;
	private final ChallengeView view;

	private final String privateChallengeMsg = "Permission denied: The challenge you are trying to view is set to private.";

	/**
	 * Constructor for presenter for challenge page
	 * 
	 * @param eventBus - manages changing views within the application
	 * @param view - the view to display
	 */
	public ChallengePresenter(HandlerManager eventBus, ChallengeView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	/**
	 * Standard method for displaying the page 
	 * Should not be called without a challengeId parameter, redirect to an error page
	 * 
	 * @param container - the root container of the app          
	 */
	@Override
	public void go(HasWidgets container) {
		eventBus.fireEvent(new GoToErrorEvent());
	}

	/**
	 * Standard method for displaying the page 
	 * Displays the challenge page 
	 * 
	 * @param container - the root container of the app
	 * @param challengeUri - the uri that provides the info for the specific challenge          
	 */	
	public void go(HasWidgets container, String challengeUri) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		// clear subpanel and title
		view.getChallengeInfoPanel().clear();
		view.getTitleLabel().setText("");
		// fill view with challenge info
		populateMainPanel(challengeUri);
	}

	/**
	 * Populates the challenge page the specific challenge's info
	 * 
	 * Scenarios: 
	 * challengeId was not set - display error message in view 
	 * challenge is private and user is not participant - display private challenge message
	 * user is a participant of challenge - display challenge 
	 * 
	 * @param challengeUri - the uri that provides the info for the specific challenge
	 */
	private void populateMainPanel(String challengeUri) {
		if (challengeUri == null) {
			eventBus.fireEvent(new GoToErrorEvent());
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

	/**
	 * Helper method - Displays challenge contents once it has been verified that challenge
	 * is public or user is a participant of a private challenge
	 * 
	 * @param model - the model of the challenge to be displayed
	 */
	private void displayChallenge(final OutgoingChallengeJsonModel model) {
		// clear subpanel
		view.getChallengeInfoPanel().clear();
		// challenge info
		ChallengeInfoJsonModel info = model.getInfo();
		view.getTitleLabel().setText("Challenge: " + info.getTitle());
		final VerticalPanel infoPanel = view.getChallengeInfoPanel();
		infoPanel.addStyleName("text-panel");
		// only display location if set
		if (info.getLocation() != null) {
			// Regular expresion for latitude and longitude as stored in DATABASE
			RegExp regexp = RegExp.compile("\\((\\-?\\d+(\\.\\d+)?),\\s*(\\-?\\d+(\\.\\d+)?)\\)");
			MatchResult match = regexp.exec(info.getLocation());
			LatLng challengePoint = LatLng.newInstance(Double.parseDouble(match.getGroup(1)), Double.parseDouble(match.getGroup(3)));
			// Get the name of the city on the location of the challenge
			Geocoder geocoder = Geocoder.newInstance();
			GeocoderRequest request = GeocoderRequest.newInstance();
			request.setLocation(challengePoint);
			geocoder.geocode(request, new GeocoderRequestHandler() {

				@Override
				public void onCallback(JsArray<GeocoderResult> results,
						GeocoderStatus status) {
					// Get the full location
					String formattedAddress = results.get(0).getFormatted_Address();
					String cityLocation = (formattedAddress != "" && formattedAddress != null) ? formattedAddress : "Unknown";
					Label locationLabel = new Label("Location: ");
					Label locationText = new Label(cityLocation);
					locationLabel.addStyleName("details-label");
					locationText.addStyleName("details-text");
					HorizontalPanel locationPanel = new HorizontalPanel();
					locationPanel.add(locationLabel);
					locationPanel.add(locationText);
					infoPanel.add(locationPanel);
					// action button (join, complete(d), edit)
					displayActionButtons(model, infoPanel);
				}
				
			});
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
		
	}

	/**
	 * Helper method - display the number of participants
	 * 
	 * @param model - the model of the challenge to be displayed
	 * @param label - the UI label for number of participants
	 */
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
								label.setText(Integer.toString(models.size()));
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

	/**
	 * Displays the appropriate buttons on the challenge page depending on 
	 * user's relation to the challenge
	 * 
	 * Scenarios: 
	 * user is a nonparticipant - display join button
	 * user is admin - display an edit button
	 * user is participant - display complete button, display leave challenge button
	 * 
	 * @param model - the model of the challenge to be displayed
	 * @param panel - the UI panel to display the buttons in
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
								if (models.size() >= 0) {
									Boolean participating = false;
									long currentUser = Long.parseLong(Cookies.getCookie("guid"));
									for (OutgoingParticipantJsonModel participantModel : models) {
										if (participantModel.getInfo().getUserId() == currentUser) {
											participating = true;
											// user is participant, add leave button
											Button leaveBtn = new Button("Leave Challenge");
											leaveBtn.setStyleName("btn btn-primary");
											leaveBtn.addStyleName("leave-btn");
											panel.add(leaveBtn);
											addLeaveBtnFunctionality(leaveBtn, participantModel, model);
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

	/**
	 * Helper method - displays the correct version of the complete button 
	 * 
	 * Scenarios: 
	 * user is participant and hasn't completed challenge -	display mark complete button
	 * user is participant and completed challenge - display disabled complete button with "Completed" text
	 * 
	 * @param model - the model of the challenge to be displayed
	 * @param completeBtn - the UI button for marking a challenge complete
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
								if (models.size() >= 0) {
									Boolean completed = false;
									long currentUser = Long.parseLong(Cookies.getCookie("guid"));
									for (OutgoingCompletedChallengeJsonModel model : models) {
										if (model.getInfo().getUserId() == currentUser) {
											completed = true;
											completeBtn.setEnabled(false);
											completeBtn.setText("Completed");
											completeBtn.removeStyleName("btn-primary");
											completeBtn.addStyleName("btn-success");
											break;
										}
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

	/**
	 * Security check for when challenge's setting is private
	 * 
	 * Scenarios:
	 * user is not a participant - display an error message and do not display the challenge info
	 * user is a participant - display challenge as usual
	 * 
	 * @param model - the model of the challenge to be displayed
	 */
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
								if (models.size() >= 0) {
									Boolean participating = false;
									long currentUser = Long.parseLong(Cookies.getCookie("guid"));
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

	/**
	 * Adds functionality to "Mark Complete" button
	 * When clicked, should send a http post to create a new completed challenge instance 
	 * 
	 * @param completeBtn - UI button to add functionality to
	 * @param model - the model of the challenge to be displayed 
	 */
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
						info.setUserId(Long.parseLong(Cookies.getCookie("guid")));

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

	/**
	 * Adds functionality to "Join" button
	 * When clicked, should send a http post to create a new participant instance 
	 * 
	 * @param joinBtn - UI button to add functionality to
	 * @param model - the model of the challenge to be displayed 
	 */
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
						info.setUserId(Long.parseLong(Cookies.getCookie("guid")));
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

	/**
	 * Adds functionality to "Leave" button
	 * When clicked, should send a http delete to delete the participant instance 
	 * 
	 * @param leaveBtn - UI button to add functionality to
	 * @param pModel - the model of the participant
	 * @param chModel - the model of the challenge to be displayed 
	 */
	private void addLeaveBtnFunctionality(final Button leaveBtn, final OutgoingParticipantJsonModel pModel, final OutgoingChallengeJsonModel chModel) {
		leaveBtn.addClickHandler(new ClickHandler(){
			@Override
			public void onClick(ClickEvent event) {
				List<ResourceLink> links = pModel.getLinks();
				for (ResourceLink link : links) {
					if (link.getType().equals(LinkTypes.PARTICIPANT.toString())) {
						RequestBuilder builder = HTTPRequestBuilder.getDeleteRequest(link.getUri()); 
						try {
							builder.sendRequest(null, new RequestCallback() {
								@Override
								public void onResponseReceived(Request request, Response response) {
									if (response.getStatusCode() == 200) {
										// update view
										displayChallenge(chModel);
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
	
	/**
	 * Adds functionality to "Edit" button
	 * When clicked, should redirect user to the edit challenge page
	 * 
	 * @param editBtn - UI button to add functionality to
	 * @param model - the model of the challenge to be displayed 
	 */
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
