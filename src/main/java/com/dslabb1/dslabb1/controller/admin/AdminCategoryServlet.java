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

@WebServlet("/admin/categories")
public class AdminCategoryServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            List<Category> categories = productService.getCategories();

            // Set products and categories to request scope
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/WEB-INF/views/admin/categories.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to load categories.");
            request.getRequestDispatcher("/WEB-INF/views/admin/categories.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String name = request.getParameter("name");
            productService.addNewCategory(name);
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Failed to add category/Duplicate category name.");
            request.getRequestDispatcher("/WEB-INF/views/admin/categories.jsp").forward(request, response);
        }
    }
}
