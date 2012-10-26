package com.cs410.getfit.server.models;

import java.util.ArrayList;
import java.util.List;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "challenges")
public class ChallengeImpl implements Challenge {
	@DatabaseField(generatedId = true)
	private long guid;
	@DatabaseField
	private String title;
	@DatabaseField
	private long startdate;
	@DatabaseField
	private long enddate;
	@DatabaseField
	private String location;
	@DatabaseField
	private Boolean isprivate;
	private List <ChallengeUser> participants = new ArrayList<ChallengeUser>();
	//private Set <Activity> activities;
	
	
	public ChallengeImpl(String title, Boolean isPrivate, String location,
			long startDate, long endDate, List<ChallengeUser> participants) {
		setTitle(title);
		setIsPrivate(isPrivate);
		setLocation(location);
		setStartDate(startDate);
		setEndDate(endDate);
		setParticipants(participants);
	}
	public ChallengeImpl() {
		//for bean definition
	}
	@Override
	public long getGuid() {
		return guid;
	}
	@Override
	public String getTitle() {
		return title;
	}
	@Override
	public void setTitle(String title) {
		this.title = title;
	}
	@Override
	public Boolean isPrivate() {
		return isprivate;
	}
	@Override
	public void setIsPrivate(Boolean isPrivate) {
		this.isprivate = isPrivate;
	}
	@Override
	public String getLocation() {
		return location;
	}
	@Override
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public long getStartDate() {
		return startdate;
	}
	@Override
	public void setStartDate(long startDate) {
		this.startdate = startDate;
	}
	@Override
	public long getEndDate() {
		return enddate;
	}
	@Override
	public void setEndDate(long endDate) {
		this.enddate = endDate;
	}
	@Override
	public List <ChallengeUser> getParticipants() {
		return participants;
	}
	@Override
	public void setParticipants(List <ChallengeUser> participants) {
		this.participants = participants;
	}
	@Override // for testing purposes only
	public void setGuid(Long guid) {
		this.guid = guid;
		
	}
}
