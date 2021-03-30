<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="WEB-INF/view/fragments/header.jsp"/>
</div>

<div style="color: red" >
    <c:out value="Error: ${errorMessage}"/>


</div>
</body>
</html>
