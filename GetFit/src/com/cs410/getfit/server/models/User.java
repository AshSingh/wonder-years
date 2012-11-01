package com.cs410.getfit.server.models;

public interface User {

	/**
	 * @return FB ID
	 */
	public abstract String getFB_ID();
	
	/**
	 * @return FB ID
	 */
	public abstract void setFB_ID(String FB_ID);

	/**
	 * @return users boolean isPrivate privacy setting
	 */
	public abstract boolean getIsPrivate();

	/**
	 * @param set users boolean isPrivate privacy setting
	 */
	public abstract void setIsPrivate(boolean isPrivate);

	/**
	 * @return users first name
	 */
	public abstract String getFirstName();
	
	/**
	 * @return users first name
	 */
	public abstract void setFirstName(String firstName);

	/**
	 * @return users last name
	 */
	public abstract String getLastName();
	
	/**
	 * @return users last name
	 */
	public abstract void setLastName(String lastName);
	
	public abstract void setGuid(Long guid);
	public abstract Long getGuid();

}