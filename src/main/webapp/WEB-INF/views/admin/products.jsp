<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Products</title>
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
                <a href="" class="text-gray-800 hover:text-gray-600 font-semibold mr-6">Categories</a>
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

    <h1 class="text-2xl text-center mt-10">Manage Products</h1>

    <c:if test="${not empty message}">
        <p class="text-center mt-5 text-red-500">${message}</p>
    </c:if>

    <div class="container mx-auto mt-8">

        <!-- Product Form -->
        <h2 class="text-xl mb-4">Add Product</h2>
        <form action="${pageContext.request.contextPath}/admin/products" method="post" class="mb-8">
            <input type="hidden" name="action" value="addProduct">

            <label for="name" class="block">Product Name</label>
            <input id="name" type="text" name="name" class="border rounded w-full p-2 mb-4" required>

            <label for="price" class="block">Price</label>
            <input id="price" type="text" name="price" class="border rounded w-full p-2 mb-4" required>

            <label for="stock" class="block">Stock</label>
            <input id="stock" type="number" name="stock" class="border rounded w-full p-2 mb-4" required>

            <label for="category_id" class="block">Category</label>
            <select id="category_id" name="category_id" class="border rounded w-full p-2 mb-4">
                <c:forEach var="category" items="${categories}">
                    <option value="${category.id}">${category.name}</option>
                </c:forEach>
            </select>

            <button type="submit" class="bg-blue-500 text-white p-2 rounded">Add Product</button>
        </form>

        <!-- Products List -->
        <h2 class="text-xl mb-4">Products</h2>
        <table class="table-auto w-full">
            <thead>
            <tr>
                <th class="text-left">Name</th>
                <th class="text-left">Price</th>
                <th class="text-left">Stock</th>
                <th class="text-left">Category</th>
                <th class="text-left">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="product" items="${products}">
                <tr>
                    <td>${product.name}</td>
                    <td>${product.price}</td>
                    <td>${product.stock}</td>
                    <td>${product.category}</td>
                    <td>
                        <%-- <form action="${pageContext.request.contextPath}/admin/editProduct" method="get">
                            <input type="hidden" name="id" value="${product.id}">
                            <input type="hidden" name="action" value="editProduct">
                            <button type="submit" class="bg-yellow-500 text-white p-2 rounded">Edit</button>
                        </form> --%>
                        <a href="${pageContext.request.contextPath}/admin/editProduct?id=${product.id}" class="bg-yellow-500 text-white p-2 rounded inline-block">Edit</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
</body>
</html>
