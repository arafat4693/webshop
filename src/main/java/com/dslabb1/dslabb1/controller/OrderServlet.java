package com.dslabb1.dslabb1.controller;

import com.dslabb1.dslabb1.model.Order;
import com.dslabb1.dslabb1.model.OrderItem;
import com.dslabb1.dslabb1.model.User;
import com.dslabb1.dslabb1.service.OrderService;
import com.dslabb1.dslabb1.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/order")
public class OrderServlet extends HttpServlet {
    private ProductService productService;
    private OrderService orderService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute("productService");
        orderService = (OrderService) getServletContext().getAttribute("orderService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");

        try {
            List<OrderInfo> orders = orderService.getOrders(user.getId());
            // Set the orders list in the request scope
            request.setAttribute("orders", orders);
        }catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to load orders.");
        }

        request.getRequestDispatcher("/WEB-INF/views/orders.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");
        User user = (User) session.getAttribute("user");

        if (cart == null || cart.isEmpty()) {
            request.setAttribute("message", "Your cart is empty.");
        }else {
            try {
                orderService.placeOrder(user.getId(), cart);

                // Clear the cart
                session.removeAttribute("cart");

                request.setAttribute("message", "Your order has been placed successfully!");
                request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            }catch (SQLException e) {
                request.setAttribute("message", "Failed to place the order. Please try again.");
                request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
            }
        }
    }
}
