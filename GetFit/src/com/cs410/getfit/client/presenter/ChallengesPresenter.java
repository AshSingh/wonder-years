package com.cs410.getfit.client.presenter;

import java.util.List;

import com.cs410.getfit.client.event.GoToCreateChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.json.ParticipantsJsonFormatter;
import com.cs410.getfit.client.json.UsersJsonFormatter;
import com.cs410.getfit.client.view.ChallengesView;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingParticipantJsonModel;
import com.cs410.getfit.shared.OutgoingUserJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.core.client.Callback;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.geolocation.client.Geolocation;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChallengesPresenter implements Presenter, ChallengesView.Presenter{

	private final HandlerManager eventBus;
	private final ChallengesView view;

	private final String NO_CHALLENGES_MSG = "There are currently no challenges to display.";

	/**
	 * Constructor for presenter for challenges page
	 * 
	 * @param eventBus - manages changing views within the application
	 * @param view - the view to display
	 */
	public ChallengesPresenter(HandlerManager eventBus, ChallengesView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	/**
	 * Standard method for displaying the page 
	 * Displays the challenges page 
	 * 
	 * @param container - the root container of the app         
	 */	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		cleanPanels();
		displayChallenges();
		displayUserChallenges();
	}

	/**
	 * Clear panels to avoid duplicated information
	 */
	private void cleanPanels(){
		view.getChallengesPanel().clear();
		view.getUserChallengesPanel().clear();
	}

	/**
	 * Retrieves the list of public challenges to be displayed
	 */
	private void displayChallenges(){
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest("/challenges"); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
						if (models.size() > 0) {
							for (OutgoingChallengeJsonModel model : models) {
								addChallengeToView(model, false);
							}
						}
						else {
							view.getChallengesPanel().add(new Label(NO_CHALLENGES_MSG));
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
	 * Displays a list of challenges in which the current user is involved
	 */
	private void displayUserChallenges() {
		// GET request on user guid 
		long userGuid = Long.parseLong(Cookies.getCookie("guid"));
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest("/users/" + userGuid); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingUserJsonModel> models = UsersJsonFormatter.parseUserJsonInfo(response.getText());
						if (models.size() > 0) {
							List<ResourceLink> links = models.get(0).getLinks();
							for (ResourceLink link : links) {
								if (link.getType().equals(LinkTypes.USERCHALLENGES.toString())){
									UserChallengesHelper.displayUserChallenges(link.getRel() + link.getUri(), view.getUserChallengesPanel(), eventBus);
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

	
	/**
	 * Helper method - displays a challenge for the public challenges list with its info
	 * 
	 * @param model - the model of the challenge to be displayed
	 */

	private void addChallengeToView(OutgoingChallengeJsonModel model, Boolean showDistance) {
		// only display public challenges
		if (!model.getInfo().getIsprivate()) {
			String challengeUri = null;
			String participantsUri = null;
			ChallengeInfoJsonModel infoModel = model.getInfo();
			List<ResourceLink> links = model.getLinks();
			for (ResourceLink link : links) {
				if (link.getType().equals(LinkTypes.CHALLENGE.toString())) {
					challengeUri = link.getUri();
				}
				else if (link.getType().equals(LinkTypes.PARTICIPANTS.toString())) {
					participantsUri = link.getRel() + link.getUri();
				}
			}
			VerticalPanel challengePanel = new VerticalPanel();
			HorizontalPanel horizontalPanel = new HorizontalPanel();
			// only create hyperlink if successfully got challenge uri
			Widget name;
			if (challengeUri != null) {
				name = new Hyperlink(infoModel.getTitle(), challengeUri);
			}
			// else just display challenge name as a label
			else {
				name = new Label(infoModel.getTitle());
			}
			name.addStyleName("challenges-link span4");
			Label participants = new Label();
			setParticipantCountLabel(participants, participantsUri);
			participants.addStyleName("challenges-participants span2");
			Label desc = new Label(infoModel.getDescription());
			desc.addStyleName("challenges-desc");
			// add widgets to subpanel
			horizontalPanel.add(name);
			horizontalPanel.add(participants);
			if (showDistance) {
				Label distance;
				if(model.getDistance() == Double.MAX_VALUE) {
					distance = new Label("N/A");
				} else {
					distance = new Label(Math.round(model.getDistance()/1000) + " kms away.");
				}
				distance.addStyleName("challenges-distance span4");
				horizontalPanel.add(distance);
			}
			challengePanel.add(horizontalPanel);
			challengePanel.add(desc);
			// add to main panel
			view.getChallengesPanel().add(challengePanel);
		}
	}

	/**
	 * Helper method - displays the number of participants for a challenge
	 * 
	 * @param participants - UI label to be set
	 * @param participantsUri - uri for HTTP request
	 */
	private void setParticipantCountLabel(final Label participants, String participantsUri) {
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(participantsUri); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingParticipantJsonModel> models = ParticipantsJsonFormatter.parseParticipantsJsonInfo(response.getText());
						participants.setText(models.size() + " participants");
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
	 * Redirects user to create challenge page when corresponding button is clicked
	 */
	public void onNewChallengeButtonClicked(){
		eventBus.fireEvent(new GoToCreateChallengeEvent());
	}
	
	/**
	 * Sorts list of public challenges based on proximity to current user
	 */
	public void onSortByLocationButtonClicked() {
		Geolocation userLocation = Geolocation.getIfSupported();
		if(userLocation != null) {
			PositionSortCallback posCallback = new PositionSortCallback(eventBus, this, view);
			userLocation.getCurrentPosition((Callback) posCallback);
		}
	}
	
	/**
	 * Helper method - displays the sorted by proximity list of challenges
	 * 
	 * @param model - the model of a challenge to be displayed
	 */
	public void receiveSortedModels(OutgoingChallengeJsonModel model) {
		addChallengeToView(model, true);
	}
}
