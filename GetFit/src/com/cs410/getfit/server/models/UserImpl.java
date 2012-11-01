package com.cs410.getfit.server.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "users")
public class UserImpl implements Serializable, User {

	@DatabaseField(generatedId = true)
	private long guid;
	@DatabaseField
	private String FB_ID;
	@DatabaseField
	private String firstName;
	@DatabaseField
	private String lastName;
	@DatabaseField
	private boolean isPrivate;
	
	public UserImpl() {
		//for bean definition
	}
	public UserImpl(String FB_ID, String firstname, String lastname, boolean isPrivate) {
		setFB_ID(FB_ID);
		setFirstName(firstname);
		setLastName(lastname);
		setIsPrivate(isPrivate);
	}
	@Override
	public String getFB_ID() {
		return FB_ID;
	}
	@Override
	public void setFB_ID(String FB_ID) {
		this.FB_ID = FB_ID;
	}
	@Override
	public String getFirstName() {
		return firstName;
	}
	@Override
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	@Override
	public String getLastName() {
		return lastName;
	}
	@Override
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	@Override
	public boolean getIsPrivate() {
		return isPrivate;
	}
	@Override
	public void setIsPrivate(boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	@Override
	public void setGuid(Long guid) {
		this.guid = guid;	
	}
	@Override
	public Long getGuid() {
		return guid;
	}	
}
