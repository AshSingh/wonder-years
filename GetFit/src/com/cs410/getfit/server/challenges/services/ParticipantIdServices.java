package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;
/**
 * An object which handles specific participant functionality
 * @author kiramccoan
 *
 */
public class ParticipantIdServices implements ParticipantResourceServices {

	private long participantId;
	private ChallengeUser participant;
	private Dao <ChallengeUser, Long> challengeUserDao;
	private Dao <User, Long> userDao;
	private TransactionManager manager;

	public Dao<ChallengeUser, Long> getChallengeUserDao() {
		return challengeUserDao;
	}

	public void setChallengeUserDao(Dao<ChallengeUser, Long> challengeUserDao) {
		this.challengeUserDao = challengeUserDao;
	}
	
	public TransactionManager getTransactionManager() {
		return manager;
	}

	public void setTransactionManager(TransactionManager manager) {
		this.manager = manager;
	}

	public void setParticipantId(long participantId) {
		this.participantId = participantId;
	}

	public void setParticipant(ChallengeUser participant) {
		this.participant = participant;
	}
	@Override
	public List<ChallengeUser> create() throws SQLException {
		return null; //cannot create from id resource
	}

	@Override
	public List<ChallengeUser> get() throws SQLException {
		List<ChallengeUser> participants = new ArrayList<ChallengeUser>();
		ChallengeUser participant;
		try {
			participant = challengeUserDao.queryForId(participantId);
			if (participant != null) {
				userDao.refresh(participant.getUser());
				participants.add(participant);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return new ArrayList<ChallengeUser>();
		}
		return participants;
	}

	@Override
	public boolean update() throws SQLException {
		participant.setGuid(participantId);
		ChallengeUser oldChallengeUser = null;
		Integer rowsUpdated = 0;
		oldChallengeUser = challengeUserDao.queryForId(participantId);
		userDao.refresh(oldChallengeUser.getUser());
		
		if (participant.isAdmin() != null) {
			participant.setAdmin(oldChallengeUser.isAdmin());
		}
		if (participant.getUser() != null) {
			participant.setUser(oldChallengeUser.getUser());
		}
		participant.setChallenge(oldChallengeUser.getChallenge());
		rowsUpdated = manager.callInTransaction(new Callable<Integer>() {
			public Integer call() throws Exception {
				return challengeUserDao.update(participant);
			}
		});
		return rowsUpdated == 1;
	}
	
	public Dao <User, Long> getUserDao() {
		return userDao;
	}

	public void setUserDao(Dao <User, Long> userDao) {
		this.userDao = userDao;
	}

	@Override
	public boolean delete() throws SQLException {
		int rowsDeleted = 0;
		rowsDeleted = manager.callInTransaction(new Callable<Integer>() {
			public Integer call() throws Exception {
				return challengeUserDao.deleteById(participantId);
			}
		});
		return rowsDeleted == 1;
	}
}
