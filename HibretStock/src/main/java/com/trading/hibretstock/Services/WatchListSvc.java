package com.trading.hibretstock.Services;

import java.util.Set;

import com.trading.hibretstock.Dao.WatchListDao;
import com.trading.hibretstock.Models.Stock;
import com.trading.hibretstock.Models.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WatchListSvc {
    @Autowired
    private WatchListDao watchListDAO;

    public User getProfileAttributes(String email) {
        return this.watchListDAO.getProfileAttributes(email);
    }

    public boolean addStockToWatchList(User user, String stockSymbol) {
        return this.watchListDAO.addStockToWatchList(user, stockSymbol);
    }

    public Set<Stock> retrieveWatchList(User user)
    {
        return this.watchListDAO.retrieveWatchList(user);
    }
}