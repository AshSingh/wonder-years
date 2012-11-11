package com.cs410.getfit.client.presenter;

import java.util.List;

import com.cs410.getfit.client.event.GoToCreateChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.json.UsersJsonFormatter;
import com.cs410.getfit.client.view.DashboardView;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
import com.cs410.getfit.shared.OutgoingUserJsonModel;
import com.cs410.getfit.shared.ResourceLink;
import com.cs410.getfit.shared.UserInfoJsonModel;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class DashboardPresenter implements Presenter, DashboardView.Presenter{

	private final HandlerManager eventBus;
	private final DashboardView view;

	public DashboardPresenter(HandlerManager eventBus, DashboardView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		// customize view to display user's name and challenges user is participating in
		setUserDetails();
	}
	
	private void setUserDetails() {
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
							UserInfoJsonModel userInfo = models.get(0).getInfo();
							String fullname = userInfo.getFirstname() + " " + userInfo.getLastname();
							// display user's name
							view.getNameLabel().setText(fullname);

							List<ResourceLink> links = models.get(0).getLinks();
							for (ResourceLink link : links) {
								if (link.getType().equals(LinkTypes.USERCHALLENGES.toString())){
									displayChallenges(link.getRel() + link.getUri());
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

	// display a list of challenges that user is participating in
	private void displayChallenges(String userChallengesUri){
		// clean panel
		view.getUserChallengesPanel().clear();
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(userChallengesUri); 
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
		String challengeUri = null;
		ChallengeInfoJsonModel infoModel = model.getInfo();
		List<ResourceLink> links = model.getLinks();
		for (ResourceLink link : links) {
			if (link.getType().equals(LinkTypes.CHALLENGE.toString())) {
				challengeUri = link.getUri();
			}
		}
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
		// add to main panel
		view.getUserChallengesPanel().add(name);
	}

	public void onNewChallengeButtonClicked(){
		eventBus.fireEvent(new GoToCreateChallengeEvent());
	}
}
