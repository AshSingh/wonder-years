package com.cs410.getfit.shared;

public class UserInfoJsonModel {
	private String firstname;
	private String lastname;
	private Boolean isPrivate;
	
	/**
	 * @return first name
	 */
	public String getFirstname() {
		return firstname;
	}
	/**
	 * @param firstname
	 */
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	/**
	 * @return last name
	 */
	public String getLastname() {
		return lastname;
	}
	/**
	 * @param lastname
	 */
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	/**
	 * @return true if privacy setting is set to private
	 */
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	/**
	 * @param isPrivate set privacy setting to true if private
	 */
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	
}
