package com.cs410.getfit.client.presenter;

import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.view.CreateAndEditChallengeView;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.user.client.ui.HasWidgets;

public class CreateChallengePresenter implements Presenter, CreateAndEditChallengeView.Presenter{
	
	private final HandlerManager eventBus;
	private final CreateAndEditChallengeView view;

	public CreateChallengePresenter(HandlerManager eventBus, CreateAndEditChallengeView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
	}

	@Override
	public void onSaveChallengeButtonClicked() {
		Boolean fieldsPass = CreateAndEditChallengeHelper.verifyFields(view);

		if (fieldsPass) {
			RequestBuilder builder = HTTPRequestBuilder.getPostRequest("/challenges"); 
			CreateAndEditChallengeHelper.sendRequest(view, builder, eventBus);
		}
	}
}
