package com.trading.hibretstock.Dao;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;

import com.trading.hibretstock.Models.Stock;
import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Models.WatchList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class WatchListDao
{
    @PersistenceContext
	private EntityManager entityManager;

	private static final Logger logger = LoggerFactory.getLogger(WatchListDao.class);

    public User getProfileAttributes(String email) {
		logger.info("Getting user from email : " + email);
		TypedQuery<User> query = this.entityManager.createQuery("SELECT u from User u where u.email = ?1", User.class);
        query.setParameter(1, email);
        query.setMaxResults(1);
		return query.getSingleResult();
    }
    
    @Transactional
    public boolean addStockToWatchList(User user, String stockSymbol) 
    {
        logger.info("addStockToWatchList::"+stockSymbol);
        logger.info("addStockToWatchList::"+user.getFirstName());
        Stock st = null;
        try {
            st=checkIfStockExists(stockSymbol);
        } catch (Exception e) {
            st=null;
        }

        if (st!=null)
        {
            WatchList wt = user.getWatchList();
            
            if (wt ==null)
            {
                wt = new WatchList();
            }
            logger.info("addStockToWatchList::"+wt.toString());
            String hs = wt.getStocks();
            if (hs ==null || hs.isEmpty())
            {
                hs="" + st.getTicker();
            }
            else
                hs = hs + "|" + st.getTicker();

            wt.setStocks(hs);
            user.setWatchList(wt);
            logger.info("addStockToWatchList::"+user.getWatchList().toString());
            flushAndClear();
        }

        logger.info("addStockToWatchList::done updating");
        return true;
		
    }
    
    public Stock checkIfStockExists(String symbol) throws NoResultException {
        logger.info("Getting stock data froma database for user ");
        TypedQuery<Stock> query = this.entityManager.createQuery("SELECT c from Stock c where c.ticker = ?1",
                Stock.class);
        query.setParameter(1, symbol);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    private void flushAndClear() {
		this.entityManager.flush();
		this.entityManager.clear();
    }
    
    public Set<Stock> retrieveWatchList (User user)
    {
        logger.info("retrieveWatchList::"+user.getEmail());
        Set<Stock> stockList = new HashSet<Stock>();

        WatchList wt = user.getWatchList();
        if (wt ==null)
        {
            return new HashSet<Stock>();
        }

        String stocks = wt.getStocks();
        String[] pipe_split = stocks.split("\\|");

        for (String st : pipe_split)
        {
            Stock s =null;
            try {
                s = checkIfStockExists(st);
            } catch (Exception e) {
                s=null;
            }

            if (s!=null)
                stockList.add(s);
        }
        logger.info("retrieveWatchList::size::"+stockList.size());
        return stockList;
        
    }
}