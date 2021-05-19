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
<fmt:message bundle="${local}" key="bonus.common.info" var="bonusInfoMessage"/>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-login">
    <div class="account_card">
        <h1 class="user_headline" style="margin-bottom: 30px">${user.name} ${user.lastname} </h1>
        <div style="color: aliceblue; margin-bottom: 30px">
            <b>${loginName}: ${user.login}</b>
        </div>
        <div>
            <div class="bonus_message">
                <c:if test="${bonusMessage}">
                    <h1>${bonusInfoMessage}</h1>
                </c:if>
            </div>
            <div style="color: #CF469D; margin-bottom: 40px">
                <b>${balanceInfo} ${user.balance}</b>
            </div>

            <div style="margin-bottom: 50px;">
                <a href="/musicwebapp/controller?command=refillBalancePage" class="header__link__button">${refill}</a>
            </div>
            <div style="margin-bottom: 50px;">
                <a href="/musicwebapp/controller?command=paymentHistory"
                   class="header__link__button">${paymenttHistory}</a>
            </div>
        </div>
    </div>
</div>
</body>
</html>
