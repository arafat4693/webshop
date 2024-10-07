package com.dslabb1.dslabb1.controller.admin;

import com.dslabb1.dslabb1.controller.CategoryInfo;
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

@WebServlet("/admin/editCategory")
public class EditCategoryServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));

        try {
            CategoryInfo category = productService.getSingleCategory(categoryId);
            if(category == null) {
                request.setAttribute("message", "Invalid category Id.");
                request.getRequestDispatcher("/WEB-INF/views/admin/editCategory.jsp").forward(request, response);
            }else {
                request.setAttribute("category", category);
                request.getRequestDispatcher("/WEB-INF/views/admin/editCategory.jsp").forward(request, response);
            }
        }catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error fetching Category details.");
            request.getRequestDispatcher("/WEB-INF/views/admin/editCategory.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int categoryId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");

        try {
            productService.updateCategory(categoryId, name);
            response.sendRedirect(request.getContextPath() + "/admin/categories");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating category/duplicate names.");
            request.getRequestDispatcher("/WEB-INF/views/admin/editCategory.jsp").forward(request, response);
        }
    }
}
