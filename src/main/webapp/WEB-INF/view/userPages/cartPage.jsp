<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.h1.ordered" var="oderedTracksSum" />
<fmt:message bundle="${local}" key="local.h1.emptyCart" var="emptyCartMessage" />
<fmt:message bundle="${local}" key="local.totalAmount" var="totalAmountInfo" />
<fmt:message bundle="${local}" key="local.button.pay" var="pay" />

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-login">
<h1 class="headlines--type">${oderedTracksSum}</h1>

<c:if test="${ empty trackList}">
    <h1 style="margin-left: 45%; margin-top: 40px; margin-bottom: 50px">${emptyCartMessage}</h1>
</c:if>

<c:if test="${not empty trackList}">
    <jsp:include page="../fragments/trackList.jsp"/>
    <br/>
    <div style="margin-left: 46%; margin-bottom: 40px" class="common-label">${totalAmountInfo} ${orderAmount}</div>
    <a href="/musicwebapp/controller?command=payOrder" class="header__link__button" style="margin-left: 45%">${pay}</a>
</c:if>
</div>
<br>
</body>
</html>
