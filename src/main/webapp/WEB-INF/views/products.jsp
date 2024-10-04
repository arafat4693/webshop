<%@ page import="com.dslabb1.dslabb1.model.Product" %>
<%@ page import="java.util.List" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Products - Webshop</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">

<!-- Navbar -->
<nav class="bg-white shadow-lg">
    <div class="max-w-7xl mx-auto px-4">
        <div class="flex justify-between items-center py-4">
            <!-- Logo -->
            <div class="text-2xl font-bold text-gray-800">
                Webshop
            </div>
            <!-- Navigation links -->
            <div>
                <a href="cart" class="text-gray-800 hover:text-gray-600 font-semibold mr-6">Cart</a>
                <a href="#" class="text-gray-800 hover:text-gray-600 font-semibold mr-6">Orders</a>
                <a href="products" class="text-gray-800 hover:text-gray-600 font-semibold mr-6">Products</a>
            </div>
            <!-- Logout button -->
            <div>
                <a href="logout" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded">
                    Logout
                </a>
            </div>
        </div>
    </div>
</nav>

<!-- Main content -->
<div class="max-w-7xl mx-auto py-12">
    <h1 class="text-4xl font-bold text-gray-800 mb-6 text-center">Products</h1>

    <!-- Products Grid -->
    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
        <c:forEach var="product" items="${products}">
            <div class="bg-white shadow-md rounded-lg overflow-hidden">
                <div class="p-6">
                    <h2 class="text-lg font-bold text-gray-800">${product.name}</h2>
                    <p class="text-gray-600">Category: ${product.category}</p>
                    <div class="mt-4">
                        <span class="text-xl font-bold text-gray-800">$${product.price}</span>
                    </div>
                    <p class="text-gray-600 mt-2 mb-2">Stock: ${product.stock}</p>
                    <form action="cart" method="post">
                        <input type="hidden" name="productId" value="${product.id}">
                        <label>Quantity: <input type="number" name="quantity" min="1" max="${product.stock}" value="1"></label>
                        <button type="submit" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-4 rounded block mt-2">Add to Cart</button>
                    </form>
                </div>
            </div>
        </c:forEach>
    </div>
</div>

</body>
</html>
