<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Categories</title>
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
                <a href="${pageContext.request.contextPath}/admin/categories" class="text-gray-800 hover:text-gray-600 font-semibold mr-6">Categories</a>
                <a href="${pageContext.request.contextPath}/admin/products" class="text-gray-800 hover:text-gray-600 font-semibold mr-6">Products</a>
            </div>
            <!-- Logout button -->
            <div>
                <a href="${pageContext.request.contextPath}/logout" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded">
                    Logout
                </a>
            </div>
        </div>
    </div>
</nav>

<h1 class="text-2xl text-center mt-10">Manage Categories</h1>

<c:if test="${not empty message}">
    <p class="text-center mt-5 text-red-500">${message}</p>
</c:if>

<div class="container mx-auto mt-8">

    <!-- Form -->
    <h2 class="text-xl mb-4">Add Product</h2>
    <form action="${pageContext.request.contextPath}/admin/categories" method="post" class="mb-8">
        <label for="name" class="block">Category Name</label>
        <input id="name" type="text" name="name" class="border rounded w-full p-2 mb-4" required>

        <button type="submit" class="bg-blue-500 text-white p-2 rounded">Add Category</button>
    </form>

    <!-- Categories List -->
    <h2 class="text-xl mb-4">Products</h2>
    <table class="table-auto w-full">
        <thead>
        <tr>
            <th class="text-left">Name</th>
            <th class="text-left">Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="category" items="${categories}">
            <tr>
                <td>${category.name}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/admin/editCategory?id=${category.id}" class="bg-yellow-500 text-white p-2 rounded inline-block">Edit</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
