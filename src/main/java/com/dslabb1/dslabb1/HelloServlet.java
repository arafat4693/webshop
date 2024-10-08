package com.dslabb1.dslabb1;

import java.io.*;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        response.setContentType("text/html");

        // Hello
        String test = (String) getServletContext().getAttribute("test");
        System.out.println(test);
        request.setAttribute("products", "hello product");
        request.getRequestDispatcher("/WEB-INF/views/test.jsp").forward(request, response);
    }

    public void destroy() {
    }
}