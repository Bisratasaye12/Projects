package com.trading.hibretstock.Dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;

import com.trading.hibretstock.Models.Stock;
import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Models.WatchList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;


@Component
public class StockAnalysisDao {
    @PersistenceContext
    private EntityManager entityManager;

    private static final Logger logger = LoggerFactory.getLogger(StockAnalysisDao.class);

    public User getProfileAttributes(String email) {
        logger.info("Getting user from email : " + email);
        TypedQuery<User> query = this.entityManager.createQuery("SELECT u from User u where u.email = ?1", User.class);
        query.setParameter(1, email);
        return query.getSingleResult();
    }

    public Map<String, List<? extends Object>> retrieveSellList(User user) {

        List<Stock> sellingList = new ArrayList<Stock>();
        List<Integer> sellingListAmount = new ArrayList<Integer>();

        User u = getProfileAttributes(user.getEmail());
        WatchList wt = u.getWatchList();

        if (wt != null) {
            logger.info("watchlist id:::" + wt.getId());

            Map<Integer, Integer> map = wt.getStockToAmount();
            logger.info("map::" + map);
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry pair = (Map.Entry) it.next();
                if ((int) pair.getValue() > 0) {
                    Integer stockKey = (Integer) pair.getKey();
                    Stock s = this.getStockFromId(Integer.toUnsignedLong((int) pair.getKey()));
                    logger.info("unsold shares::" + s.getTicker());
                    sellingList.add(s);
                    sellingListAmount.add((int) pair.getValue());
                }
            }

        }

        Map<String, List<? extends Object>> userStockList = new HashMap<String, List<? extends Object>>();
        userStockList.put("Sell", sellingList);
        userStockList.put("Amount", sellingListAmount);

        return userStockList;

    }

    public Stock getStockFromId(long id) throws NoResultException {
        logger.info("Getting stock data from database for ID ");
        TypedQuery<Stock> query = this.entityManager.createQuery("SELECT c from Stock c where c.id = ?1", Stock.class);
        query.setParameter(1, id);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    public List<Stock> getWatchlistStocks(User user) 
    {
        logger.info("getWatchlistStocks::" + user.getEmail());
        WatchList wt = user.getWatchList();

        List<Stock> watchListStocks = new ArrayList<Stock>();

        if (wt != null) 
        {
            logger.info("watchlist id:::" + wt.getId());

            Map<Integer, Integer> map = wt.getStockToAmount();
            logger.info("watchlist map::" + map);
            Iterator it = map.entrySet().iterator();
            while (it.hasNext()) 
            {
                Map.Entry pair = (Map.Entry) it.next();
                Integer stockKey = (Integer) pair.getKey();
                Stock s = this.getStockFromId(Integer.toUnsignedLong((int) pair.getKey()));
                watchListStocks.add(s);
            }

        }
        logger.info("getWatchlistStocks::watchListStocks" + watchListStocks.size());

        return watchListStocks;

    }

}