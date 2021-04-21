<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.h1.refill" var="refillInfo" />
<fmt:message bundle="${local}" key="local.h1.paymentAmount" var="paymentAmount" />
<fmt:message bundle="${local}" key="local.h1.cardNumber" var="cardNumber" />
<fmt:message bundle="${local}" key="local.h1.cardName" var="cardName" />
<fmt:message bundle="${local}" key="local.h1.cardExp" var="cardExp" />
<fmt:message bundle="${local}" key="local.button.refillBalance" var="refillBalance" />
<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-payment-card">
    <h1 class="headlines">${refillInfo}</h1>
    <form action="/musicwebapp/controller?command=topUpBalance" method="post">
        <table class="table-payment-card">
            <tr class="payment__td__th">
                <td class="payment__td__th">${paymentAmount}</td>
                <td><input class="payment-input" type="text" name="paymentAmount"/></td>
            </tr>
            <tr class="payment__td__th">
                <td class="payment__td__th">${cardNumber}</td>
                <td class="credit-card-td"><input class="payment-input" type="tel" placeholder="xxxx" required></td>
                <td class="credit-card-td"><input class="payment-input" type="tel" placeholder="xxxx" required></td>
                <td class="credit-card-td"><input class="payment-input" type="tel" placeholder="xxxx" required></td>
                <td class="credit-card-td"><input class="payment-input" type="tel" placeholder="xxxx" required></td>
            </tr>
            <tr class="payment__td__th">
                <td class="payment__td__th">${cardName}</td>
                <td><input class="payment-input-name" type="text" name="nameOnCard" required/></td>
                <td><input class="payment-input-name" type="text" name="lastnameOnCard" required/></td>
            </tr>
            <tr class="payment__td__th">
                <td class="payment__td__th">${cardExp}</td>
                <td><input class="payment-input-date" type="text" name="month" required/> /</td>
                <td><input class="payment-input-date" type="text" name="year" required/></td>
            </tr>
            <tr class="payment__td__th">
                <td class="payment__td__th">CVV:</td>
                <td><input class="payment-input-date" type="text" pattern="{3}" name="cvv" required/></td>
            </tr>
        </table>
        <button class="button__center" type="submit">${refillBalance}</button>
    </form>
</div>
</body>
</html>