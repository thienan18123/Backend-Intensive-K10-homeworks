<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${category.name}"/></title>
</head>
<body>

<%-- Back link: to the parent category, or to the homepage for a top-level category --%>
<c:choose>
    <c:when test="${not empty parent}">
        <a href="${ctx}/category?id=${parent.id}">&laquo; <c:out value="${parent.name}"/></a>
    </c:when>
    <c:otherwise>
        <a href="${ctx}/">&laquo; Trang chủ</a>
    </c:otherwise>
</c:choose>

<h1><c:out value="${category.name}"/></h1>

<c:if test="${not empty subCategories}">
    <h2>Chuyên mục con</h2>
    <ul>
        <c:forEach var="sub" items="${subCategories}">
            <li><a href="${ctx}/category?id=${sub.id}"><c:out value="${sub.name}"/></a></li>
        </c:forEach>
    </ul>
</c:if>

<h2>Bài viết</h2>
<c:choose>
    <c:when test="${empty articles}">
        <p>Chưa có bài viết.</p>
    </c:when>
    <c:otherwise>
        <ul>
            <c:forEach var="a" items="${articles}">
                <li>
                    <a href="${ctx}/article?id=${a.id}"><c:out value="${a.title}"/></a>
                    <br><c:out value="${a.summary}"/>
                </li>
            </c:forEach>
        </ul>
    </c:otherwise>
</c:choose>

</body>
</html>