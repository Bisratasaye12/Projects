package com.trading.hibretstock.Services;

import java.util.List;

import com.trading.hibretstock.Dao.AdminDao;
import com.trading.hibretstock.Models.User;
import com.trading.hibretstock.Utils.FileReaderUtil;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminSvc
{
    @Autowired
    private AdminDao adminDAO;

    private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);

    public void updateServiceCharge (String val)
    {
        FileReaderUtil.writeServiceChargeValue(val);
    }

    public List<User> getUnverifiedUsers ()
    {
        return this.adminDAO.getUnverifiedUsers();
    }

    public boolean sendEmail (String[] emails)
    {
        for (String em : emails)
        {
            try {
                sendEmail(em);
                this.adminDAO.verifyUsers(em);
            } catch (Exception e) 
            {
                logger.error(e.toString());
                return false;
            }
        }

        for (String em : emails)
        {
            User u  = this.adminDAO.getProfileAttributes(em);
            logger.info("verification status::"+u.isVerified());
        }
        return true;
    }

    private void sendEmail(String emailId) throws EmailException {
        HtmlEmail email = new HtmlEmail();

        email.setHostName("smtp.googlemail.com");
        email.setSmtpPort(587);  
        email.setSSLOnConnect(true);
        email.setAuthenticator(new DefaultAuthenticator("nahafile@gmail.com", "ljpp bbcr sdyd lokq"));
        
        email.setFrom("nahafile@gmail.com", "Hibret Stock Trading Platform");
        email.setSubject("Thank you for signing up on Hibret Stock Trading Platform.");

        String htmlContent = "<!DOCTYPE html>\r\n"
            + "<html lang=\"en\">\r\n"
            + "<head>\r\n"
            + "    <meta charset=\"UTF-8\">\r\n"
            + "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
            + "    <title>Email Verification</title>\r\n"
            + "    <style>\r\n"
            + "        body {\r\n"
            + "            font-family: Arial, sans-serif;\r\n"
            + "            background-color: #f4f4f4;\r\n"
            + "            margin: 0;\r\n"
            + "            padding: 0;\r\n"
            + "        }\r\n"
            + "        .container {\r\n"
            + "            max-width: 600px;\r\n"
            + "            margin: 50px auto;\r\n"
            + "            background-color: #ffffff;\r\n"
            + "            padding: 20px;\r\n"
            + "            border-radius: 8px;\r\n"
            + "            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);\r\n"
            + "        }\r\n"
            + "        h1 {\r\n"
            + "            color: #333333;\r\n"
            + "        }\r\n"
            + "        p {\r\n"
            + "            color: #555555;\r\n"
            + "        }\r\n"
            + "        a {\r\n"
            + "            display: inline-block;\r\n"
            + "            margin-top: 20px;\r\n"
            + "            padding: 10px 20px;\r\n"
            + "            background-color: #28a745;\r\n"
            + "            color: #ffffff;\r\n"
            + "            text-decoration: none;\r\n"
            + "            border-radius: 4px;\r\n"
            + "        }\r\n"
            + "        a:hover {\r\n"
            + "            background-color: #218838;\r\n"
            + "        }\r\n"
            + "    </style>\r\n"
            + "</head>\r\n"
            + "<body>\r\n"
            + "    <div class=\"container\">\r\n"
            + "        <h1>Hibret Stock</h1>"
            + "        <p>This email is being sent to notify you that your account has now been verified.</p>\r\n"
            + "    </div>\r\n"
            + "</body>\r\n"
            + "</html>";

        email.setHtmlMsg(htmlContent);

        //email.setTextMsg("Your account has been verified.");
        email.addTo(emailId);
        email.send();
    }
}