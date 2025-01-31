package com.trading.hibretstock.Controllers;

import java.io.IOException;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.trading.hibretstock.Models.SignUpForm;
import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Services.UserAuthSvc;
import com.trading.hibretstock.Utils.SessionMgmtUtil;
import com.trading.hibretstock.Validations.UserValidation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserAuthController {
    private static final Logger logger = LoggerFactory.getLogger(UserAuthController.class);

    @Autowired
    private UserAuthSvc userAuthService;

    @Autowired
    private SessionMgmtUtil sessionMgmtUtils;

    @Autowired
    UserValidation registerUserValidator;

    @GetMapping(value = "/register")
    public ModelAndView showRegisterPage(HttpServletRequest request, HttpServletResponse response, ModelMap map) {
        ModelAndView mv = new ModelAndView("signup-form");
        mv.addObject("user", new User());
        return mv;
    }

    @PostMapping(value = "/register")
    public ModelAndView registerUser(HttpServletRequest request,
            HttpServletResponse response, @ModelAttribute("user") @Validated User user, BindingResult bindingResult,
            ModelMap map) throws IllegalStateException, IOException {

        registerUserValidator.validate(user, bindingResult);

        if (bindingResult.hasErrors())
        {
            ModelAndView mv = new ModelAndView("signup-form");
            mv.addObject("user", new User());
            mv.addObject("errorMessage", "Password should be minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
            return mv;
        }

        if (this.userAuthService.checkIfUserRegistered(user)) {
            ModelAndView mv = new ModelAndView("signup-form");
            mv.addObject("user", new User());
            mv.addObject("errorMessage", "Oops. An account with this email already exists.");
            return mv;
        }

        if (userAuthService.registerUser(user)) 
        {
            logger.info("User successfully registered");
            
            logger.info("Successfully authenticated");
            this.sessionMgmtUtils.createNewSessionForUser(request, user.getEmail());
            ModelAndView mv = new ModelAndView("redirect:/");
            if (this.userAuthService.isUserVerified(user.getEmail())) {
                request.getSession().setAttribute("verified", "yes");
            } else {
                request.getSession().setAttribute("verified", "no");
            }
            return mv;
           
        } 
        else 
        {
            ModelAndView mv = new ModelAndView("signup-form");
            mv.addObject("user", new User());
            mv.addObject("errorMessage", "Oops. Something went wrong. Please try again.");
            return mv;

        }

    }

    @GetMapping(value = "/login")
    public ModelAndView showLoginPage(HttpServletRequest request, HttpServletResponse response, SignUpForm user) {
        ModelAndView mv = new ModelAndView("signin-form");
        mv.addObject("user", new User());
        return mv;
    }

    @PostMapping(value = "/login")
    public ModelAndView loginUser(HttpServletRequest request, HttpServletResponse response,
            @ModelAttribute("user") @Validated SignUpForm loginForm, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            return new ModelAndView("signin-form");
        }

        if (!this.sessionMgmtUtils.doesSessionExist(request)) {

            if (this.userAuthService.isAdmin(loginForm) == true) {
                logger.info("Admin login");
                this.sessionMgmtUtils.createNewSessionForUser(request, loginForm.getEmail());
                ModelAndView mv = new ModelAndView("redirect:/");
                request.getSession().setAttribute("isAdmin", "Yes");
                return mv;
            } else if (this.userAuthService.authenticate(loginForm) == true) {
                logger.info("Successfully authenticated");
                this.sessionMgmtUtils.createNewSessionForUser(request, loginForm.getEmail());
                ModelAndView mv = new ModelAndView("redirect:/");
                if (this.userAuthService.isUserVerified(loginForm)) {
                    request.getSession().setAttribute("verified", "yes");
                } else {
                    request.getSession().setAttribute("verified", "no");
                }
                return mv;

            } else {
                logger.info("Invalid user credentails");
                ModelAndView mv = new ModelAndView("signin-form");
                mv.addObject("user", new User());
                mv.addObject("errorMessage", "Invalid credentails. Please try again.");
                return mv;
            }

        }
        ModelAndView mv = new ModelAndView("redirect:/");
        return mv;
    }

    @RequestMapping(value = "/logout")
    public ModelAndView logoutUser(HttpServletRequest request) 
    {
        logger.info("Logging out");
        request.getSession().invalidate();
        return new ModelAndView("redirect:/");
    }

}