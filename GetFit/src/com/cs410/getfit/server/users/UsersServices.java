package com.cs410.getfit.server.users;

import java.util.List;

import com.cs410.getfit.server.models.User;

/**
 * Represents the services offered by the Users resource
 * @author kiramccoan
 *
 */
public interface UsersServices {

	/**
	 * @return all users in data storage
	 */
	List<User> queryForAllUsers();

	/**
	 * create a user in data storage
	 * @param user to create
	 */
	
	void createUser(User user);
	
	/**
	 * get a user in data storage
	 * @param fb_id
	 */
	User getUser(String fb_id);
	
	/**
	 * get a user by their guid
	 * @param guid
	 * @return
	 */
	User getUserById(String guid);

}
