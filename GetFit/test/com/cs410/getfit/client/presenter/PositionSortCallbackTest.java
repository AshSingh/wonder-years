package com.cs410.getfit.client.presenter;

import org.junit.Test;

import com.cs410.getfit.client.view.ChallengesViewImpl;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.junit.client.GWTTestCase;

public class PositionSortCallbackTest extends GWTTestCase{
	//private JUnit4Mockery context = new JUnit4Mockery();

	@Test
	public void testPositionSortCallback() {
		
		HandlerManager eventbus = new HandlerManager(null);
		ChallengesViewImpl view = new ChallengesViewImpl();
		ChallengesPresenter presenter = new ChallengesPresenter(eventbus, view);
		PositionSortCallback posSortCallback = new PositionSortCallback(eventbus, presenter, view);
		assertEquals(posSortCallback.getClass().toString(),"class com.cs410.getfit.client.presenter.PositionSortCallback");
	}
	
	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}

}
