package com.trading.hibretstock.Services;

import com.trading.hibretstock.Dao.UserAcntDao;
import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Models.UserBank;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserAcntSvc
{
    @Autowired
    private UserAcntDao userAccountDAO;

    public User getProfileAttributes (String email)
    {
        return this.userAccountDAO.getProfileAttributes(email);
    }

    public User updateProfile (String  email , String phoneNumber)
    {
        return this.userAccountDAO.updateProfileAttributes(email , phoneNumber);
    }

    public User updateBankDetails (User user, UserBank userbank)
    {
        return this.userAccountDAO.updateBankDetails(user , userbank);
    }
}