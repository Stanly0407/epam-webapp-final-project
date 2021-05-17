<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.loginName" var="loginName"/>
<fmt:message bundle="${local}" key="local.h1.balance" var="balanceInfo"/>
<fmt:message bundle="${local}" key="local.h1.refill" var="refill"/>
<fmt:message bundle="${local}" key="local.h1.paymentHistory" var="paymenttHistory"/>
<fmt:message bundle="${local}" key="local.h1.changePassword" var="changePassword"/>
<fmt:message bundle="${local}" key="local.placeholder.newPassword" var="newPassword"/>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-login">
    <h1 class="user_headline" style="margin-bottom: 30px">${user.name} ${user.lastname} </h1>
    <div style="color: aliceblue; margin-bottom: 70px">
    <b >${loginName}: ${user.login}</b>
    </div>
    <div>
        <b style="color: #CF469D;">${balanceInfo} ${user.balance}</b><br> <br>

        <c:if test="${bonusMessage}">
            <h1 class="message_h1">У вас есть бонусы, закажите треки, чтобы узнать подробнее.</h1>
        </c:if>

        <div style="margin-bottom: 50px;">
            <a href="/musicwebapp/controller?command=refillBalancePage" class="header__link__button">${refill}</a>
        </div>
        <div style="margin-bottom: 50px;">
            <a href="/musicwebapp/controller?command=paymentHistory" class="header__link__button">${paymenttHistory}</a>
        </div>

    </div>
</div>
</body>
</html>
