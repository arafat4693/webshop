package com.dslabb1.dslabb1.dao;

import com.dslabb1.dslabb1.model.CartItem;
import com.dslabb1.dslabb1.model.Product;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductDAO extends BaseDAO{
    public ProductDAO(Connection connection) {
        super(connection);
    }

    public List<Product> allProducts() throws SQLException {
        List<Product> products = new ArrayList<>();
        String query = "SELECT p.id, p.name, p.price, p.stock, c.name as category FROM products p JOIN categories c ON p.category_id = c.id";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("id");
                int stock = rs.getInt("stock");
                double price = rs.getDouble("price");
                String name = rs.getString("name");
                String category = rs.getString("category");

                Product product = new Product(id, name, price, category, stock);
                products.add(product);
            }
        }

        return products;
    }

    public List<CartItem> allCartItems(HashMap<Integer, Integer> cart) throws SQLException {
        List<CartItem> cartItems = new ArrayList<>();

        for (HashMap.Entry<Integer, Integer> entry : cart.entrySet()) {
            int productId = entry.getKey();
            int quantity = entry.getValue();

            String query = "SELECT * FROM products WHERE id = ?";

            try(PreparedStatement stmt = connection.prepareStatement(query)){
                stmt.setInt(1, productId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    double price = rs.getDouble("price");
                    cartItems.add(new CartItem(rs.getString("name"), price, quantity));
                }
            }
        }

        return cartItems;
    }
}
