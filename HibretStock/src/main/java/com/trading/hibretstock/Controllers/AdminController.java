package com.trading.hibretstock.Controllers;

import java.util.ArrayList;
import java.util.List;

import jakarta.servlet.http.HttpServletRequest;

import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Services.AdminSvc;
import com.trading.hibretstock.Utils.FileReaderUtil;
import com.trading.hibretstock.Utils.SessionMgmtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class AdminController
{
    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired 
    private AdminSvc adminService;

    @Autowired
    private SessionMgmtUtil sessionMgmtUtils;


    @GetMapping (value = "/admin/modify")    
    public ModelAndView updateServiceCharge (HttpServletRequest request )
    {
        if (!this.sessionMgmtUtils.doesSessionExist(request)) 
        {
            logger.info("Admin page");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new User());
            mv.addObject("errorMessage", "You are unauthorized to view this page");
            return mv;
        }

        ModelAndView mv = new ModelAndView("admin-update");
        mv.addObject("errorMessage", "Current Service Charge: " + FileReaderUtil.readServiceChargeValue());
        return mv;
    }

    @PostMapping (value = "/admin/modify")    
    public ModelAndView updateServiceChargePost (HttpServletRequest request , @ModelAttribute("serviceCharge") String val)
    {
        this.adminService.updateServiceCharge(val);
        
        ModelAndView mv = new ModelAndView();
        mv.addObject("errorMessage", "Service Charge successfully updated");
        mv.setViewName("admin-update");
        return mv;
    }

    @GetMapping(value="/admin/verify")
    public ModelAndView verifyUsers(HttpServletRequest request)
    {
        if (!this.sessionMgmtUtils.doesSessionExist(request)) 
        {
            logger.info("Admin page");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new User());
            mv.addObject("errorMessage", "You are unauthorized to view this page");
            return mv;
        }

        List<User> userList = this.adminService.getUnverifiedUsers();
        ModelAndView mv = new ModelAndView();
        mv.addObject("userList", userList);
        mv.setViewName("admin-verify");
        return mv;
    }

    @PostMapping(value="/admin/verify")
    public ModelAndView sendEmail(HttpServletRequest request)
    {
        String[] emails = request.getParameterValues("checkedRows");
        boolean result =this.adminService.sendEmail (emails);
        ModelAndView mv = new ModelAndView();
        if (result)
            mv.addObject("successMessage", "Selected users successfully verified");
        else
            mv.addObject("errorMessage", "Selected users could not be verified");


        mv.setViewName("admin-verify");
        mv.addObject("userList", new ArrayList<User>());
       return mv;
    }
}