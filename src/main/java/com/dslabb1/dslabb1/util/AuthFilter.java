package com.dslabb1.dslabb1.util;

import com.dslabb1.dslabb1.model.User;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.*;

/**
 * The AuthFilter class implements a servlet filter used for handling user authentication and
 * authorization in a web application. It checks if a user is authorized to access specific
 * protected URLs based on their role.
 */
@WebFilter("/*")
public class AuthFilter implements Filter {

    private Map<String, List<String>> protectedUrls;

    /**
     * Initializes the filter by defining the protected URLs and their corresponding required roles.
     * This method is called by the servlet container upon initialization.
     *
     * @param filterConfig the filter configuration object provided by the servlet container.
     */
    @Override
    public void init(FilterConfig filterConfig) {
        // Define protected URLs and required roles
        protectedUrls = new HashMap<>();

        // Admin URLs
        protectedUrls.put("/admin/*", List.of("ADMIN"));

        // worker URLs
        protectedUrls.put("/worker/*", List.of("WORKER"));

        // User URLs (logged-in users)
        protectedUrls.put("/products", Arrays.asList("USER", "ADMIN", "WORKER"));
        protectedUrls.put("/cart", List.of("USER"));
        protectedUrls.put("/orders", List.of("USER"));
        // Add other URLs as needed
    }

    /**
     * This method is invoked for each request that passes through the filter. It checks if the
     * requested URL is protected, and if so, whether the user is logged in and has the required role
     * to access the resource. If the user is not logged in or does not have permission, the filter
     * redirects them to the login page or an access denied page.
     *
     * @param request the ServletRequest object that contains the client's request.
     * @param response the ServletResponse object that contains the filter's response.
     * @param chain the FilterChain object used to invoke the next filter or resource in the chain.
     * @throws IOException if an I/O error occurs during the filter operation.
     * @throws ServletException if a servlet-related error occurs during the filter operation.
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        String path = req.getServletPath();

        // Check if the URL is protected
        for (Map.Entry<String, List<String>> entry : protectedUrls.entrySet()) {
            String urlPattern = entry.getKey();
            List<String> roles = entry.getValue();

            if (pathMatches(urlPattern, path)) {
                HttpSession session = req.getSession(false);
                User user = (session != null) ? (User) session.getAttribute("user") : null;

                // If the user is not logged in, redirect to the login page
                if (user == null) {
                    res.sendRedirect(req.getContextPath() + "/login");
                    return;
                }

                // If the user does not have the required role, forward to the access denied page
                if (!roles.contains(user.getRole())) {
                    req.getRequestDispatcher("/WEB-INF/views/accessDenied.jsp").forward(req, res);
                    return;
                }
            }
        }

        // Continue with the filter chain
        chain.doFilter(request, response);
    }

    /**
     * Helper method to determine if the requested path matches the URL pattern. It supports matching
     * patterns that end with "/*" to represent all sub-paths.
     *
     * @param pattern the URL pattern to match (e.g., "/admin/*").
     * @param path the requested path from the client.
     * @return true if the path matches the pattern; false otherwise.
     */
    private boolean pathMatches(String pattern, String path) {
        if (pattern.endsWith("/*")) {
            String basePattern = pattern.substring(0, pattern.length() - 2);
            return path.startsWith(basePattern);
        } else {
            return pattern.equals(path);
        }
    }

    /**
     * This method is called when the filter is being destroyed, allowing for cleanup of resources. In
     * this case, no specific cleanup actions are necessary.
     */
    @Override
    public void destroy() {
        // Clean up resources
    }
}

