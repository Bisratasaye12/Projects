package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String QUERY = "INSERT INTO books (bookname, bookedition, bookprice) VALUES (?, ?, ?)";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html");
        PrintWriter pw = resp.getWriter();

        // Check session for authentication
        HttpSession session = req.getSession(false); // Do not create a session if none exists
        if (session == null || session.getAttribute("username") == null) {
            // No session or user not logged in
            resp.sendRedirect("login.html");
            return;
        }

        // Fetch parameters from the form
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        try {
            // Load the JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a database connection
            try (Connection conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/bookregister", "root", "root");
                 PreparedStatement ps = conn.prepareStatement(QUERY)) {

                // Set query parameters
                ps.setString(1, bookName);
                ps.setString(2, bookEdition);
                ps.setFloat(3, bookPrice);

                // Execute the query
                int count = ps.executeUpdate();
                if (count == 1) {
                    pw.println("<h2>Book registered successfully.</h2>");
                } else {
                    pw.println("<h2>Book not registered successfully.</h2>");
                }
            }
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h1>Driver not found: " + cnf.getMessage() + "</h1>");
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>SQL Error: " + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
        pw.println("<a href='Home.html'>Home</a>");
        pw.print("<br>");
        pw.println("<a href='booklist'>Book List</a>");
        pw.print("<br>");
        pw.println("<a href='logout'>Logout</a>\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        doGet(req, resp);
    }
}
