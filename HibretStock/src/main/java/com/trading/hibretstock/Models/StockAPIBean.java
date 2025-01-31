package com.trading.hibretstock.Models;
import com.fasterxml.jackson.annotation.JsonProperty;

public class StockAPIBean {

    @JsonProperty("ticker")
    private String ticker;

    @JsonProperty("name")
    private String name;

    @JsonProperty("exchange_short")
    private String exchangeShort;

    @JsonProperty("exchange_long")
    private String exchangeLong;

    @JsonProperty("mic_code")
    private String micCode;

    @JsonProperty("currency")
    private String currency;

    @JsonProperty("price")
    private Double price;

    @JsonProperty("day_high")
    private Double dayHigh;

    @JsonProperty("day_low")
    private Double dayLow;

    @JsonProperty("day_open")
    private Double dayOpen;

    @JsonProperty("52_week_high")
    private Double week52High;

    @JsonProperty("52_week_low")
    private Double week52Low;

    @JsonProperty("market_cap")
    private Double marketCap;

    @JsonProperty("previous_close_price")
    private Double previousClosePrice;

    @JsonProperty("previous_close_price_time")
    private String previousClosePriceTime;

    @JsonProperty("day_change")
    private Double dayChange;

    @JsonProperty("volume")
    private Integer volume;

    @JsonProperty("is_extended_hours_price")
    private Boolean isExtendedHoursPrice;

    @JsonProperty("last_trade_time")
    private String lastTradeTime;
    
    @JsonProperty("percentageChange")
    private String percentageChange;

    // Getters and Setters
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

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDayHigh() {
        return dayHigh;
    }

    public void setDayHigh(Double dayHigh) {
        this.dayHigh = dayHigh;
    }
    
    public String getPercentageChange() {
        return percentageChange;
    }

    public void setPercentageChange(String percentChange) {
        this.percentageChange = percentChange;
    }

    public Double getDayLow() {
        return dayLow;
    }

    public void setDayLow(Double dayLow) {
        this.dayLow = dayLow;
    }

    public Double getDayOpen() {
        return dayOpen;
    }

    public void setDayOpen(Double dayOpen) {
        this.dayOpen = dayOpen;
    }

    public Double getWeek52High() {
        return week52High;
    }

    public void setWeek52High(Double week52High) {
        this.week52High = week52High;
    }

    public Double getWeek52Low() {
        return week52Low;
    }

    public void setWeek52Low(Double week52Low) {
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

    public void setPreviousClosePrice(Double previousClosePrice) {
        this.previousClosePrice = previousClosePrice;
    }

    public String getPreviousClosePriceTime() {
        return previousClosePriceTime;
    }

    public void setPreviousClosePriceTime(String previousClosePriceTime) {
        this.previousClosePriceTime = previousClosePriceTime;
    }

    public Double getDayChange() {
        return dayChange;
    }

    public void setDayChange(Double dayChange) {
        this.dayChange = dayChange;
    }

    public Integer getVolume() {
        return volume;
    }

    public void setVolume(Integer volume) {
        this.volume = volume;
    }

    public Boolean getIsExtendedHoursPrice() {
        return isExtendedHoursPrice;
    }

    public void setIsExtendedHoursPrice(Boolean isExtendedHoursPrice) {
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
        return "StockAPIBean{" +
                "ticker='" + ticker + '\'' +
                ", name='" + name + '\'' +
                ", exchangeShort='" + exchangeShort + '\'' +
                ", exchangeLong='" + exchangeLong + '\'' +
                ", micCode='" + micCode + '\'' +
                ", currency='" + currency + '\'' +
                ", price=" + price +
                ", dayHigh=" + dayHigh +
                ", dayLow=" + dayLow +
                ", percentageChange=" + percentageChange +
                ", dayOpen=" + dayOpen +
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

