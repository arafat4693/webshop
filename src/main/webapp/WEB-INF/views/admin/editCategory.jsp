<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Category</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>

<h1 class="text-2xl text-center mt-10">Edit Product</h1>

<c:if test="${not empty message}">
    <p class="text-center mt-5 text-red-500">${message}</p>
</c:if>

<div class="container mx-auto mt-8">
    <!-- Product Edit Form -->
    <c:if test="${empty message}">
        <form action="${pageContext.request.contextPath}/admin/editCategory" method="post">
            <input type="hidden" name="id" value="${category.id}">

            <label for="name" class="block">Product Name</label>
            <input id="name" type="text" name="name" value="${category.name}" class="border rounded w-full p-2 mb-4" required>

            <button type="submit" class="bg-green-500 text-white p-2 rounded">Save Changes</button>
        </form>
    </c:if>

    <a href="${pageContext.request.contextPath}/admin/categories" class="mt-5 inline-block text-blue-500">Back to Category List</a>
</div>

</body>
</html>
