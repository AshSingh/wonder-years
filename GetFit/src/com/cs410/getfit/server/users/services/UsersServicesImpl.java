package com.cs410.getfit.server.users.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.users.UserNewsfeedObservable;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

public class UsersServicesImpl implements UsersServices {
	private Dao<User, String> userDao;
	private TransactionManager transactionManager;

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
		if (user.getFB_ID() != null && !user.getFB_ID().equals("")) {
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
	
	public User getUser(String fb_id) {
		User user = null;
		try {
			ArrayList<User> userList = (ArrayList<User>) userDao.queryForEq("FB_ID", fb_id);
			if (userList.size() > 0) {
				user = userList.get(0);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public User getUserById(String guid) {
		User user = null;
		try {
			user = userDao.queryForId(guid);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	public boolean updateUser(final User user) throws SQLException {
		int rowsUpdated = 0;
		User oldUser = null;
		oldUser = userDao.queryForId(String.valueOf(user.getGuid()));
		if(oldUser != null) {
			user.setFB_ID(oldUser.getFB_ID());
			user.setFirstName(oldUser.getFirstName());
			user.setLastName(oldUser.getLastName());
			rowsUpdated = transactionManager.callInTransaction(new Callable<Integer>() {
					public Integer call() throws Exception {
						Integer updated = userDao.update(user);
						UserNewsfeedObservable.getInstance().notifyHistorySource(Calendar.getInstance().getTimeInMillis());
						return updated;
					}
				});
			return rowsUpdated == 1;
		}
		return false;
	}

	public TransactionManager getTransactionManager() {
		return transactionManager;
	}

	public void setTransactionManager(TransactionManager transactionManager) {
		this.transactionManager = transactionManager;
	}
}
