package com.trading.hibretstock.Controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class ErrorHandling {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHandling.class);

    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute("javax.servlet.error.status_code");

        if (status != null) {
            logger.error("Error code: " + status);
        }

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");  
        
        if ("404".equals(status)) {
            model.addAttribute("errorMessage", "Page not found.");
        } else if ("500".equals(status)) {
            model.addAttribute("errorMessage", "Internal server error. Please try again later.");
        } else {
            model.addAttribute("errorMessage", "Oops! Something went wrong, please try again later.");
        }

        return modelAndView;
    }
}