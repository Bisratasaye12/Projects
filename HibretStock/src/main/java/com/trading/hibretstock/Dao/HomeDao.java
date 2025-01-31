package com.trading.hibretstock.Dao;

import com.trading.hibretstock.Models.Stock;
import com.trading.hibretstock.Models.StockAPIBean;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

@Repository
public class HomeDao {

    private static final Logger logger = LoggerFactory.getLogger(HomeDao.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void saveStock(StockAPIBean stockAPIBean) {
        Stock stock = mapToStock(stockAPIBean, new Stock());
        entityManager.persist(stock);
        flushAndClear();
    }

    @Transactional
    public void updateStock(StockAPIBean stockAPIBean, Long id) {
        Stock stock = entityManager.find(Stock.class, id);
        if (stock != null) {
            mapToStock(stockAPIBean, stock);
            entityManager.merge(stock);
            flushAndClear();
        } else {
            logger.warn("Stock with ID {} not found for update.", id);
        }
    }

    public Stock findStockByTicker(String ticker) throws NoResultException {
        logger.info("Fetching stock by ticker: {}", ticker);
        TypedQuery<Stock> query = entityManager.createQuery(
                "SELECT s FROM Stock s WHERE s.ticker = :ticker", Stock.class);
        query.setParameter("ticker", ticker);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    private Stock mapToStock(StockAPIBean stockAPIBean, Stock stock) {
    	stock.setTicker(stockAPIBean.getTicker()); // Setting ticker
    	stock.setName(stockAPIBean.getName()); // Setting name
    	stock.setExchangeShort(stockAPIBean.getExchangeShort()); // Setting short exchange name
    	stock.setExchangeLong(stockAPIBean.getExchangeLong()); // Setting long exchange name
    	stock.setMicCode(stockAPIBean.getMicCode()); // Setting MIC code
    	stock.setCurrency(stockAPIBean.getCurrency()); // Setting currency
    	stock.setPrice(parseDoubleOrDefault(stockAPIBean.getPrice(), 0.0)); // Setting price
    	stock.setDayHigh(parseDoubleOrDefault(stockAPIBean.getDayHigh(), 0.0)); // Setting day high
    	stock.setDayLow(parseDoubleOrDefault(stockAPIBean.getDayLow(), 0.0)); // Setting day low
    	stock.setDayOpen(parseDoubleOrDefault(stockAPIBean.getDayOpen(), 0.0)); // Setting day open
    	stock.setWeek52High(parseDoubleOrDefault(stockAPIBean.getWeek52High(), 0.0)); // Setting 52-week high
    	stock.setWeek52Low(parseDoubleOrDefault(stockAPIBean.getWeek52Low(), 0.0)); // Setting 52-week low
    	stock.setMarketCap(parseDoubleOrDefault(stockAPIBean.getMarketCap(), 0.0)); // Setting market capitalization
    	stock.setPreviousClosePrice(parseDoubleOrDefault(stockAPIBean.getPreviousClosePrice(), 0.0)); // Setting previous close price
    	stock.setPreviousClosePriceTime(stockAPIBean.getPreviousClosePriceTime()); // Setting previous close price time
    	stock.setDayChange(parseDoubleOrDefault(stockAPIBean.getDayChange(), 0.0)); // Setting day change
    	stock.setVolume(stockAPIBean.getVolume()); // Setting volume
    	stock.setExtendedHoursPrice(stockAPIBean.getIsExtendedHoursPrice()); // Setting extended hours price flag
    	stock.setLastTradeTime(stockAPIBean.getLastTradeTime()); // Setting last trade time

        return stock;
    }

    private Double parseDoubleOrDefault(Double double1, Double defaultValue) {
        try {
            return (double1 != null) ? double1 : defaultValue;
        } catch (NumberFormatException e) {
            logger.warn("Failed to parse value: {}. Defaulting to {}", double1, defaultValue, e);
            return defaultValue;
        }
    }
    
    public Stock checkIfStockExists(String symbol) throws NoResultException 
    {
        logger.info("Getting user data from database for user ");
        TypedQuery<Stock> query = this.entityManager.createQuery("SELECT c from Stock c where c.ticker = ?1",
                Stock.class);
        query.setParameter(1, symbol);
        query.setMaxResults(1);
        return query.getSingleResult();
    }

    private void flushAndClear() {
        entityManager.flush();
        entityManager.clear();
    }
}
