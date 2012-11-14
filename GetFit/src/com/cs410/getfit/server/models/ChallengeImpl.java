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
	private String location;
	@DatabaseField
	private Boolean isprivate;
	@DatabaseField
	private String description;
	private List <ChallengeUser> participants = new ArrayList<ChallengeUser>();
	
	
	public ChallengeImpl(String title, Boolean isPrivate, String location, String description, List<ChallengeUser> participants) {
		this.title = title;
		this.isprivate = isPrivate;
		this.location = location;
		this.participants = participants;
		this.description = description;
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
	@Override
	public String getDescription() {
		return description;
	}
	@Override
	public void setDescription(String description) {
		this.description = description;
	}
}
