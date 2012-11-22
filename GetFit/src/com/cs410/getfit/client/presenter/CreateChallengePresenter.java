package com.cs410.getfit.client.presenter;

import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.client.event.GoToChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.view.CreateAndEditChallengeView;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.IncomingChallengeJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.OutgoingChallengeJsonModel;
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

public class CreateChallengePresenter implements Presenter, CreateAndEditChallengeView.Presenter{
	
	private final HandlerManager eventBus;
	private final CreateAndEditChallengeView view;

	/**
	 * Constructor for presenter for create challenge page
	 * 
	 * @param eventBus - manages changing views within the application
	 * @param view - the view to display
	 */
	public CreateChallengePresenter(HandlerManager eventBus, CreateAndEditChallengeView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	/**
	 * Standard method for displaying the page 
	 * Displays the create challenge page 
	 * 
	 * @param container - the root container of the app         
	 */	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		resetFields();
		container.add(view.getMenuBar().asWidget());
		view.createMap();
		container.add(view.asWidget());
	}

	/**
	 * Helper method - resets possible previous set text in the form fields
	 */
	private void resetFields(){
		view.getChallengeNameBox().setText("");
		view.getLocationBox().setValue("");
		view.getDescriptionBox().setText("");
		view.getPrivacyPrivateRadioButton().setValue(false);
		view.getPrivacyPublicRadioButton().setValue(true);
	}
	
	/**
	 * Creates new challenge from user input if input passes verification
	 */
	@Override
	public void onSaveChallengeButtonClicked() {
		Boolean fieldsPass = CreateAndEditChallengeHelper.verifyFields(view);

		if (fieldsPass) {
			RequestBuilder builder = HTTPRequestBuilder.getPostRequest("/challenges"); 
			
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
							List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
							String challengeUri = null;
							if (models.size() > 0) {
								OutgoingChallengeJsonModel model = models.get(0);
								List<ResourceLink> links = model.getLinks();
								for (ResourceLink link : links) {
									if (link.getType().equals(LinkTypes.CHALLENGE.toString())) {
										challengeUri = link.getUri();
										break;
									}
								}
								eventBus.fireEvent(new GoToChallengeEvent(challengeUri));
							}
							else {
								eventBus.fireEvent(new GoToErrorEvent());
							}
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
