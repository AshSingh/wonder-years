package com.cs410.getfit.server.models;

public interface User {

	/**
	 * @return username
	 */
	public abstract String getUsername();

	/**
	 * @param username
	 */
	public abstract void setUsername(String username);

	/**
	 * @return users password
	 */
	public abstract String getPassword();

	/**
	 * @param users password
	 */
	public abstract void setPassword(String password);

	/**
	 * @return users first name
	 */
	public abstract String getFirstName();

	/**
	 * @param users first name
	 */
	public abstract void setFirstName(String firstName);

	/**
	 * @return users last name
	 */
	public abstract String getLastName();

	/**
	 * @param users last name
	 */
	public abstract void setLastName(String lastName);

}