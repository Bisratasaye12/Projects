package com.trading.hibretstock.Dao;

import jakarta.persistence.EntityManager;
//import jakarta.persistence.NoResultException;
//import jakarta.persistence.NonUniqueResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import com.trading.hibretstock.Models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserAuthDao
{
    private static final Logger logger = LoggerFactory.getLogger(UserAuthDao.class);
    
    @PersistenceContext
    private EntityManager entityManager;
    
    @Transactional
	public User saveUser(User user) 
	{
		logger.info("Saving user object to database");
		return this.entityManager.merge(user);
    }
	
	public int checkIfUserExists(String email) 
	{
		logger.info("Checking if user exists");
		int result = 0;
		try {
			Query query = this.entityManager.createQuery("SELECT COUNT(u) FROM User u WHERE u.email = ?1");
			query.setParameter(1, email);
			query.setMaxResults(1);
			Long resultInLong = (Long) query.getSingleResult();
			result = Math.toIntExact(resultInLong);
		} catch (Exception e) {
			logger.error(e.toString());
			result = 0;
		}

		return result;
	}

	public User getProfileAttributes(String email) {
		logger.info("Getting user from email : " + email);
		TypedQuery<User> query = this.entityManager.createQuery("SELECT u from User u where u.email = ?1", User.class);
		query.setParameter(1, email);
		query.setMaxResults(1);
		return query.getSingleResult();
    }

}