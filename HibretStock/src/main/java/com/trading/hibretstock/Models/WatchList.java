package com.trading.hibretstock.Models;

import java.util.Map;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;

@Entity
@Table(name = "watchlist")
public class WatchList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ElementCollection (fetch = FetchType.EAGER)
    @MapKeyColumn(name = "stock_id")
    @Column(name = "amount")
    @CollectionTable(name = "trade_stock")
    private Map<Integer, Integer> stockToAmount ;

    @OneToOne(fetch = FetchType.EAGER)
    @PrimaryKeyJoinColumn
    private User user;

    @Column
    private String stocks;

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return this.user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    /**
     * @return the stocks
     */
    public String getStocks() {
        return stocks;
    }

    /**
     * @param stocks the stocks to set
     */
    public void setStocks(String stocks) {
        this.stocks = stocks;
    }

    /**
     * @return the stockToAmount
     */
    public Map<Integer, Integer> getStockToAmount() {
        return stockToAmount;
    }

    /**
     * @param stockToAmount the stockToAmount to set
     */
    public void setStockToAmount(Map<Integer, Integer> stockToAmount) {
        this.stockToAmount = stockToAmount;
    }

}