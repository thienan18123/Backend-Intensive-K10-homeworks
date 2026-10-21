<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Register</title>
</head>
<body>
<%@ include file="/WEB-INF/views/common/authbar.jspf" %>

<h1>Register</h1>

<c:if test="${not empty error}">
    <p style="color:red"><c:out value="${error}"/></p>
</c:if>

<form method="post" action="${ctx}/register">
    <p>
        <label>Name<br>
            <input type="text" name="name" value="<c:out value='${name}'/>" maxlength="100" required>
        </label>
    </p>
    <p>
        <label>ID<br>
            <input type="text" name="loginId" value="<c:out value='${loginId}'/>" maxlength="50" required>
        </label>
    </p>
    <p>
        <label>Password<br>
            <input type="password" name="password" minlength="8" required>
        </label>
    </p>
    <button type="submit">Register</button>
</form>

<p>Already have an account? <a href="${ctx}/login">Login</a></p>
<p><a href="${ctx}/home">&laquo; Home</a></p>
</body>
</html>