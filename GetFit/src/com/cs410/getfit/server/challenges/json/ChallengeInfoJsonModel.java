package com.cs410.getfit.server.challenges.json;

public class ChallengeInfoJsonModel {
	private String title;
	private long startdate;
	private long enddate;
	private String location;
	private Boolean isprivate;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public long getStartdate() {
		return startdate;
	}
	public void setStartdate(long startdate) {
		this.startdate = startdate;
	}
	public long getEnddate() {
		return enddate;
	}
	public void setEnddate(long enddate) {
		this.enddate = enddate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Boolean setIsprivate() {
		return isprivate;
	}
	public void setIsprivate(boolean isprivate) {
		this.isprivate = isprivate;
	}
}
