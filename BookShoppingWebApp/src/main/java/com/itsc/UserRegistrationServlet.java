package com.itsc;


import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerUser")
public class UserRegistrationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String INSERT_USER_QUERY = "INSERT INTO users (username, password) VALUES (?, ?)";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Set response content type
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        // Retrieve user inputs
        String username = req.getParameter("username");
        String password = req.getParameter("password");

        // Input validation
        if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
            pw.println("<h2>Username and password are required!</h2>");
            return;
        }
        
     // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h1>Driver Not Found: " + cnf.getMessage() + "</h1>");
            return;
        }

        try {
            // Hash the password
            String hashedPassword = hashPassword(password);

            // Insert into the database
            try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookregister", "root", "root");
                 PreparedStatement ps = conn.prepareStatement(INSERT_USER_QUERY)) {
                 
                ps.setString(1, username);
                ps.setString(2, hashedPassword);
                int result = ps.executeUpdate();

                if (result == 1) {
                    pw.println("<h2>User registered successfully!</h2>");
                } else {
                    pw.println("<h2>Failed to register user. Try again.</h2>");
                }
            } catch (SQLException se) {
                se.printStackTrace();
                pw.println("<h2>Error: " + se.getMessage() + "</h2>");
            }
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            pw.println("<h2>Error while hashing password: " + e.getMessage() + "</h2>");
        }

        // Add a link to go back to the registration page
        pw.println("<a href='register.html'>Back to Registration</a>");
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
