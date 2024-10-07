package com.dslabb1.dslabb1.service;

import com.dslabb1.dslabb1.controller.CartItemInfo;
import com.dslabb1.dslabb1.controller.CategoryInfo;
import com.dslabb1.dslabb1.dao.ProductDAO;
import com.dslabb1.dslabb1.controller.ProductInfo;
import com.dslabb1.dslabb1.dao.UserDAO;
import com.dslabb1.dslabb1.model.CartItem;
import com.dslabb1.dslabb1.model.Category;
import com.dslabb1.dslabb1.model.Product;

import javax.print.attribute.standard.PrinterURI;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ProductService {
    private final ProductDAO productDAO;

    public ProductService(ProductDAO productDAO) {
        this.productDAO = productDAO;
    }

    public List<ProductInfo> getAllProducts() throws SQLException {
        List<ProductInfo> productInfos = new ArrayList<>();
        List<Product> products = productDAO.allProducts();
        for (Product product : products) {
            productInfos.add(new ProductInfo(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getCategory(),
                    product.getStock()
            ));
        }
        return productInfos;
    }

    public List<CartItemInfo> getAllCartItems(HashMap<Integer, Integer> cart) throws SQLException {
        List<CartItem> cartItems = productDAO.allCartItems(cart);
        List<CartItemInfo> cartItemInfos = new ArrayList<>();
        for(CartItem c : cartItems) cartItemInfos.add(new CartItemInfo(c.getName(), c.getPrice(), c.getQuantity()));
        return cartItemInfos;
    }

    public List<CategoryInfo> getCategories() throws SQLException {
        List<Category> categories = productDAO.getAllCategories();
        List<CategoryInfo> categoryInfos = new ArrayList<>();
        for (Category category : categories) {
            categoryInfos.add(new CategoryInfo(
                    category.getId(),
                    category.getName()
            ));
        }
        return categoryInfos;
    }

    public void addProduct(String name, double price, int stock, int categoryId) throws SQLException {
        productDAO.addProduct(name, price, stock, categoryId);
    }

    public ProductInfo getSingleProduct(int productId) throws SQLException {
        Product product = productDAO.getProduct(productId);
        return new ProductInfo(product.getId(), product.getName(), product.getPrice(), product.getCategory(), product.getStock());
    }

    public void updateProduct(int productId, String name, double price, int stock, int categoryId) throws SQLException {
        productDAO.editProduct(productId, name, price, stock, categoryId);
    }

    public void addNewCategory(String name) throws SQLException {
        productDAO.addCategory(name);
    }

    public CategoryInfo getSingleCategory(int id) throws SQLException {
        Category category = productDAO.getCategoryById(id);
        return new CategoryInfo(category.getId(), category.getName());
    }

    public void updateCategory(int id, String name) throws SQLException {
        productDAO.editCategory(id, name);
    }
}
