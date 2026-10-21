<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Login</title>
</head>
<body>
<%@ include file="/WEB-INF/views/common/authbar.jspf" %>

<h1>Login</h1>

<c:if test="${param.registered == '1'}">
    <p style="color:green">Registration successful. Please log in.</p>
</c:if>
<c:if test="${not empty error}">
    <p style="color:red"><c:out value="${error}"/></p>
</c:if>

<form method="post" action="${ctx}/login">
    <p>
        <label>ID<br>
            <input type="text" name="loginId" value="<c:out value='${loginId}'/>" required>
        </label>
    </p>
    <p>
        <label>Password<br>
            <input type="password" name="password" required>
        </label>
    </p>
    <button type="submit">Login</button>
</form>

<p>No account yet? <a href="${ctx}/register">Register</a></p>
<p><a href="${ctx}/home">&laquo; Home</a></p>
</body>
</html>