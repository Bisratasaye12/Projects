package com.trading.hibretstock.Controllers;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Models.UserBank;
import com.trading.hibretstock.Services.UserAcntSvc;
import com.trading.hibretstock.Utils.SessionMgmtUtil;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAcntController
{
    private static final Logger logger = LoggerFactory.getLogger(UserAcntController.class);
    
    @Autowired
    private SessionMgmtUtil sessionMgmtUtils;

    @Autowired
    private UserAcntSvc userAccountService;


    @GetMapping(value = "/update/profile")
    public ModelAndView updateProfilePage(HttpServletRequest request) 
    {
        if (!this.sessionMgmtUtils.doesSessionExist(request)) 
        {
            logger.info("Please login to access this page");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new User());
            mv.addObject("errorMessage", "Please login to access this page");
            return mv;

        }
        ModelAndView mv = new ModelAndView("profile-update");
        String email = (String) request.getSession().getAttribute("user");
        User user = this.userAccountService.getProfileAttributes(email);
        logger.info("updateProfilePage::"+user.toString());
        mv.addObject("user", user);
        return mv;
    }
    
//    @PostMapping(value="/check/account")
//    public ModelAndView checkAccount


    @PostMapping(value = "/update/profile")
    public ModelAndView updateProfile( HttpServletRequest request,
            HttpServletResponse response, @ModelAttribute("user") @Validated User user ,  BindingResult bindingResult) throws IllegalStateException, IOException {

        logger.info("Inside updateProfile::"+user.toString());
        User updatedUser = this.userAccountService.updateProfile(user.getEmail() , user.getPhoneNumber());

        ModelAndView mv = new ModelAndView("profile-update");
        mv.addObject("user", updatedUser);
        mv.addObject("successMessage", "Profile updated successfully");
        return mv;
    }

    @GetMapping(value = "/update/bankAccount")
    public ModelAndView manageBankAccount(HttpServletRequest request) 
    {
        if (!this.sessionMgmtUtils.doesSessionExist(request)) 
        {
            logger.info("Please login to access this page");
            ModelAndView mv = new ModelAndView("login-form");
            mv.addObject("user", new User());
            mv.addObject("errorMessage", "Please login to access this page");
            return mv;

        }
        ModelAndView mv = new ModelAndView("user-account");

        String email = (String) request.getSession().getAttribute("user");
        User user = this.userAccountService.getProfileAttributes(email);
        UserBank userBank = user.getUserBankDetails();
        if (userBank ==null)
            userBank = new UserBank();
            
        logger.info(userBank.toString());
        mv.addObject("userBank", userBank);
        return mv;
    }

    @PostMapping(value = "/update/bankAccount")
    public ModelAndView updateBankDetails( HttpServletRequest request,
            HttpServletResponse response, @ModelAttribute("userBank") @Validated UserBank userbank ,  BindingResult bindingResult
          ) throws IllegalStateException, IOException {
                
        logger.info("Inside updateBankDetails::"+userbank.toString());

        String email = (String) request.getSession().getAttribute("user");
        User user = this.userAccountService.getProfileAttributes(email);

        logger.info(userbank.toString());

        User updatedUser = this.userAccountService.updateBankDetails(user,userbank);

        ModelAndView mv = new ModelAndView("user-account");
        mv.addObject("user", updatedUser);
        mv.addObject("successMessage", "Bank Account added successfully");
        return mv;
    }
}
