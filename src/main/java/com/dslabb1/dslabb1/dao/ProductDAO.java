package com.dslabb1.dslabb1.dao;

import com.dslabb1.dslabb1.model.CartItem;
import com.dslabb1.dslabb1.model.Category;
import com.dslabb1.dslabb1.model.Product;
import com.dslabb1.dslabb1.model.User;

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

    public Product getProduct(int productId) throws SQLException {
        String query = "SELECT p.id, p.name, p.price, p.stock, c.name as category FROM products p JOIN categories c ON p.category_id = c.id WHERE p.id = ?";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getString("category"),
                        rs.getInt("stock")
                );
            }
        }

        return null;
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

    public Category getCategoryByName(String name) throws SQLException {
        String sql = "SELECT * FROM categories WHERE name = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

    public Category getCategoryById(int id) throws SQLException {
        String sql = "SELECT * FROM categories WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Category(rs.getInt("id"), rs.getString("name"));
            }
        }
        return null;
    }

    public List<Category> getAllCategories() throws SQLException {
        String sql = "SELECT * FROM categories";
        List<Category> categories = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Category category = new Category(
                        rs.getInt("id"),
                        rs.getString("name")
                );
                categories.add(category);
            }
        }

        return categories;
    }

    public void addCategory(String name) throws SQLException {
        String query = "INSERT INTO categories (name) VALUES (?)";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.executeUpdate();
        }
    }

    public void editCategory(int id, String name) throws SQLException {
        String query = "UPDATE categories SET name = ? WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setInt(2, id);
            stmt.executeUpdate();
        }
    }

    public void addProduct(String name, double price, int stock, int categoryId) throws SQLException {
        String query = "INSERT INTO products (name, price, stock, category_id) VALUES (?, ?, ?, ?)";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setInt(4, categoryId);

            stmt.executeUpdate();
        }
    }

    public void editProduct(int productId, String name, double price, int stock, int categoryId) throws SQLException {
        String query = "UPDATE products SET name = ?, price = ?, stock = ?, category_id = ? WHERE id = ?";

        try(PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, stock);
            stmt.setInt(4, categoryId);
            stmt.setInt(5, productId);

            stmt.executeUpdate();
        }
    }
}
