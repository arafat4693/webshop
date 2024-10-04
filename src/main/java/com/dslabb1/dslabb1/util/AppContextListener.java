package com.dslabb1.dslabb1.util;

import com.dslabb1.dslabb1.dao.ProductDAO;
import com.dslabb1.dslabb1.dao.UserDAO;
import com.dslabb1.dslabb1.service.ProductService;
import com.dslabb1.dslabb1.service.UserService;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

import java.sql.Connection;
import java.sql.DriverManager;

@WebListener
public class AppContextListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();

        try {
            String url = "jdbc:mysql://localhost:3306/webshop";
            String username = "root";
            String password = "arafat12#";
            Class.forName("com.mysql.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, username, password);

            UserDAO userDAO = new UserDAO(connection);
            ProductDAO productDAO = new ProductDAO(connection);
            // OrderDAO orderDAO = new OrderDAO(connection);

            UserService userService = new UserService(userDAO);
            ProductService productService = new ProductService(productDAO);
            // OrderService orderService = new OrderService(orderDAO, productDAO);

            context.setAttribute("userService", userService);
            context.setAttribute("test", "userService");
            context.setAttribute("productService", productService);
            // context.setAttribute("orderService", orderService);

            System.out.println("Webshop application initialized.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
