package com.myhero;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        try {
            // Database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/mysql", "root", "mohit@77");

            // Query to check credentials
            PreparedStatement ps = con.prepareStatement("SELECT user_type FROM usersnames WHERE email=? AND password=?");
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String userType = rs.getString("user_type");
                if ("user".equals(userType)) {
                    response.sendRedirect("userDashboard.jsp");
                } else if ("startup".equals(userType)) {
                    response.sendRedirect("startupDashboard.jsp");
                }
            } else {
                PrintWriter out = response.getWriter();
                response.setContentType("text/html");
                out.println("<h3>Invalid login credentials. Please try again!</h3>");
                request.getRequestDispatcher("login.jsp").include(request, response);
            }

            con.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
