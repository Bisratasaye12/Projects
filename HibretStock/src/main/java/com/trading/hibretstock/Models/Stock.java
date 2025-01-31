package com.trading.hibretstock.Models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "stock")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String ticker;

    @Column
    private String name;

    @Column
    private String exchangeShort;

    @Column
    private String exchangeLong;

    @Column
    private String micCode;

    @Column
    private String currency;

    @Column
    private double price;

    @Column
    private double dayHigh;

    @Column
    private double dayLow;

    @Column
    private double dayOpen;

    @Column
    private double week52High;

    @Column
    private double week52Low;

    @Column
    private Double marketCap;

    @Column
    private double previousClosePrice;
    
    @Column
    private String percentageChange;
    
    @Column
    private String previousClosePriceTime;

    @Column
    private double dayChange;

    @Column
    private int volume;

    @Column
    private boolean isExtendedHoursPrice;

    @Column
    private String lastTradeTime;

    // Getters and Setters
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTicker() {
        return ticker;
    }

    public void setTicker(String ticker) {
        this.ticker = ticker;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExchangeShort() {
        return exchangeShort;
    }

    public void setExchangeShort(String exchangeShort) {
        this.exchangeShort = exchangeShort;
    }
    
    public String getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(String percentChange) {
        this.percentageChange = percentChange;
    }

    public String getExchangeLong() {
        return exchangeLong;
    }

    public void setExchangeLong(String exchangeLong) {
        this.exchangeLong = exchangeLong;
    }

    public String getMicCode() {
        return micCode;
    }

    public void setMicCode(String micCode) {
        this.micCode = micCode;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(double dayHigh) {
        this.dayHigh = dayHigh;
    }

    public double getDayLow() {
        return dayLow;
    }

    public void setDayLow(double dayLow) {
        this.dayLow = dayLow;
    }

    public double getDayOpen() {
        return dayOpen;
    }

    public void setDayOpen(double dayOpen) {
        this.dayOpen = dayOpen;
    }

    public double getWeek52High() {
        return week52High;
    }

    public void setWeek52High(double week52High) {
        this.week52High = week52High;
    }

    public double getWeek52Low() {
        return week52Low;
    }

    public void setWeek52Low(double week52Low) {
        this.week52Low = week52Low;
    }

    public Double getMarketCap() {
        return marketCap;
    }

    public void setMarketCap(Double marketCap) {
        this.marketCap = marketCap;
    }

    public Double getPreviousClosePrice() {
        return previousClosePrice;
    }

    public void setPreviousClosePrice(double previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    public String getPreviousClosePriceTime() {
        return previousClosePriceTime;
    }

    public void setPreviousClosePriceTime(String previousClosePriceTime) {
        this.previousClosePriceTime = previousClosePriceTime;
    }

    public double getDayChange() {
        return dayChange;
    }

    public void setDayChange(double dayChange) {
        this.dayChange = dayChange;
    }

    public int getVolume() {
        return volume;
    }

    public void setVolume(int volume) {
        this.volume = volume;
    }

    public boolean isExtendedHoursPrice() {
        return isExtendedHoursPrice;
    }

    public void setExtendedHoursPrice(boolean isExtendedHoursPrice) {
        this.isExtendedHoursPrice = isExtendedHoursPrice;
    }

    public String getLastTradeTime() {
        return lastTradeTime;
    }

    public void setLastTradeTime(String lastTradeTime) {
        this.lastTradeTime = lastTradeTime;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "id=" + id +
                ", ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", exchangeShort='" + exchangeShort + '\'' +
                ", exchangeLong='" + exchangeLong + '\'' +
                ", micCode='" + micCode + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", dayHigh=" + dayHigh +
                ", dayLow=" + dayLow +
                ", dayOpen=" + dayOpen +
                ", percentageChange=" + percentageChange +
                ", week52High=" + week52High +
                ", week52Low=" + week52Low +
                ", marketCap=" + marketCap +
                ", previousClosePrice=" + previousClosePrice +
                ", previousClosePriceTime='" + previousClosePriceTime + '\'' +
                ", dayChange=" + dayChange +
                ", volume=" + volume +
                ", isExtendedHoursPrice=" + isExtendedHoursPrice +
                ", lastTradeTime='" + lastTradeTime + '\'' +
                '}';
    }
}
