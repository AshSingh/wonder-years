package com.cs410.getfit.client.presenter;

import java.util.Date;
import java.util.List;

import com.cs410.getfit.client.event.GoToCreateChallengeEvent;
import com.cs410.getfit.client.event.GoToErrorEvent;
import com.cs410.getfit.client.json.ChallengeHistoryJsonFormatter;
import com.cs410.getfit.client.json.ChallengesJsonFormatter;
import com.cs410.getfit.client.json.HTTPRequestBuilder;
import com.cs410.getfit.client.json.UsersJsonFormatter;
import com.cs410.getfit.client.view.DashboardView;
import com.cs410.getfit.shared.ChallengeInfoJsonModel;
import com.cs410.getfit.shared.LinkTypes;
import com.cs410.getfit.shared.NewsfeedItemType;
import com.cs410.getfit.shared.OutgoingChallengeHistoryJsonModel;
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
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.HasWidgets;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

public class DashboardPresenter implements Presenter, DashboardView.Presenter{

	private final HandlerManager eventBus;
	private final DashboardView view;

	private final String NO_NEWSFEED_MSG = "There is currently no newsfeed to display.";
	
	// polling variables
	private static Timer refreshTimer;
	private final int POLL_INTERVAL = 2000;

	private long lastPollDate;
	private final String dateFormat = "MMM d, yyyy 'at' hh:mm:ss a";

	/**
	 * Constructor for presenter for dashboard page
	 * 
	 * @param eventBus - manages changing views within the application
	 * @param view - the view to display
	 */
	public DashboardPresenter(HandlerManager eventBus, DashboardView view){
		this.eventBus = eventBus;
		this.view = view;
		this.view.setPresenter(this);
	}

	/**
	 * Standard method for displaying the page 
	 * Displays the dashboard page 
	 * 
	 * @param container - the root container of the app         
	 */	
	@Override
	public void go(HasWidgets container) {
		container.clear();
		container.add(view.getMenuBar().asWidget());
		container.add(view.asWidget());
		// customize view to display user's name and challenges user is participating in, display newsfeed
		populateView();
	}

	/**
	 * Stops polling once user has navigated away from the dashboard page
	 */
	public static void cancelRefreshTimer(){
		if (refreshTimer != null) {
			refreshTimer.cancel();
			refreshTimer = null;
		}
	}
	
	/**
	 * Initializes timer and starts polling 
	 * 
	 * @param newsfeedUri - the uri for newsfeed
	 */
	private void setUpNewsfeed(final String newsfeedUri){
		// reset last poll date
		lastPollDate = new Long(0);
		// Setup timer to periodically check for new newsfeed items automatically.
		refreshTimer = new Timer() {
			@Override
			public void run() {
				checkForNewsfeedUpdates(newsfeedUri);
			}
		};
		refreshTimer.scheduleRepeating(POLL_INTERVAL);
	}

	/**
	 * Helper method - called during polling
	 * Checks to see if there is any new newsfeed items
	 * 
	 * Scenarios
	 * json returned is empty, no new info to display
	 * json returned is empty on first poll - no newsfeed to display, display appropriate msg
	 * json returned has content, update newsfeed panel
	 * 
	 * @param newsfeedUri - the uri for newsfeed
	 */
	private void checkForNewsfeedUpdates(String newsfeedUri) {
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(newsfeedUri + "?lastPolled=" + lastPollDate); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						long pollTime = lastPollDate;
						// update last poll date
						lastPollDate = System.currentTimeMillis();
						List<OutgoingChallengeHistoryJsonModel> models = ChallengeHistoryJsonFormatter.parseChallengeHistoryJsonInfo(response.getText());
						// only update newsfeed when new items exist
						if (models.size() > 0) {
							// clear newsfeed panel
							view.getNewsFeedPanel().clear();
							for (OutgoingChallengeHistoryJsonModel model : models) {
								// four widgets per news feed item: user, action, challenge, date 
								// allows different parts of newsfeed item to be populated asynchronously 
								VerticalPanel newsfeedItemPanel = new VerticalPanel();
								HorizontalPanel detailsPanel = new HorizontalPanel();
								
								Label userLabel = new Label();
								Label actionLabel = new Label();
								actionLabel.addStyleName("newsfeed-item-action");
								Hyperlink challengeHyperlink = new Hyperlink();
								Label datetimeLabel = new Label();
								datetimeLabel.addStyleName("newsfeed-item-datetime");
								
								detailsPanel.add(userLabel);
								detailsPanel.add(actionLabel);
								detailsPanel.add(challengeHyperlink);
								
								newsfeedItemPanel.add(detailsPanel);
								newsfeedItemPanel.add(datetimeLabel);
								
								view.getNewsFeedPanel().add(newsfeedItemPanel);
								// set datetime text
								Date itemDate = new Date(model.getInfo().getDatemodified());
								String dateString = DateTimeFormat.getFormat(dateFormat).format(itemDate);
								datetimeLabel.setText(dateString);
								// set action text
								String itemType = model.getInfo().getNewsfeedItemType();
								if (itemType.equals(NewsfeedItemType.COMPLETE.toString())) {
									actionLabel.setText("completed");
								}
								else if (itemType.equals(NewsfeedItemType.CREATE.toString())) {
									actionLabel.setText("created");
								}
								else if (itemType.equals(NewsfeedItemType.EDIT.toString())) {
									actionLabel.setText("editted the details of");	
								}							
								else if (itemType.equals(NewsfeedItemType.JOIN.toString())) {
									actionLabel.setText("joined");
								}
								// set user text
								displayUserForNewsfeedItem(model, userLabel);
								// set challenge hyperlink
								displayChallengeForNewsfeedItem(model, challengeHyperlink);
							}
						}
						else {
							// if no models returned on first poll, no newsfeed to display
							// else no new newsfeed items to display - no action needed
							if (pollTime == 0) {
								view.getNewsFeedPanel().clear();
								view.getNewsFeedPanel().add(new Label(NO_NEWSFEED_MSG));
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
	 * Helper method - displays the name of the user involved in a newsfeed item
	 * 
	 * @param model - a single challenge history model for the newsfeed item
	 * @param userLabel - the label to set the name to
	 */
	private void displayUserForNewsfeedItem(OutgoingChallengeHistoryJsonModel model, final Label userLabel){
		List<ResourceLink> links = model.getLinks();
		String userUri = null; 
		for (ResourceLink link : links) {
			if (link.getType().equals(LinkTypes.USER.toString())) { 
				userUri = link.getUri();
			}
		}
		if (userUri == null) {
			// if can't retrieve user name, use generic name
			userLabel.setText("A user");
		}
		else {
			RequestBuilder builder = HTTPRequestBuilder.getGetRequest(userUri); 
			try {
				builder.sendRequest(null, new RequestCallback() {
					@Override
					public void onResponseReceived(Request request, Response response) {
						if (response.getStatusCode() == 200) {
							List<OutgoingUserJsonModel> models = UsersJsonFormatter.parseUserJsonInfo(response.getText());
							if (models.size() > 0) {
								UserInfoJsonModel userInfo = models.get(0).getInfo();
								userLabel.setText(userInfo.getFirstname() + " " + userInfo.getLastname());
							}
							else {
								// if can't retrieve user name, use generic name
								userLabel.setText("A user");
							}
						}
						else {
							// if can't retrieve user name, use generic name
							userLabel.setText("A user");
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
	 * Helper method - displays a hyperlink to the challenge involved in a newsfeed item
	 * 
	 * @param model - a single challenge history model for the newsfeed item
	 * @param challengeHyperlink - the hyperlink to set the challenge to
	 */
	private void displayChallengeForNewsfeedItem(OutgoingChallengeHistoryJsonModel model, final Hyperlink challengeHyperlink){
		List<ResourceLink> links = model.getLinks();
		String challengeUri = null; 
		for (ResourceLink link : links) {
			if (link.getType().equals(LinkTypes.CHALLENGE.toString())) { 
				challengeUri = link.getUri();
			}
		}
		challengeHyperlink.setTargetHistoryToken(challengeUri);
		RequestBuilder builder = HTTPRequestBuilder.getGetRequest(challengeUri); 
		try {
			builder.sendRequest(null, new RequestCallback() {
				@Override
				public void onResponseReceived(Request request, Response response) {
					if (response.getStatusCode() == 200) {
						List<OutgoingChallengeJsonModel> models = ChallengesJsonFormatter.parseChallengeJsonInfo(response.getText());
						if (models.size() > 0) {
							ChallengeInfoJsonModel challengeInfo = models.get(0).getInfo();
							challengeHyperlink.setText(challengeInfo.getTitle());
						}
						else {
							// if can't retrieve challenge name, use generic name
							challengeHyperlink.setText("a challenge");
						}
					}
					else {
						// if can't retrieve challenge name, use generic name
						challengeHyperlink.setText("a challenge");
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
	 * Helper method - populates view with user specific information (user's challenges and newsfeed)
	 */
	private void populateView() {
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
									// display challenges user is participating in
									UserChallengesHelper.displayUserChallenges(link.getRel() + link.getUri(), view.getUserChallengesPanel(), eventBus);
								}
								else if (link.getType().equals(LinkTypes.NEWSFEED.toString())) {
									// set up newsfeed with auto-refresh 
									setUpNewsfeed(link.getRel() + link.getUri());
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
	 * Redirects user to create challenge page when corresponding button is clicked
	 */
	public void onNewChallengeButtonClicked(){
		eventBus.fireEvent(new GoToCreateChallengeEvent());
	}
}
