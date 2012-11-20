package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.List;

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
import com.google.gwt.core.client.JsArray;
import com.google.gwt.dom.client.Style.Display;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.maps.client.services.Geocoder;
import com.google.gwt.maps.client.services.GeocoderRequest;
import com.google.gwt.maps.client.services.GeocoderRequestHandler;
import com.google.gwt.maps.client.services.GeocoderResult;
import com.google.gwt.maps.client.services.GeocoderStatus;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.RadioButton;

public class EditChallengePresenter implements Presenter, CreateAndEditChallengeView.Presenter{

	private final HandlerManager eventBus;
	private final CreateAndEditChallengeView view;

	private String challengeUri;
	
	/**
	 * Constructor for presenter for edit challenge page
	 * 
	 * @param eventBus - manages changing views within the application
	 * @param view - the view to display
	 */
	public EditChallengePresenter(HandlerManager eventBus, CreateAndEditChallengeView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	/**
	 * Standard method for displaying the page 
	 * Should not be called without a uri parameter, redirect to an error page
	 * 
	 * @param container - the root container of the app          
	 */
	@Override
	// should not be called without challengeId parameter, redirect to error page
	public void go(HasWidgets container) {
		eventBus.fireEvent(new GoToErrorEvent());
	}

	/**
	 * Standard method for displaying the page 
	 * Displays the challenge page 
	 * 
	 * @param container - the root container of the app
	 * @param uri - the uri that provides the info for the specific challenge          
	 */	
	public void go(HasWidgets container, String uri) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		// Create map
		view.createMap();
		//  hide view until user is verified as admin
		view.asWidget().setVisible(false);
		container.add(view.asWidget());
		// parse out edit string in challengeUri
		challengeUri = uri.replace(HistoryValues.EDIT.toString(), "");
		// only display edit page if user is admin
		checkUserIsAdmin();
	}

	/**
	 * Security check to see if user is actually admin of challenge
	 * Retrieves uri to list of participants
	 */
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

	/**
	 * Security check to see if user is actually admin of challenge
	 * 
	 * Scenarios:
	 * user is part of participants list and is admin - continue to display edit challenge page
	 * user is part of participants list and is not admin - redirect to an error page
	 * user is not part of participants lsit - redirect to an error page
	 * 
	 * @param participantsUri - uri to retrieve list of participants
	 */
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
							long currentUser = Long.parseLong(Cookies.getCookie("guid"));
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
	
	/**
	 * Populate the form with the challenge's current details
	 */
	private void populateFields() {
		if (challengeUri.equals("")) {
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
								view.getLocationBox().setValue(infoModel.getLocation());
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

	/**
	 * Saves edited challenge if input passes verification
	 */
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
			model.setAdminId(Long.parseLong(Cookies.getCookie("guid")));

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

	@Override
	public void onChangeLocationPreference() {
		RadioButton yesLocation = view.getLocationYesRadioButton();
		// if yes is checked
		if(yesLocation.getValue()) {
			view.getLocationDiv().getStyle().setDisplay(Display.BLOCK);
		} else {
			view.getLocationDiv().getStyle().setDisplay(Display.NONE);
			view.getLocationBox().setValue("");
		}
	}

	@Override
	public void onSearchAddressButtonClicked() {
		String searchAddress = view.getAddress();
		Geocoder geocoder = Geocoder.newInstance();
		GeocoderRequest request = GeocoderRequest.newInstance();
		request.setAddress(searchAddress);
		geocoder.geocode(request, new GeocoderRequestHandler() {
			@Override
			public void onCallback(JsArray<GeocoderResult> results,
					GeocoderStatus status) {
				// Get the LatLng Location
				view.setSearchedAddress(results.get(0).getGeometry().getLocation());
			}
		});
	}

}
