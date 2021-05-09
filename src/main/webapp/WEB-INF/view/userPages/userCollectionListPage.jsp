<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.h1.collections" var="collections" />
<fmt:message bundle="${local}" key="local.h1.sorryInfo" var="sorryInfo" />

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-login">
<jsp:include page="../fragments/search.jsp"/>
<h1 class="headlines">${collections}</h1>

<c:if test="${ empty playlists && empty albums}">
    <h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">${sorryInfo}</h1>
</c:if>

<c:if test="${not empty playlists}">
    <jsp:include page="../fragments/playlists.jsp"/>
</c:if>
<c:if test="${not empty albums}">
    <jsp:include page="../fragments/albums.jsp"/>
</c:if>
</div>

</body>
</html>
