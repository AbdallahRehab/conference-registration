package com.example.conferenceregistration;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.*;

@WebServlet(value = "/register")
public class RegisterServlet extends HttpServlet {
    @Serial
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");
        String phone = request.getParameter("phone");
        String organization = request.getParameter("organization");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        String jdbcURL = "jdbc:mysql://localhost:3306/conference_db";
        String dbUser = "root";
        String dbPassword = "password";

        try {
            Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);
            String sql = "INSERT INTO participants (name, email, phone, organization) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, name);
            statement.setString(2, email);
            statement.setString(3, phone);
            statement.setString(4, organization);

            int rows = statement.executeUpdate();
            PrintWriter out = response.getWriter();
            response.setContentType("text/html");
            if (rows > 0) {
                out.println("<h3>Registration Successful!</h3>");
                out.println("<p>Thank you for registering, " + name + ".</p>");
            } else {
                out.println("<h3>Registration Failed!</h3>");
            }
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}