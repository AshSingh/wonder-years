package com.cs410.getfit.shared;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@SuppressWarnings("serial")
@DatabaseTable(tableName = "users")
public class User implements Serializable {

	@DatabaseField(generatedId = true)
	private long guid;
	@DatabaseField(unique = true)
	private String email;
	@DatabaseField
	private String password;
	@DatabaseField
	private String firstName;
	@DatabaseField
	private String lastName;
	
	public long getGuid() {
		return guid;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}	
}
