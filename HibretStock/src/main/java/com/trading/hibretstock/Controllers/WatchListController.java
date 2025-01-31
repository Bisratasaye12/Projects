package com.trading.hibretstock.Controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import com.trading.hibretstock.Models.Stock;
import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Services.WatchListSvc;
import com.trading.hibretstock.Utils.SessionMgmtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class WatchListController
{
    private static final Logger logger = LoggerFactory.getLogger(WatchListController.class);
    

    @Autowired
    private WatchListSvc watchListService;

    @Autowired
    private SessionMgmtUtil sessionMgmtUtils;

    @PostMapping(value = "/watchlist" , consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addToWatchList (HttpServletRequest request , @RequestBody HashMap<String, String> stockSymbol)
    {
        logger.info("addToWatchList::"+stockSymbol.get("ticker"));
        String email = (String) request.getSession().getAttribute("user");
        User user = this.watchListService.getProfileAttributes(email);


        boolean result = this.watchListService.addStockToWatchList (user , stockSymbol.get("ticker"));
        Map<String , String> res = new HashMap<String, String>();
        res.put("result", "Success");
        return ResponseEntity.ok(res);
    }

    @GetMapping(value = "/watchlist" )
    public ModelAndView retrieveWatchList (HttpServletRequest request )
    {
        if (!this.sessionMgmtUtils.doesSessionExist(request)) 
        {
            logger.info("Please login to access this page");
            ModelAndView mv = new ModelAndView("signin-form");
            mv.addObject("user", new User());
            mv.addObject("errorMessage", "Please login to access this page");
            return mv;

        }

        String email = (String) request.getSession().getAttribute("user");
        User user = this.watchListService.getProfileAttributes(email);


        Set<Stock> stockList= this.watchListService.retrieveWatchList (user);
        logger.info("Returned stocklist from watch::"+stockList.size());

        ModelAndView mv = new ModelAndView("watch-list");
        mv.addObject("stockList", stockList);
        return mv;
    }

}