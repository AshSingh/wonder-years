package com.cs410.getfit.client.presenter;

import java.util.List;

import com.cs410.getfit.client.event.GoToCreateChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.view.ChallengesView;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;

public class ChallengesPresenter implements Presenter, ChallengesView.Presenter{

	private final HandlerManager eventBus;
	private final ChallengesView view;

	public ChallengesPresenter(HandlerManager eventBus, ChallengesView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		displayChallenges();
	}

	// display a list of challenges
	private void displayChallenges(){
		// clean panel
		view.getChallengesPanel().clear();
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest("/challenges"); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
						if (models.size() > 0) {
							for (OutgoingChallengeJsonModel model : models) {
								addChallengeToView(model);
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

	private void addChallengeToView(OutgoingChallengeJsonModel model) {
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
			name.addStyleName("challenges-link");
			Label participants = new Label();
			setParticipantCountLabel(participants, participantsUri);
			Label desc = new Label(infoModel.getDescription());
			desc.addStyleName("challenges-desc");
			// add widgets to subpanel
			horizontalPanel.add(name);
			horizontalPanel.add(participants);
			challengePanel.add(horizontalPanel);
			challengePanel.add(desc);
			// add to main panel
			view.getChallengesPanel().add(challengePanel);
		}
	}

	// get number of participants in a challenge and edit label accordingly
	private void setParticipantCountLabel(final Label participants, String participantsUri) {
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(participantsUri); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
						participants.setText(models.size() + " participants");
					}
					else {
						// TODO: temp edit out error until empty table error is fixed to 
							// send back an empty array instead
						//eventBus.fireEvent(new GoToErrorEvent(response.getStatusCode()));
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

	public void onNewChallengeButtonClicked(){
		eventBus.fireEvent(new GoToCreateChallengeEvent());
	}
}
