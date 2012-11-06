package com.cs410.getfit.server.challenges.services;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import com.cs410.getfit.server.models.Challenge;
import com.cs410.getfit.server.models.ChallengeHistory;
import com.cs410.getfit.server.models.ChallengeHistoryImpl;
import com.cs410.getfit.server.models.ChallengeImpl;
import com.cs410.getfit.server.models.ChallengeUser;
import com.cs410.getfit.server.models.User;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.misc.TransactionManager;

public class ParticipantsServices implements ParticipantResourceServices {

	private long challengeId;
	private List<ChallengeUser> participants;
	private Dao<User, Long> userDao;
	private Dao<ChallengeUser, Long> challengeUserDao;
	private TransactionManager manager;
	private Dao<ChallengeHistory, Long> challengeHistoryDao;
	private Dao<Challenge, Long> challengeDao;
	public Dao<Challenge, Long> getChallengeDao() {
		return challengeDao;
	}

	public void setChallengeDao(Dao<Challenge, Long> challengeDao) {
		this.challengeDao = challengeDao;
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

	public TransactionManager getTransactionManager() {
		return manager;
	}

	public void setTransactionManager(TransactionManager manager) {
		this.manager = manager;
	}

	public void setChallengeId(long challengeId) {
		this.challengeId = challengeId;
	}

	public Dao <User, Long> getUserDao() {
		return userDao;
	}

	public void setUserDao(Dao <User, Long> userDao) {
		this.userDao = userDao;
	}

	@Override
	public List<ChallengeUser> create() throws SQLException {
		final Challenge challenge = new ChallengeImpl();
		challenge.setGuid(challengeId);
		List<ChallengeUser> challengeUsersCreated = new ArrayList<ChallengeUser>();
		challengeUsersCreated = manager
				.callInTransaction(new Callable<List<ChallengeUser>>() {
					public List<ChallengeUser> call() throws Exception {
						List<ChallengeUser> created = new ArrayList<ChallengeUser>();
						for (ChallengeUser participant : participants) {
							if (participant.getUser() == null) { 
								return null;// dont create if no user is defined
							}
						}
						for (ChallengeUser participant: participants) {
								participant.setChallenge(challenge);
								participant.setDateJoined(Calendar.getInstance().getTimeInMillis());
								ChallengeUser participant_created = challengeUserDao
										.createIfNotExists(participant);
								userDao.refresh(participant.getUser());
								challengeDao.refresh(participant.getChallenge());
								if(!participant.getUser().getIsPrivate())
									createHistoryItem(participant);
								created.add(participant_created);
						}
						return created;
					}
				});
		return challengeUsersCreated;
	}
	
	private void createHistoryItem(ChallengeUser participant) throws SQLException {
		String firstname = participant.getUser().getFirstName();
		String lastname = participant.getUser().getLastName();
		String challengeTitle = participant.getChallenge().getTitle();
		Date date = new Date(participant.getDateJoined());
		String desc = firstname +" "+lastname +" has joined "+challengeTitle+" on "+date;
		ChallengeHistory history_item = new ChallengeHistoryImpl(participant.getUser(), participant.getChallenge(), desc);
		challengeHistoryDao.create(history_item);
	}
	
	@Override
	public List<ChallengeUser> get() throws SQLException {
		List <ChallengeUser> participants = challengeUserDao.queryForEq("challenge_id", challengeId);
		for(ChallengeUser participant: participants) {
			userDao.refresh(participant.getUser());
		}
		return participants;
	}

	@Override
	public boolean update() throws SQLException {
		return false; //no bulk updating
	}

	public void setParticipants(List<ChallengeUser> participants) {
		this.participants = participants;
	}
}
