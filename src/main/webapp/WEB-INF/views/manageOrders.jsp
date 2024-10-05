<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Worker Orders</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body>
<h1 class="text-2xl text-center mt-10">Orders to Pack</h1>

<c:if test="${not empty message}">
    <p class="text-center mt-5 text-red-500">${message}</p>
</c:if>

<div class="container mx-auto mt-8">
    <table class="table-auto w-full">
        <thead>
        <tr>
            <th class="px-4 py-2 text-left">Order ID</th>
            <th class="px-4 py-2 text-left">Date</th>
            <th class="px-4 py-2 text-left">Status</th>
            <th class="px-4 py-2 text-left">Action</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="order" items="${orders}">
            <tr>
                <td class="border px-4 py-2">${order.id}</td>
                <td class="border px-4 py-2">${order.date}</td>
                <td class="border px-4 py-2">${order.status}</td>
                <td class="border px-4 py-2">
                    <form action="${pageContext.request.contextPath}/worker/manageOrders" method="post">
                        <input type="hidden" name="orderId" value="${order.id}">
                        <button type="submit" class="bg-green-500 text-white px-4 py-2 rounded">Pack</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

</body>
</html>
