package com.cs410.getfit.server.models;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "users")
public class UserImpl implements Serializable, User {
	@DatabaseField(generatedId = true)
	private long guid;
	@DatabaseField(unique=true, canBeNull = false)
	private String FB_ID;
	@DatabaseField
	private String firstname;
	@DatabaseField
	private String lastname;
	@DatabaseField
	private boolean isPrivate;
	
	public UserImpl() {
		//for bean definition
	}
	public UserImpl(String FB_ID, String firstname, String lastname, boolean isPrivate) {
		this.FB_ID = FB_ID;
		this.firstname = firstname;
		this.lastname = lastname;
		this.isPrivate = isPrivate;
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
		return firstname;
	}
	@Override
	public void setFirstName(String firstName){
		this.firstname = firstName;
	}
	@Override
	public String getLastName() {
		return lastname;
	}
	@Override
	public void setLastName(String lastName){
		this.lastname = lastName;
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
		
	public String toString() {
		return "FB_ID: " + this.getFB_ID() + " Name: " + this.getFirstName() + "  lastname: " + this.getLastName() + " isPrivate: " + this.getIsPrivate();
	}
}
