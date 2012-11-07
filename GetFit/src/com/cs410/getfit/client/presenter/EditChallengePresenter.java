package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.client.AuthResponse;
import com.cs410.getfit.client.HistoryValues;
import com.cs410.getfit.client.event.GoToChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.json.ParticipantsJsonFormatter;
import com.cs410.getfit.client.view.CreateAndEditChallengeView;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.IncomingChallengeJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingParticipantJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.HasWidgets;

public class EditChallengePresenter implements Presenter, CreateAndEditChallengeView.Presenter{

	private final HandlerManager eventBus;
	private final CreateAndEditChallengeView view;

	private String challengeUri;
	

	public EditChallengePresenter(HandlerManager eventBus, CreateAndEditChallengeView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	// should not be called without challengeId parameter, redirect to error page
	public void go(HasWidgets container) {
		eventBus.fireEvent(new GoToErrorEvent());
	}

	public void go(HasWidgets container, String uri) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		//  hide view until user is verified as admin
		view.asWidget().setVisible(false);
		container.add(view.asWidget());
		// parse out edit string in challengeUri
		challengeUri = uri.replace(HistoryValues.EDIT.toString(), "");
		// only display edit page if user is admin
		checkUserIsAdmin();
	}

	private void checkUserIsAdmin() {
		// GET request on challenge uri to get rel to participants
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(challengeUri); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
						if (models.size() > 0) {
							OutgoingChallengeJsonModel model = models.get(0);
							List<ResourceLink> links = model.getLinks();
							for (ResourceLink link : links) {
								if (link.getType().equals(LinkTypes.PARTICIPANTS.toString())) {
									// check on participants
									checkParticipants(link.getRel() + link.getUri());
								}
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

	private void checkParticipants(String participantsUri) {
		// GET request on challenge uri to get rel to participants
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(participantsUri); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingParticipantJsonModel> models = ParticipantsJsonFormatter.parseParticipantsJsonInfo(response.getText());
						if (models.size() > 0) {
							Boolean admin = false;
							long currentUser = AuthResponse.getInstance().getGuid();
							for (OutgoingParticipantJsonModel participantModel : models) {
								if (participantModel.getInfo().getUserId() == currentUser) {
									// check is user is admin - if true allow user to view page
									if (participantModel.getInfo().isAdmin()) {
										admin = true;
										view.asWidget().setVisible(true);
										populateFields();
									}		
									break;
								}
							}
							// user is not admin - display an error message
							if (!admin) {
								eventBus.fireEvent(new GoToErrorEvent());
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
	
	// fill in the fields with current challenge details
	private void populateFields() {
		if (challengeUri == "") {
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
								ChallengeInfoJsonModel infoModel = model.getInfo();
								// set current details
								view.getChallengeNameBox().setText(infoModel.getTitle());
								view.getLocationBox().setText(infoModel.getLocation());
								view.getDescriptionBox().setText(infoModel.getDescription());
								view.getPrivacyPrivateRadioButton().setValue(infoModel.getIsprivate());
								view.getPrivacyPublicRadioButton().setValue(!infoModel.getIsprivate());
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

	@Override
	public void onSaveChallengeButtonClicked() {
		Boolean fieldsPass = CreateAndEditChallengeHelper.verifyFields(view);

		if (fieldsPass) {
			RequestBuilder builder = HTTPRequestBuilder.getPutRequest(challengeUri); 

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
								eventBus.fireEvent(new GoToChallengeEvent(challengeUri));
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

}
