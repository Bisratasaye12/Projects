package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Base64;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/loginUser")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String GET_USER_QUERY = "SELECT password FROM users WHERE username = ?";

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

        try (Connection conn = DriverManager.getConnection("jdbc:mysql:///bookregister", "root", "root");
             PreparedStatement ps = conn.prepareStatement(GET_USER_QUERY)) {

            // Check if the user exists in the database
            ps.setString(1, username);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String storedHashedPassword = rs.getString("password");

                    // Verify the hashed password
                    if (verifyPassword(password, storedHashedPassword)) {
                        // Create a session
                        HttpSession session = req.getSession();
                        session.setAttribute("username", username);

                        // Set a session ID in a cookie
                        Cookie sessionCookie = new Cookie("JSESSIONID", session.getId());
                        sessionCookie.setHttpOnly(true); // Secure the cookie
                        sessionCookie.setMaxAge(30 * 60); // Set cookie expiration time (30 minutes)
                        resp.addCookie(sessionCookie);

                        pw.println("<h2>Login successful! Welcome, " + username + ".</h2>");
                        pw.println("<a href='Home.html'>Home</a>");
                    } else {
                        pw.println("<h2>Invalid username or password!</h2>");
                    }
                } else {
                    pw.println("<h2>User not found!</h2>");
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h2>Error: " + se.getMessage() + "</h2>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h2>Error: " + e.getMessage() + "</h2>");
        }
    }

    private boolean verifyPassword(String password, String hashedPassword) throws NoSuchAlgorithmException {
        // Hash the provided password and compare it with the stored hash
        String hashedInput = hashPassword(password);
        return hashedPassword.equals(hashedInput);
    }

    private String hashPassword(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        byte[] hash = md.digest(password.getBytes());
        return Base64.getEncoder().encodeToString(hash);
    }
}
