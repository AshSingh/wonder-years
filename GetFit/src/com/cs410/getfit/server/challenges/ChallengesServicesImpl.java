package com.cs410.getfit.server.challenges;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.shared.Challenge;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;

public class ChallengesServicesImpl implements ChallengeResourceServices {
	Dao<Challenge, Long> challengeDao;
	ConnectionSource connectionSource;

	public Dao<Challenge, Long> getChallengeDao() {
		return challengeDao;
	}

	public void setChallengeDao(Dao<Challenge, Long> challengeDao) {
		this.challengeDao = challengeDao;
	}

	public ConnectionSource getConnectionSource() {
		return connectionSource;
	}

	public void setConnectionSource(ConnectionSource source) {
		this.connectionSource = source;
	}

	@Override
	public boolean create(final List<Challenge> challenges) {
		try {
			TransactionManager.callInTransaction(connectionSource,
					new Callable<Void>() {
						public Void call() throws Exception {
							for (Challenge challenge : challenges) {
								challengeDao.create(challenge);
							}
							return null;
						}
					});
		} catch (SQLException e) {
			return false;
		}
		return true;
	}

	@Override
	public List<Challenge> get(long challengeId){
		try {
			return challengeDao.queryForAll();
		} catch (SQLException e) {
			return new ArrayList<Challenge>();
		}
	}

	@Override
	public boolean update(final List<Challenge> challenges, long challengeId){
		try {
			TransactionManager.callInTransaction(connectionSource,
					new Callable<Void>() {
						public Void call() throws Exception {
							for (Challenge challenge : challenges) {
								challengeDao.update(challenge);
							}
							return null;
						}
					});
			return true;
		} catch (SQLException e) {
			return false;
		}
	}

}
