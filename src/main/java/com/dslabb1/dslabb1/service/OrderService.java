package com.dslabb1.dslabb1.service;

import com.dslabb1.dslabb1.dao.OrderDAO;
import com.dslabb1.dslabb1.dao.ProductDAO;
import com.dslabb1.dslabb1.model.Order;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class OrderService {
    private final OrderDAO orderDAO;
    private final ProductDAO productDAO;

    public OrderService(OrderDAO orderDAO, ProductDAO productDAO) {
        this.orderDAO = orderDAO;
        this.productDAO = productDAO;
    }

    public void placeOrder(int userId, HashMap<Integer, Integer> cart) throws SQLException {
        orderDAO.saveOrders(userId, cart);
    }

    public List<Order> getOrders(int userId) throws SQLException {
        return orderDAO.getUserOrders(userId);
    }
}
