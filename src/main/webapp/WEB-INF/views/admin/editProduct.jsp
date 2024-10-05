<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Product</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>

<h1 class="text-2xl text-center mt-10">Edit Product</h1>

<c:if test="${not empty message}">
    <p class="text-center mt-5 text-red-500">${message}</p>
</c:if>

<div class="container mx-auto mt-8">
    <!-- Product Edit Form -->
    <form action="${pageContext.request.contextPath}/admin/editProduct" method="post">
        <input type="hidden" name="id" value="${product.id}">

        <label for="name" class="block">Product Name</label>
        <input id="name" type="text" name="name" value="${product.name}" class="border rounded w-full p-2 mb-4" required>

        <label for="price" class="block">Price</label>
        <input id="price" type="text" name="price" value="${product.price}" class="border rounded w-full p-2 mb-4" required>

        <label for="stock" class="block">Stock</label>
        <input id="stock" type="number" name="stock" value="${product.stock}" class="border rounded w-full p-2 mb-4" required>

        <label for="category_id" class="block">Category</label>
        <select id="category_id" name="category_id" class="border rounded w-full p-2 mb-4">
            <c:forEach var="category" items="${categories}">
                <option value="${category.id}" <c:if test="${category.name == product.category}">selected</c:if>>${category.name}</option>
            </c:forEach>
        </select>

        <button type="submit" class="bg-green-500 text-white p-2 rounded">Save Changes</button>
    </form>

    <a href="products" class="mt-5 inline-block text-blue-500">Back to Product List</a>
</div>

</body>
</html>
