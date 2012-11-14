package com.cs410.getfit.shared;

public class IncomingUserJsonModel {
	private UserInfoJsonModel info;
	
	public Boolean getIsPrivate() {
		return info.getIsPrivate();
	}
}
