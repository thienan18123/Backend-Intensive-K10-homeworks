<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ</title>
</head>
<body>
<%@ include file="/WEB-INF/views/common/authbar.jspf" %>
<h1>Tin tức</h1>

<c:forEach var="cat" items="${categories}">
    <h2><a href="${ctx}/category?id=${cat.id}"><c:out value="${cat.name}"/></a></h2>
    <c:choose>
        <c:when test="${empty cat.articles}">
            <p>Chưa có bài viết.</p>
        </c:when>
        <c:otherwise>
            <ul>
                <c:forEach var="a" items="${cat.articles}">
                    <li>
                        <a href="${ctx}/article?id=${a.id}"><c:out value="${a.title}"/></a>
                        <br><c:out value="${a.summary}"/>
                    </li>
                </c:forEach>
            </ul>
        </c:otherwise>
    </c:choose>
    <hr>
</c:forEach>
</body>

</html>