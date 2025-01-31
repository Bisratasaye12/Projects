package com.trading.hibretstock.Validations;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.trading.hibretstock.Models.User;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class UserValidation implements Validator {

    @Override
    public boolean supports(Class<?> clazz) 
    {
        return User.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) 
    {
        User login = (User) target;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email.required", "Email is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.required", "Password is required.");

        if (errors.hasErrors())
        {
            return;
        }

        boolean verifyPass = this.verifyPassword(login.getPassword());
        if (!verifyPass)
        {
            errors.rejectValue("password", "error.password.required", "Password should minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
        }

    }

    public boolean verifyPassword(String password) 
    {
		boolean isPassword = false;
		
		//Checking if password contains minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character
		Pattern passwordPattern = Pattern.compile("^(?=.*?[A-Z])(?=(.*[a-z]){1,})(?=(.*[\\d]){1,})(?=(.*[\\W_]){1,})(?!.*\\s).{8,}$");
		
		Matcher m = passwordPattern.matcher(password);
		isPassword = m.find();
		
		return isPassword;
	}

}
