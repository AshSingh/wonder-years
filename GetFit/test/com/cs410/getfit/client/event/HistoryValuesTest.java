package com.cs410.getfit.client.event;

import static org.junit.Assert.*;

import org.junit.Test;

import com.cs410.getfit.client.HistoryValues;

public class HistoryValuesTest {

	@Test
	public void HistoryValuesTest() {
		assertEquals(HistoryValues.CHALLENGES.toString(), "challenges");
		assertEquals(HistoryValues.CREATECHALLENGE.toString(), "createchallenge");
		assertEquals(HistoryValues.DASHBOARD.toString(), "dashboard");
		assertEquals(HistoryValues.EDIT.toString(), "edit");
		assertEquals(HistoryValues.ERROR.toString(), "error");
		assertEquals(HistoryValues.LOGIN.toString(), "login");
		assertEquals(HistoryValues.SETTINGS.toString(), "settings");
	}

}
