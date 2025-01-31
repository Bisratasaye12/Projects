package com.trading.hibretstock.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trading.hibretstock.Dao.HomeDao;
import com.trading.hibretstock.Models.Stock;
import com.trading.hibretstock.Models.StockAPIBean;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import net.sf.json.JSONSerializer;

@Service
public class HomeSvc
{
    @Autowired
    private HomeDao homePageDAO;

    private static final Logger logger = LoggerFactory.getLogger(HomeSvc.class);

    @Autowired
    private RestTemplate restTemplate;

    final String stockUri1 = "https://api.stockdata.org/v1/data/quote?symbols=AAPL,GOOG,WDC&api_token=qaW8KH5IjqONdSrkoEQLynnG0VS2dxvizfobUD2V";

    final String stockUri2 = "https://api.stockdata.org/v1/data/quote?symbols=JPM,BKNG,VZ&api_token=qaW8KH5IjqONdSrkoEQLynnG0VS2dxvizfobUD2V";
    
    final String stockUri3 = "https://api.stockdata.org/v1/data/quote?symbols=BABA,SNAP,AMD&api_token=qaW8KH5IjqONdSrkoEQLynnG0VS2dxvizfobUD2V";
    
    final String stockUri4 = "https://api.stockdata.org/v1/data/quote?symbols=CSCO,ORCL,INTC&api_token=qaW8KH5IjqONdSrkoEQLynnG0VS2dxvizfobUD2V";

    final String currencyUri = "https://api.freecurrencyapi.com/v1/latest?apikey=fca_live_yPexQ7W2qyLbZJvxYhyRRmnNebaSuUerFLU5IhT9";
    
    public List<StockAPIBean> getTopStocks() {
        List<StockAPIBean> stocks = new ArrayList<>();

        // Fetch and process data from the first API
        String result = this.restTemplate.getForObject(stockUri1, String.class);
        extractDataSection(result, stocks);

        // Fetch and process data from the second API
        result = this.restTemplate.getForObject(stockUri2, String.class);
        extractDataSection(result, stocks);
        
        // Fetch and process data from the third API
        result = this.restTemplate.getForObject(stockUri3, String.class);
        extractDataSection(result, stocks);
        
        // Fetch and process data from the fourth API
        result = this.restTemplate.getForObject(stockUri4, String.class);
        extractDataSection(result, stocks);

        logger.info("size::" + stocks.size());
        
        saveAllStocks(stocks);
        logger.info("saved stocks:", stocks);
        return stocks;
    }

    private void extractDataSection(String result, List<StockAPIBean> stocks) {
        if (result == null || result.isEmpty()) {
            logger.warn("Empty or null response received.");
            return;
        }

        try {
            Object obj = new JSONParser().parse(result);
            JSONObject jo = (JSONObject) obj;

            // Focus only on the "data" section
            JSONArray dataArray = (JSONArray) jo.get("data");

            // Map each element in the data array to StockAPIBean
            for (Object item : dataArray) {
                Map map = (Map) item;
                ObjectMapper mapper = new ObjectMapper(); // Jackson ObjectMapper
                StockAPIBean stock = mapper.convertValue(map, StockAPIBean.class);
               
                calculatePercentageChange(stock);
                
                stocks.add(stock);
            }
        } catch (ParseException e) {
            logger.error("Failed to parse JSON response: " + e.toString());
        }
    }
    
    private void calculatePercentageChange(StockAPIBean stock) {
        if (stock.getPreviousClosePrice() != null && stock.getPrice() != null) {
            double percentChange = ((stock.getPrice() - stock.getPreviousClosePrice()) / stock.getPreviousClosePrice()) * 100;

            String percentageChangeString = String.format("%.2f%%", percentChange);
            stock.setPercentageChange(percentageChangeString);
        }
    }
    
    public void saveAllStocks(List<StockAPIBean> stocks)
    {
        logger.info("Saving stock object to database");
        for (StockAPIBean stock : stocks) {
            Stock temp = null;
            try {
                temp = this.homePageDAO.checkIfStockExists(stock.getTicker());
            } catch (Exception e) {
                temp = null;
            }
            if (temp == null) 
            {
                this.homePageDAO.saveStock(stock);
            } 
            else {
                this.homePageDAO.updateStock(stock, temp.getId());
            }
        }
        logger.info("Done updating stock table");
    }

    public Map<String , String> getTopCurrencies ()
    {
        String result = this.restTemplate.getForObject(currencyUri, String.class);

        net.sf.json.JSONObject json = (net.sf.json.JSONObject) JSONSerializer.toJSON( result );        
        net.sf.json.JSONObject data = json.getJSONObject("data");
        
        Map<String,String> map = new HashMap<String,String>();
        Iterator iter = data.keys();
        
        while(iter.hasNext())
        {
            String key = (String)iter.next();
            String value = data.getString(key);
            map.put(key,value);
        }

        Map<String,String> resultMap = new HashMap<String,String>();

        String eur = map.get("EUR");
        String jpy = map.get("JPY");
        String inr = map.get("INR");

        resultMap.put("Euro", eur);
        resultMap.put("Yen", jpy);
        resultMap.put("Rupee", inr);

        return resultMap;
    }
}