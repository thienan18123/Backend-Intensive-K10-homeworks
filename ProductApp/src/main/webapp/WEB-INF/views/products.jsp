<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c"   uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Product list</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 40px; color: #222; }
        h1 { color: #1a56db; }
        table { border-collapse: collapse; min-width: 500px; }
        th, td { border: 1px solid #ddd; padding: 10px 14px; text-align: left; }
        th { background: #1a56db; color: #fff; }
        tr:nth-child(even) { background: #f5f7fb; }
        .num { text-align: right; }
        .error { color: #b91c1c; }
    </style>
</head>
<body>
    <h1>Product list</h1>

    <c:choose>
        <c:when test="${not empty error}">
            <p class="error"><c:out value="${error}"/></p>
        </c:when>

        <c:when test="${empty products}">
            <p>No products found.</p>
        </c:when>

        <c:otherwise>
            <p>Total: ${products.size()} products</p>
            <table>
                <tr>
                    <th>#</th>
                    <th>Name</th>
                    <th class="num">Price ($)</th>
                    <th class="num">Stock</th>
                </tr>
                <c:forEach var="p" items="${products}" varStatus="row">
                    <tr>
                        <td>${row.index + 1}</td>
                        <td><c:out value="${p.name}"/></td>
                        <td class="num"><fmt:formatNumber value="${p.price}" minFractionDigits="2" maxFractionDigits="2"/></td>
                        <td class="num">${p.stock}</td>
                    </tr>
                </c:forEach>
            </table>
        </c:otherwise>
    </c:choose>
</body>
</html>