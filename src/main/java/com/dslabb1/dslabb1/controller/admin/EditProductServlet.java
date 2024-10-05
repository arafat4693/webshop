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
import java.util.ArrayList;
import java.util.List;

@WebServlet("/admin/editProduct")
public class EditProductServlet extends HttpServlet {
    private ProductService productService;

    @Override
    public void init() {
        productService = (ProductService) getServletContext().getAttribute("productService");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));

        try {
            Product product = productService.getSingleProduct(productId);
            if(product == null) {
                request.setAttribute("message", "Invalid product Id.");
                request.getRequestDispatcher("/WEB-INF/views/admin/editProduct.jsp").forward(request, response);
            }

            List<Category> categories = productService.getCategories();

            request.setAttribute("product", product);
            request.setAttribute("categories", categories);

            request.getRequestDispatcher("/WEB-INF/views/admin/editProduct.jsp").forward(request, response);
        }catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error fetching product details.");
            request.getRequestDispatcher("/WEB-INF/views/admin/editProduct.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int productId = Integer.parseInt(request.getParameter("id"));
        String name = request.getParameter("name");
        double price = Double.parseDouble(request.getParameter("price"));
        int stock = Integer.parseInt(request.getParameter("stock"));
        int categoryId = Integer.parseInt(request.getParameter("category_id"));

        try {
            productService.updateProduct(productId, name, price, stock, categoryId);
            response.sendRedirect(request.getContextPath() + "/admin/products");
        } catch (SQLException e) {
            e.printStackTrace();
            request.setAttribute("message", "Error updating product.");
            request.getRequestDispatcher("/WEB-INF/views/admin/editProduct.jsp").forward(request, response);
        }
    }
}
