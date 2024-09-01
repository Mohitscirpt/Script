package com.myhero;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

@WebServlet("/RegisterStartupServlet")
public class RegisterStartupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String email = request.getParameter("email");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "mohit@77");

            // Insert into startups table
            PreparedStatement ps = con.prepareStatement("INSERT INTO startupss  (name, email) VALUES (?, ?)");
            ps.setString(1, name);
            ps.setString(2, email);
            int result = ps.executeUpdate();

            if (result > 0) {
                // Also insert into users table for login purpose
                PreparedStatement psUser = con.prepareStatement("INSERT INTO users (email, password, user_type) VALUES (?, ?, ?)");
                psUser.setString(1, email);
                psUser.setString(2, "startup123");  // Default password for demo purposes
                psUser.setString(3, "startup");
                psUser.executeUpdate();

                response.sendRedirect("startupDashboard.jsp");
            } else {
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<h3>Error in registration. Please try again!</h3>");
                request.getRequestDispatcher("registerStartup.jsp").include(request, response);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
