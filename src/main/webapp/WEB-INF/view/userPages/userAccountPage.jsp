<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>
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
    <h1 class="user_headline">${user.name} ${user.lastname} </h1><br> <br>
    <b style="color: aliceblue">${loginName}: ${user.login}</b>
    <br>
    <br>
    <div>
        <b style="color: aliceblue">${balanceInfo} ${user.balance}</b><br> <br>

            <div style="margin-top: 50px;">
                <a href="/musicwebapp/controller?command=refillBalancePage" class="header__link__button">${refill}</a>
            </div>
            <a href="/musicwebapp/controller?command=paymentHistory" class="header__link__button">${paymenttHistory}</a>
        <br>
    </div>
</div>
</body>
</html>
