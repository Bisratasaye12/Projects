package com.itsc;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/booklist")
public class BookListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String QUERY = "select id, bookname, bookedition, bookprice from books";

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		PrintWriter pw = resp.getWriter();
		resp.setContentType("text/html");

		try {
			// Load the JDBC driver
			Class.forName("com.mysql.cj.jdbc.Driver");

			// Create a database connection
			try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookregister", "root",
					"root"); PreparedStatement ps = conn.prepareStatement(QUERY)) {

				ResultSet rs = ps.executeQuery();
				pw.println("<table border='1'>");
				pw.println("<tr>");
				pw.println("<th>Book Id</th>");
				pw.println("<th>Book Name</th>");
				pw.println("<th>Book Edition</th>");
				pw.println("<th>Book Price</th>");

				pw.println("<th>Edit</th>");
				pw.println("<th>Delete</th>");
				pw.println("</tr>");
				while (rs.next()) {
					pw.println("<tr>");
					pw.println("<td>" + rs.getInt(1) + "</td>");
					pw.println("<td>" + rs.getString(2) + "</td>");
					pw.println("<td>" + rs.getString(3) + "</td>");
					pw.println("<td>" + rs.getFloat(4) + "</td>");

					pw.println("<td><a href ='editurl?id=" + rs.getInt(1) + "'>edit</a></td>");
					pw.println("<td><a href ='deleteurl?id=" + rs.getInt(1) + "'>delete</a></td>");
					pw.println("</tr>");
				}
				pw.println("</table>");
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
		pw.println("<a href='logout'>Logout</a>\n");

	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		doGet(req, resp);
	}
}
