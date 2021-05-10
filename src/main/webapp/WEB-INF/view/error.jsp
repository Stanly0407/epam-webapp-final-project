<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.messageError.headline.noPermissions" var="noPageHeadline"/>
<fmt:message bundle="${local}" key="local.messageError.noPermissions" var="noPageMessage"/>


<html>
<body>
<div class="header">
    <jsp:include page="fragments/header.jsp"/>
</div>

<c:if test="${noPage}">
    <h1 class="headlines_type" style="color: red">${noPageHeadline}.</h1>
    <p style="color: red; margin-left: 20%;">${noPageMessage}.</p>
</c:if>
<c:if test="${!noPage}">
    <h1 class="headlines_type" style="color: red">Что-то пошло не так. Новая ошибка -> log.</h1>
    <p style="color: red; margin-left: 20%;">${noPageMessage}.</p>
</c:if>

</body>
</html>
