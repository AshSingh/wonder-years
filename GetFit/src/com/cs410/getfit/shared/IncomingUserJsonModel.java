package com.cs410.getfit.shared;

public class IncomingUserJsonModel {
	private UserInfoJsonModel info;
	
	public Boolean getIsPrivate() {
		return info.getIsPrivate();
	}
	public void setUserInfoJsonModel(UserInfoJsonModel info) {
		this.info = info;
	}
}
