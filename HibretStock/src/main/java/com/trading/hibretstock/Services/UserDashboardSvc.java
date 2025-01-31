package com.trading.hibretstock.Services;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.trading.hibretstock.Dao.UserDashboardDao;
import com.trading.hibretstock.Models.Stock;
import com.trading.hibretstock.Models.Trade;
import com.trading.hibretstock.Models.Transaction;
import com.trading.hibretstock.Models.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDashboardSvc
{
    @Autowired
    private UserDashboardDao userDashboardDAO;

    private static final Logger logger = LoggerFactory.getLogger(UserDashboardSvc.class);

    public User getProfileAttributes(String email) {
        return this.userDashboardDAO.getProfileAttributes(email);
    }

    public Map<String , List<? extends Object>> retrieveSellList ( User user)
    {
        return this.userDashboardDAO.retrieveSellList(user);
    }

    public Transaction completeTransaction (User user ,String[] selling )
    {
        Transaction currenTransaction = this.userDashboardDAO.createTransaction(user);
        if (selling !=null)
        {
            Set<Trade> sellTradesSet = new HashSet<Trade>();
            for (int i = 0; i < selling.length; i++)
            {
                String temp[] = selling[i].split(Pattern.quote("!"));
                
                // testing
                logger.info("temp[1]: {}", temp[1]);
                
                Stock s = this.userDashboardDAO.checkIfStockExists(temp[0]);
                Trade sellTrade = this.userDashboardDAO.createSellTrade(user , s , temp[1]  , currenTransaction);
                
                logger.info("CreateSellTrade finished::--------");
                sellTradesSet.add(sellTrade);
            }
            this.userDashboardDAO.updateSellMap (user , sellTradesSet);
            this.userDashboardDAO.updateSellTransactions(sellTradesSet, user);
        }
        return currenTransaction;
    }

    public List<Trade> getSellTradesUsingCurrentTransaction (User user , Transaction transaction)
    {
        Transaction h = this.userDashboardDAO.getLatestTransaction(user.getEmail());
        logger.info("Fetching latest transaction from user::"+h.getId());
        List<Trade> sellTrades = this.userDashboardDAO.getTradesUsingTransaction (h.getId()  , "Sell");
        return sellTrades;
    }

    public double getTotalPrice (List<Trade> trades)
    {
        double result =0.0;
        for (Trade tr: trades)
        {
            result = result + (tr.getIndividualPrice() * tr.getQuantity());
        }
        return result;
    }
}