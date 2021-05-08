<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.h1.refill" var="refillInfo"/>
<fmt:message bundle="${local}" key="local.h1.paymentAmount" var="paymentAmount"/>
<fmt:message bundle="${local}" key="local.h1.cardNumber" var="cardNumber"/>
<fmt:message bundle="${local}" key="local.h1.cardName" var="cardName"/>
<fmt:message bundle="${local}" key="local.h1.cardExp" var="cardExp"/>
<fmt:message bundle="${local}" key="local.button.refillBalance" var="refillBalance"/>
<fmt:message bundle="${local}" key="local.placeholder.paymentAmount" var="paymentSum"/>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-payment-card">
    <h1 class="headlines_type-2">${refillInfo}:</h1>

    <div class="payment-card">
        <form action="/musicwebapp/controller?command=refillBalance" method="post">
            <div class="payment-card_list">
                <div class="payment_label"><label for="paymentAmount">${paymentAmount}</label></div>
                <input class="payment-card_sum_input" type="text" id="paymentAmount" placeholder="${paymentSum}"
                       name="paymentAmount" required/>
            </div>

            <div class="payment-card_list">
                <div class="payment_label"><label for="cardNumber-1">${cardNumber}</label></div>
                <input class="payment-card_number" type="text" id="cardNumber-1" placeholder="XXXX" name="cardNumber">
                <input class="payment-card_number" type="text" placeholder="XXXX" name="cardNumber" required/>
                <input class="payment-card_number" type="text" placeholder="XXXX" name="cardNumber" required/>
                <input class="payment-card_number" type="text" placeholder="XXXX" name="cardNumber" required/>
            </div>


            <div class="payment-card_list">
                <div class="payment_label"><label for="nameOnCard">${cardName}</label></div>
                <input class="payment-card_sum_input" type="text" id="nameOnCard" placeholder="name" name="nameOnCard"
                       required/>
                <input class="payment-card_sum_input" type="text" id="lastnameOnCard" placeholder="lastname"
                       name="lastnameOnCard"
                       required/>
            </div>

            <div class="payment-card_list">
                <div class="payment_label"><label for="cvv">CVV:</label></div>
                <input class="payment-card_number" type="text" id="cvv" placeholder="cvv" name="cvv"
                       required/>
            </div>

            <button style="margin-top: 50px;" class="button__center" type="submit">${refillBalance}</button>
        </form>
    </div>
</div>
</body>
</html>