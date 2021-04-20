<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<h1 class="headlines">Ordered tracks:</h1>

<c:if test="${ empty trackList}">
    <h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Сart is empty...</h1>
</c:if>

<c:if test="${not empty trackList}">
    <jsp:include page="../fragments/trackList.jsp"/>
    <br/>
    <div style="margin-left: 50%;" class="common-label">Total amount: ${orderAmount}</div>
    <a href="/musicwebapp/controller?command=payOrder" class="header__link__button" style="margin-left: 800px">PAY</a>
</c:if>
</body>
</html>
