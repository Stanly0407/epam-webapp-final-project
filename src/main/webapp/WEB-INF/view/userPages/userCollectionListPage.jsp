<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-login">
<jsp:include page="../fragments/search.jsp"/>

<h1 class="headlines">COLLECTIONS:</h1>

<c:if test="${ empty playlists && empty albums}">
    <h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Sorry, no results found...</h1>
</c:if>

<c:if test="${not empty playlists}">
    <jsp:include page="../fragments/playlists.jsp"/>
</c:if>
<c:if test="${not empty albums}">
    <jsp:include page="../fragments/albums.jsp"/>
</c:if>
</div>

<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
