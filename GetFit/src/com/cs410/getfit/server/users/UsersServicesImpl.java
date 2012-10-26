package com.cs410.getfit.server.users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.cs410.getfit.server.models.User;
import com.j256.ormlite.dao.Dao;

public class UsersServicesImpl implements UsersServices {
	private Dao<User, String> userDao;

	public Dao<User, String> getUserDao() {
		return userDao;
	}

	public void setUserDao(Dao<User, String> userDao) {
		this.userDao = userDao;
	}

	public List<User> queryForAllUsers() {
		try {
			return userDao.queryForAll();
		} catch (SQLException e) {
			// TODO: return a meaningful message
			e.printStackTrace();
		}
		return new ArrayList<User>();
	}

	public void createUser(User user) {
		if (user.getId() != null && !user.getId().equals("")
				&& user.getPassword() != null && !user.getPassword().equals("")) {
			try {
				userDao.create(user);
			} catch (SQLException e) {
				// TODO: return a meaningful message
				e.printStackTrace();
			}
		} else {
			//TODO: something meaningful here
		}
	}
}
