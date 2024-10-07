package com.dslabb1.dslabb1.controller;

import java.io.IOException;

import java.sql.*;

import com.dslabb1.dslabb1.model.User;
import com.dslabb1.dslabb1.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private UserService userService;

    @Override
    public void init() {
        userService = (UserService) getServletContext().getAttribute("userService");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        try{
            UserInfo user = userService.authenticate(username, password);
            if(user != null) {
                HttpSession session = request.getSession();
                session.setAttribute("user", user);
                if ("ADMIN".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/admin");
                }else if ("WORKER".equals(user.getRole())) {
                    response.sendRedirect(request.getContextPath() + "/worker/manageOrders");
                } else{
                    response.sendRedirect(request.getContextPath() + "/products");
                }
            }else {
                request.setAttribute("errorMessage", "Invalid credentials. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
            }
        }catch (SQLException e) {
            request.setAttribute("errorMessage", "An error occurred. Please try again.");
            request.getRequestDispatcher("/WEB-INF/views/login.jsp").forward(request, response);
        }
    }
}
