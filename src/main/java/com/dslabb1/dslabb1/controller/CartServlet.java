package com.dslabb1.dslabb1.controller;

import com.dslabb1.dslabb1.model.CartItem;
import com.dslabb1.dslabb1.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@WebServlet("/cart")
public class CartServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        @SuppressWarnings("unchecked")
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");

        if (cart == null || cart.isEmpty()) {
            request.setAttribute("message", "Your cart is empty.");
        } else {
            try {
                List<CartItem> cartItems = productService.getAllCartItems(cart);
                request.setAttribute("cartItems", cartItems);
                request.setAttribute("totalPrice", calculateTotal(cartItems));
            } catch (SQLException e) {
                request.setAttribute("message", "Sever error. Please try again later");
                e.printStackTrace();
            }
        }

        request.getRequestDispatcher("/WEB-INF/views/cart.jsp").forward(request, response);
    }

    private double calculateTotal(List<CartItem> cartItems) {
        double total = 0;
        for(CartItem c : cartItems) total += c.getPrice()*c.getQuantity();
        return total;
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("productId"));
        int quantity = Integer.parseInt(request.getParameter("quantity"));

        HttpSession session = request.getSession();

        @SuppressWarnings("unchecked")
        HashMap<Integer, Integer> cart = (HashMap<Integer, Integer>) session.getAttribute("cart");

        if (cart == null) {
            cart = new HashMap<>();
        }

        cart.put(productId, cart.getOrDefault(productId, 0) + quantity);

        session.setAttribute("cart", cart);

        response.sendRedirect(request.getContextPath() + "/products");
    }
}
