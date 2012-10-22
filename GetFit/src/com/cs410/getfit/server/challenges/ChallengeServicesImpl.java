package com.cs410.getfit.server.challenges;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.MethodNotSupportedException;

import com.cs410.getfit.shared.Challenge;
import com.j256.ormlite.dao.Dao;

public class ChallengeServicesImpl implements ChallengeResourceServices {

	Dao<Challenge, Long> challengeDao;
	
	public Dao<Challenge, Long> getChallengeDao() {
		return challengeDao;
	}

	public void setChallengeDao(Dao<Challenge, Long> challengeDao) {
		this.challengeDao = challengeDao;		
	}

	@Override
	public boolean create(List<Challenge> challenges) throws MethodNotSupportedException {
		throw new MethodNotSupportedException("Cannot create challenge in challengeService.");
	}

	@Override
	public List<Challenge> get(long challengeId){
		List<Challenge> challenges = new ArrayList<Challenge>();
		Challenge challenge;
		try {
			challenge = challengeDao.queryForId(challengeId);
			if(challenge != null)
				challenges.add(challenge);
		} catch (SQLException e) {
			return challenges; //hmmmm...maybe not
		}
		return challenges;
	}

	@Override
	public boolean update(List<Challenge> challenges, long challengeId) {
		if(challenges.size() == 1) {
			try {
				if(challengeDao.queryForId(challengeId) != null){
					challengeDao.update(challenges.get(0));
					return true;
				}
			} catch (SQLException e) {
				return false;
			}
		}
		return false;
	}

}
