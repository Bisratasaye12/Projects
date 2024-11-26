package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/editurl")
public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String FETCH_QUERY = "SELECT bookname, bookedition, bookprice FROM books WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE books SET bookname=?, bookedition=?, bookprice=? WHERE id = ?";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        // Get the id from the request
        int id = Integer.parseInt(req.getParameter("id"));

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h1>Driver Not Found: " + cnf.getMessage() + "</h1>");
            return;
        }

        // Generate the connection and fetch the record
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookregister", "root", "root");
             PreparedStatement ps = conn.prepareStatement(FETCH_QUERY)) {

            ps.setInt(1, id);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Fetch the existing data
                    String bookName = rs.getString("bookname");
                    String bookEdition = rs.getString("bookedition");
                    float bookPrice = rs.getFloat("bookprice");

                    // Generate the edit form pre-filled with data
                    pw.println("<h1>Edit Book Details</h1>");
                    pw.println("<form action='editurl' method='post'>");
                    pw.println("<input type='hidden' name='id' value='" + id + "'/>");
                    pw.println("<label for='bookName'>Book Name:</label>");
                    pw.println("<input type='text' name='bookName' value='" + bookName + "' required/><br><br>");
                    pw.println("<label for='bookEdition'>Book Edition:</label>");
                    pw.println("<input type='text' name='bookEdition' value='" + bookEdition + "' required/><br><br>");
                    pw.println("<label for='bookPrice'>Book Price:</label>");
                    pw.println("<input type='number' step='0.01' name='bookPrice' value='" + bookPrice + "' required/><br><br>");
                    pw.println("<button type='submit'>Update</button>");
                    pw.println("</form>");
                } else {
                    pw.println("<h1>No record found with ID " + id + ".</h1>");
                }
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>SQL Error: " + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>Error: " + e.getMessage() + "</h1>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter pw = resp.getWriter();
        resp.setContentType("text/html");

        // Get the updated values from the form
        int id = Integer.parseInt(req.getParameter("id"));
        String bookName = req.getParameter("bookName");
        String bookEdition = req.getParameter("bookEdition");
        float bookPrice = Float.parseFloat(req.getParameter("bookPrice"));

        // Load the JDBC driver
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException cnf) {
            cnf.printStackTrace();
            pw.println("<h1>Driver Not Found: " + cnf.getMessage() + "</h1>");
            return;
        }

        // Perform the update
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookregister", "root", "root");
             PreparedStatement ps = conn.prepareStatement(UPDATE_QUERY)) {

            ps.setString(1, bookName);
            ps.setString(2, bookEdition);
            ps.setFloat(3, bookPrice);
            ps.setInt(4, id);

            int count = ps.executeUpdate();

            if (count == 1) {
                pw.println("<h2>Record updated successfully.</h2>");
            } else {
                pw.println("<h2>Failed to update record.</h2>");
            }
        } catch (SQLException se) {
            se.printStackTrace();
            pw.println("<h1>SQL Error: " + se.getMessage() + "</h1>");
        } catch (Exception e) {
            e.printStackTrace();
            pw.println("<h1>Error: " + e.getMessage() + "</h1>");
        }

        // Add navigation links
        pw.println("<a href='Home.html'>Home</a><br>");
        pw.println("<a href='booklist'>Book List</a>");
        pw.print("<br>");
        pw.println("<a href='logout'>Logout</a>\n");
    }
}
