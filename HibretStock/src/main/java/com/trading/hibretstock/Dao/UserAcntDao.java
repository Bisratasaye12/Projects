package com.trading.hibretstock.Dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Models.UserBank;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserAcntDao {
	@PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(UserAcntDao.class);

	public User getProfileAttributes(String email) {
		logger.info("Getting user from email : " + email);
		TypedQuery<User> query = this.entityManager.createQuery("SELECT u from User u where u.email = ?1", User.class);
		query.setParameter(1, email);
		return query.getSingleResult();
	}

	@Transactional
	public User updateProfileAttributes(String email  , String phoneNumber) {
		logger.info("Updating user using new user object::"+phoneNumber);
		User getUserFromEmail = getProfileAttributes(email);
		logger.info("updated id::"+getUserFromEmail.getId());
		getUserFromEmail = this.entityManager.find(User.class, getUserFromEmail.getId());

		getUserFromEmail.setPhoneNumber(phoneNumber);

		flushAndClear();
		return getUserFromEmail;

	}

	@Transactional
	public User updateBankDetails(User user, UserBank userBank) {
	    logger.info("updateBankDetails :: {}", userBank.getAccountNumber());

	    if (user.getUserBankDetails() == null) {
	        userBank = this.saveUserBank(userBank);
	        user.setUserBankDetails(userBank);
	        flushAndClear();
	        logger.info("New UserBank record saved. ID: {}", userBank.getId());
	    } else {
	        logger.info("User already has UserBank details. Skipping creation/update...");
	    }

	    return user;
	}

	@Transactional
	public UserBank saveUserBank(UserBank userBank) 
	{
		logger.info("Saving userbank object to database");
		UserBank mergedBank = entityManager.merge(userBank);
		logger.info("Saved UserBank with ID: {}", mergedBank);
		return mergedBank;
    }

	private void flushAndClear() {
		this.entityManager.flush();
		this.entityManager.clear();
	}
}