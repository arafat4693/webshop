package com.dslabb1.dslabb1.controller.admin;

import com.dslabb1.dslabb1.model.Category;
import com.dslabb1.dslabb1.model.Product;
import com.dslabb1.dslabb1.service.ProductService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/admin/products")
public class AdminProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Product> products = productService.getAllProducts();
            List<Category> categories = productService.getCategories();

            // Set products and categories to request scope
            request.setAttribute("products", products);
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/WEB-INF/views/admin/products.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to load products or categories.");
            request.getRequestDispatcher("/WEB-INF/views/admin/products.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int stock = Integer.parseInt(request.getParameter("stock"));
            int categoryId = Integer.parseInt(request.getParameter("category_id"));

            productService.addProduct(name, price, stock, categoryId);
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to add product.");
            response.sendRedirect(request.getContextPath() + "/admin/products");
        }
    }
}
