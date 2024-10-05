<%--
  Created by IntelliJ IDEA.
  User: arafa
  Date: 2024-10-04
  Time: 16:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Access Denied</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100">

<!-- Main content -->
<div class="flex items-center justify-center h-screen">
    <div class="text-center">
        <h1 class="text-6xl font-bold text-red-600">403</h1>
        <h2 class="text-4xl font-semibold text-gray-800 mt-4">Access Denied</h2>
        <p class="text-gray-600 mt-2">You do not have permission to view this page.</p>
        <div class="mt-8">
            <a href="${pageContext.request.contextPath}/login" class="bg-blue-500 hover:bg-blue-700 text-white font-bold py-2 px-6 rounded">
                Go to Login
            </a>
        </div>
    </div>
</div>

</body>
</html>
