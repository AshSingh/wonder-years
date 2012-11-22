package com.cs410.getfit.client.view;

import org.junit.Test;

import com.google.gwt.junit.client.GWTTestCase;
import com.google.gwt.maps.client.MapWidget;
import com.google.gwt.user.client.ui.Hidden;
import com.google.gwt.user.client.ui.HorizontalPanel;

public class PositionCallbackTest extends GWTTestCase{
	
	@Test
	public void testPositionCallback() {
		MapWidget mapWidget = null;
		HorizontalPanel gMapsPanel = new HorizontalPanel();
		Hidden locationBox = null;
		
		PositionCallback posCallback = new PositionCallback(mapWidget, gMapsPanel, locationBox);
		
		assertEquals(posCallback.getMapWidget(), null);
	}

	@Override
	public String getModuleName() {
		return "com.cs410.getfit.GetFit";
	}
}
