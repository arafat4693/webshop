package com.dslabb1.dslabb1.dao;

import com.dslabb1.dslabb1.model.Order;
import com.dslabb1.dslabb1.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderDAO extends BaseDAO{
    public OrderDAO(Connection connection) {
        super(connection);
    }

    public void saveOrders(int userId, HashMap<Integer, Integer> cart) throws SQLException {
        PreparedStatement orderStmt = null;
        PreparedStatement orderItemStmt = null;
        PreparedStatement updateStockStmt = null;

        try{
            beginTransaction();

            // 1. Insert a new order
            String orderQuery = "INSERT INTO orders (user_id, date) VALUES (?, NOW())";
            orderStmt = connection.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, userId);
            orderStmt.executeUpdate();

            // Get the generated order ID
            ResultSet rs = orderStmt.getGeneratedKeys();
            rs.next();
            int orderId = rs.getInt(1);

            // 2. Insert order items and update product stock
            String orderItemQuery = "INSERT INTO order_items (order_id, product_id, quantity) VALUES (?, ?, ?)";
            String updateStockQuery = "UPDATE products SET stock = stock - ? WHERE id = ? AND stock >= ?";
            orderItemStmt = connection.prepareStatement(orderItemQuery);
            updateStockStmt = connection.prepareStatement(updateStockQuery);

            for (HashMap.Entry<Integer, Integer> entry : cart.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();

                // Insert order item
                orderItemStmt.setInt(1, orderId);
                orderItemStmt.setInt(2, productId);
                orderItemStmt.setInt(3, quantity);
                orderItemStmt.executeUpdate();

                // Update product stock
                updateStockStmt.setInt(1, quantity);
                updateStockStmt.setInt(2, productId);
                updateStockStmt.setInt(3, quantity);  // Ensure stock doesn't go negative
                int updatedRows = updateStockStmt.executeUpdate();

                if (updatedRows == 0) {
                    throw new SQLException("Not enough stock for product ID: " + productId);
                }
            }

            commitTransaction();
        } catch (SQLException e) {
            if (connection != null) {
                try {
                    rollbackTransaction();  // Roll back the transaction on error
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    throw new SQLException();
                }
            }
            e.printStackTrace();
            throw new SQLException();
        } finally {
            // Close resources
            if (orderStmt != null) try { orderStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (orderItemStmt != null) try { orderItemStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
            if (updateStockStmt != null) try { updateStockStmt.close(); } catch (SQLException e) { e.printStackTrace(); }
        }
    }

    public List<Order> getUserOrders(int userId) throws SQLException {
        // Prepare a list to store orders and their items
        List<Order> orders = new ArrayList<>();

        PreparedStatement orderStmt = null;
        PreparedStatement itemStmt = null;

        try {
            // Query to fetch all orders for the user
            String orderQuery = "SELECT * FROM orders WHERE user_id = ? ORDER BY date DESC";
            orderStmt = connection.prepareStatement(orderQuery);
            orderStmt.setInt(1, userId);
            ResultSet ordersRs = orderStmt.executeQuery();

            // Query to fetch order items for each order
            String itemQuery = "SELECT oi.*, p.name, p.price FROM order_items oi JOIN products p ON oi.product_id = p.id WHERE order_id = ?";
            itemStmt = connection.prepareStatement(itemQuery);

            // Process each order
            while (ordersRs.next()) {
                int orderId = ordersRs.getInt("id");
                String status = ordersRs.getString("status");
                Timestamp date = ordersRs.getTimestamp("date");

                // For each order, fetch its items
                itemStmt.setInt(1, orderId);
                ResultSet itemsRs = itemStmt.executeQuery();

                List<OrderItem> orderItems = new ArrayList<>();

                while (itemsRs.next()) {
                    int productId = itemsRs.getInt("product_id");
                    String productName = itemsRs.getString("name");
                    double price = itemsRs.getDouble("price");
                    int quantity = itemsRs.getInt("quantity");

                    // Create OrderItem object and add it to the list
                    OrderItem item = new OrderItem(productId, productName, price, quantity);
                    orderItems.add(item);
                }

                // Create Order object and add it to the list
                Order order = new Order(orderId, date, status, orderItems);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException();
        }finally {
            try {
                if (orderStmt != null) orderStmt.close();
                if (itemStmt != null) itemStmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return orders;
    }

    public List<Order> getAllOrders() throws SQLException {
        String query = "SELECT id, date, status FROM orders WHERE status = 'PENDING'";

        List<Order> orders = new ArrayList<>();

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id"),
                        rs.getTimestamp("date"),
                        rs.getString("status"),
                        new ArrayList<>()
                );
                orders.add(order);
            }
        }

        return orders;
    }

    public void packOrder(int id) throws SQLException {
        String query = "UPDATE orders SET status = 'PACKED' WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
