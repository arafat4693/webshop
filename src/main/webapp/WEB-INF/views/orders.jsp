<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Orders</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>

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
                <a href="order" class="text-gray-800 hover:text-gray-600 font-semibold mr-6">Orders</a>
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

<h1 class="text-2xl text-center mt-10">Your Orders</h1>

<c:if test="${not empty message}">
    <p class="text-center mt-5 text-red-500">${message}</p>
</c:if>

<c:if test="${not empty orders}">
    <div class="container mx-auto mt-8">
        <c:forEach var="order" items="${orders}">
            <div class="border rounded-lg p-4 mb-4">
                <h2 class="text-xl font-bold">Order ID: ${order.id}</h2>
                <p>Date: ${order.date}</p>
                <p>Status: ${order.status}</p>

                <table class="table-auto w-full mt-4">
                    <thead>
                    <tr>
                        <th class="text-left">Product</th>
                        <th class="text-left">Quantity</th>
                        <th class="text-left">Price</th>
                        <th class="text-left">Subtotal</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="item" items="${order.items}">
                        <tr>
                            <td>${item.productName}</td>
                            <td>${item.quantity}</td>
                            <td>$${item.price}</td>
                            <td>$${item.price*item.quantity}</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </c:forEach>
    </div>
</c:if>

<c:if test="${empty orders}">
    <p class="text-center mt-10">You haven't placed any orders yet.</p>
</c:if>
</body>
</html>
