package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.client.event.GoToDashboardEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.json.UsersJsonFormatter;
import com.cs410.getfit.client.view.UserSettingsView;
import com.cs410.getfit.shared.IncomingUserJsonModel;
import com.cs410.getfit.shared.OutgoingUserJsonModel;
import com.cs410.getfit.shared.UserInfoJsonModel;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.ui.HasWidgets;

public class UserSettingsPresenter implements Presenter, UserSettingsView.Presenter{

	private final HandlerManager eventBus;
	private final UserSettingsView view;

	public UserSettingsPresenter(HandlerManager eventBus, UserSettingsView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		setCurrentInfo();
	}

	// set user's name and current privacy setting
	private void setCurrentInfo(){
		// GET request on user guid
		long currentUser = Long.parseLong(Cookies.getCookie("guid"));
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest("/users/" + currentUser); 
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
							// display current privacy setting
							view.getPrivacyPrivateRadioButton().setValue(userInfo.getIsPrivate());
							view.getPrivacyPublicRadioButton().setValue(!userInfo.getIsPrivate());
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

	@Override
	public void onSaveSettingsButtonClicked() {
		long currentUser = Long.parseLong(Cookies.getCookie("guid"));
		RequestBuilder builder = HTTPRequestBuilder.getPutRequest("/users/" + currentUser); 

		// gather user info from view
		UserInfoJsonModel info = new UserInfoJsonModel();
		info.setIsPrivate(view.getIsPrivate());

		IncomingUserJsonModel model = new IncomingUserJsonModel();
		model.setUserInfoJsonModel(info);

		List<IncomingUserJsonModel> models = new ArrayList<IncomingUserJsonModel>();
		models.add(model);

		// get formatted challenge json
		String requestJson = UsersJsonFormatter.formatUserJsonInfo(models);

		try {
			builder.sendRequest(requestJson, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						eventBus.fireEvent(new GoToDashboardEvent());
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
