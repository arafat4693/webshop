package com.dslabb1.dslabb1.controller;

import com.dslabb1.dslabb1.model.Order;
import com.dslabb1.dslabb1.service.OrderService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/worker/manageOrders")
public class ManageOrdersServlet extends HttpServlet {
    private OrderService orderService;

    @Override
    public void init() {
        orderService = (OrderService) getServletContext().getAttribute("orderService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Order> orders = orderService.getAllOrders();
            request.setAttribute("orders", orders);
            request.getRequestDispatcher("/WEB-INF/views/manageOrders.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error fetching orders.");
            request.getRequestDispatcher("/WEB-INF/views/manageOrders.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int orderId = Integer.parseInt(request.getParameter("orderId"));

        try{
            orderService.packOrder(orderId);
            response.sendRedirect(request.getContextPath() + "/worker/manageOrders");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error packing the order.");
            request.getRequestDispatcher("/WEB-INF/views/manageOrders.jsp").forward(request, response);
        }
    }
}
