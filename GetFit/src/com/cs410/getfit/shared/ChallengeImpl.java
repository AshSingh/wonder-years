package com.cs410.getfit.shared;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "challenges")
public class ChallengeImpl implements Challenge {
	@DatabaseField(generatedId = true)
	private long guid;
	@DatabaseField(unique = true)
	private String title;
	@DatabaseField
	private long startdate;
	@DatabaseField
	private long enddate;
	@DatabaseField
	private String location;
	@DatabaseField
	private boolean isprivate;
	
	
	public ChallengeImpl(String title, boolean isPrivate, String location,
			long startDate, long endDate) {
		setTitle(title);
		setIsPrivate(isPrivate);
		setLocation(location);
		setStartDate(startDate);
		setEndDate(endDate);
	}
	public ChallengeImpl() {
		//for bean definition
	}
	public long getGuid() {
		return guid;
	}
	//For testing purposes
	public void setGuid(long guid) {
		this.guid = guid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public boolean isPrivate() {
		return isprivate;
	}
	public void setIsPrivate(boolean isPrivate) {
		this.isprivate = isPrivate;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public long getStartDate() {
		return startdate;
	}
	public void setStartDate(long startDate) {
		this.startdate = startDate;
	}
	public long getEndDate() {
		return enddate;
	}
	public void setEndDate(long endDate) {
		this.enddate = endDate;
	}
}
