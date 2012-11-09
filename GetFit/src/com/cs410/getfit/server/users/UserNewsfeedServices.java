package com.cs410.getfit.server.users;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.ChallengeUser;
import com.j256.ormlite.dao.Dao;

public class UserNewsfeedServices {
	private long userId;
	private Dao<ChallengeHistory, Long> challengeHistoryDao;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private Long lastPolled;

	public List<ChallengeHistory> getChallengeHistory() throws SQLException {
		if (UserNewsfeedObserver.getInstance().getLastModified() > lastPolled || UserNewsfeedObserver.getInstance().getLastModified() == -1 || lastPolled == null) {
			List<ChallengeHistory> history = new ArrayList<ChallengeHistory>();
			List<ChallengeUser> participatingIn = challengeUserDao.queryForEq(
					"user_id", userId);
			for (ChallengeUser challengeUser : participatingIn) {
				List<ChallengeHistory> perChallenge = challengeHistoryDao
						.queryForEq("challenge_id", challengeUser
								.getChallenge().getGuid());
				if (perChallenge.size() > 0)
					history.addAll(perChallenge);
			}
			Collections.sort(history, new Comparator<ChallengeHistory>() {
				public int compare(ChallengeHistory o1, ChallengeHistory o2) {
					return (int) (o1.getDateModified() - o2.getDateModified());
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
}
