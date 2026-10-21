<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title><c:out value="${article.title}"/></title>
</head>
<body>
<%@ include file="/WEB-INF/views/common/authbar.jspf" %>
<a href="${ctx}/category?id=${category.id}">&laquo; <c:out value="${category.name}"/></a>

<h1><c:out value="${article.title}"/></h1>
<p><b><c:out value="${article.summary}"/></b></p>
<p><c:out value="${article.content}"/></p>

</body>
</html>