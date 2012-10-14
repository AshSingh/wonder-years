package com.cs410.getfit.server.users;

import java.util.List;

import com.cs410.getfit.shared.User;

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

}
