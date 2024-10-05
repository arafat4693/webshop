package com.dslabb1.dslabb1.service;

import com.dslabb1.dslabb1.dao.ProductDAO;
import com.dslabb1.dslabb1.dao.UserDAO;
import com.dslabb1.dslabb1.model.CartItem;
import com.dslabb1.dslabb1.model.Category;
import com.dslabb1.dslabb1.model.Product;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<Product> getAllProducts() throws SQLException {
        return productDAO.allProducts();
    }

    public List<CartItem> getAllCartItems(HashMap<Integer, Integer> cart) throws SQLException {
        return productDAO.allCartItems(cart);
    }

    public List<Category> getCategories() throws SQLException {
        return productDAO.getAllCategories();
    }

    public void addProduct(String name, double price, int stock, int categoryId) throws SQLException {
        productDAO.addProduct(name, price, stock, categoryId);
    }

    public Product getSingleProduct(int productId) throws SQLException {
        return productDAO.getProduct(productId);
    }

    public void updateProduct(int productId, String name, double price, int stock, int categoryId) throws SQLException {
        productDAO.editProduct(productId, name, price, stock, categoryId);
    }

    public void addNewCategory(String name) throws SQLException {
        productDAO.addCategory(name);
    }

    public Category getSingleCategory(int id) throws SQLException {
        return productDAO.getCategoryById(id);
    }

    public void updateCategory(int id, String name) throws SQLException {
        productDAO.editCategory(id, name);
    }
}
