package com.cs410.getfit.shared;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "users")
public class UserImpl implements Serializable, User {

	@DatabaseField(id = true)
	private String username;
	@DatabaseField
	private String password;
	@DatabaseField
	private String firstname;
	@DatabaseField
	private String lastname;
	
	public UserImpl() {
		//for bean definition
	}
	public UserImpl(String username, String password, String firstname, String lastname) {
		setUsername(username);
		setPassword(password);
		setFirstName(firstname);
		setLastName(lastname);
	}
	@Override
	public String getUsername() {
		return username;
	}
	@Override
	public void setUsername(String username) {
		this.username = username;
	}
	@Override
	public String getPassword() {
		return password;
	}
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String getFirstName() {
		return firstname;
	}
	@Override
	public void setFirstName(String firstName) {
		this.firstname = firstName;
	}
	@Override
	public String getLastName() {
		return lastname;
	}
	@Override
	public void setLastName(String lastName) {
		this.lastname = lastName;
	}	
}
