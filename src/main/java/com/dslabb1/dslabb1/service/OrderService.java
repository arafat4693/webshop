package com.dslabb1.dslabb1.service;

import com.dslabb1.dslabb1.controller.OrderInfo;
import com.dslabb1.dslabb1.dao.OrderDAO;
import com.dslabb1.dslabb1.dao.ProductDAO;
import com.dslabb1.dslabb1.model.Order;

import java.sql.SQLException;
import java.util.ArrayList;
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

    public List<OrderInfo> getOrders(int userId) throws SQLException {
        return convertToOrderInfos(orderDAO.getUserOrders(userId));
    }

    public List<OrderInfo> getAllOrders() throws SQLException {
        return convertToOrderInfos(orderDAO.getAllOrders());
    }

    private List<OrderInfo> convertToOrderInfos(List<Order> orders) {
        List<OrderInfo> orderInfos = new ArrayList<>();
        for (Order order : orders) {
            orderInfos.add( new OrderInfo(
                    order.getId(),
                    order.getDate(),
                    order.getStatus(),
                    order.getItems()
            ));
        }
        return orderInfos;
    }

    public void packOrder(int id) throws SQLException {
        orderDAO.packOrder(id);
    }
}
