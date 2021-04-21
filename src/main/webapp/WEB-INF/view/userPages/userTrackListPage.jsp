<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<jsp:include page="../fragments/search.jsp"/>

<h1 class="headlines">Track List:</h1>
<c:if test="${empty trackList}">
    <h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Sorry, no results found...</h1>
</c:if>
<jsp:include page="../fragments/trackList.jsp"/>

<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
