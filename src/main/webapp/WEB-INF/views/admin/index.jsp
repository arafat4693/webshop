<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Homepage</title>
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
            <!-- Login/Register buttons -->
            <div>
                <a href="${pageContext.request.contextPath}/logout" class="bg-red-500 hover:bg-red-700 text-white font-bold py-2 px-4 rounded mr-4">
                    Logout
                </a>
            </div>
        </div>
    </div>
</nav>

<!-- Main content -->
<div class="flex items-center justify-center h-screen">
    <div class="text-center">
        <h1 class="text-5xl font-bold text-gray-800">Welcome to Admin Webshop</h1>
        <p class="mt-4 text-gray-600">Create or Edit anything you want</p>
        <div class="mt-8">
            <a href="admin/products" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded mr-4">
                Products
            </a>
            <a href="admin/categories" class="bg-green-500 hover:bg-green-700 text-white font-bold py-2 px-6 rounded">
                Categories
            </a>
        </div>
    </div>
</div>

</body>
</html>
