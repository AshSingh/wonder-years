package com.cs410.getfit.server.users.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;
import com.cs410.getfit.server.users.UserNewsfeedObserver;
import com.j256.ormlite.dao.Dao;

public class UserNewsfeedServices {
	private long userId;
	private Dao<ChallengeHistory, Long> challengeHistoryDao;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private Dao<User, Long> userDao;
	private Long lastPolled;

	public List<ChallengeHistory> getChallengeHistory() throws SQLException {
		if (lastPolled == null
				|| lastPolled == 0
				|| UserNewsfeedObserver.getInstance().getLastModified() > lastPolled) {
			List<ChallengeHistory> history = new ArrayList<ChallengeHistory>();
			List<ChallengeUser> participatingIn = challengeUserDao.queryForEq(
					"user_id", userId);
			//Query all the history items for each challenge the user is participating in
			for (ChallengeUser challenge : participatingIn) {
				List<ChallengeHistory> perChallenge = challengeHistoryDao
						.queryForEq("challenge_id", challenge
								.getChallenge().getGuid());
				//Filter out history of private users
				if (perChallenge.size() > 0) {
					for(ChallengeHistory indChallengeHistory : perChallenge ) {
						userDao.refresh(indChallengeHistory.getUser());
						if(!indChallengeHistory.getUser().getIsPrivate())
							history.add(indChallengeHistory);
					}
					
				}
			}
			Collections.sort(history, new Comparator<ChallengeHistory>() {
				public int compare(ChallengeHistory o1, ChallengeHistory o2) {
					long diff = o2.getDateModified() - o1.getDateModified();
					if(diff == 0)
						return 0;
					else if(diff >0)
						return 1;
					else
						return -1;
				}
			});
			return history;
		}
		return new ArrayList<ChallengeHistory>(); // return empty list
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Dao<ChallengeHistory, Long> getChallengeHistoryDao() {
		return challengeHistoryDao;
	}

	public void setChallengeHistoryDao(
			Dao<ChallengeHistory, Long> challengeHistoryDao) {
		this.challengeHistoryDao = challengeHistoryDao;
	}

	public Dao<ChallengeUser, Long> getChallengeUserDao() {
		return challengeUserDao;
	}

	public void setChallengeUserDao(Dao<ChallengeUser, Long> challengeUserDao) {
		this.challengeUserDao = challengeUserDao;
	}

	public void setLastPolled(long lastPolled) {
		this.lastPolled = lastPolled;
	}

	public Dao<User, Long> getUserDao() {
		return userDao;
	}

	public void setUserDao(Dao<User, Long> userDao) {
		this.userDao = userDao;
	}
}
