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
<fmt:message bundle="${local}" key="local.messageError.wrongCardNumber" var="wrongCardNumber"/>
<fmt:message bundle="${local}" key="local.messageError.headline.wrongCvvOnCard" var="wrongCvvOnCard"/>
<fmt:message bundle="${local}" key="local.messageError.wrongNameOnCard" var="wrongNameOnCard"/>
<fmt:message bundle="${local}" key="local.messageError.wrongLastnameOnCard" var="wrongLastnameOnCard"/>

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
                <input class="payment-card_sum_input" type="text" pattern="^\d+(.\d{1,2})?$" id="paymentAmount"
                       placeholder="${paymentSum}" name="paymentAmount" required/>
            </div>

            <div class="payment-card_list">
                <div class="payment_label"><label for="cardNumber-1">${cardNumber}</label></div>
                <input class="payment-card_number" type="number" min="0000" max="9999" size="4" pattern="[0-9]{4}"
                       id="cardNumber-1" placeholder="XXXX" name="cardNumberPartOne" required>
                <input class="payment-card_number" type="number" min="0000" max="9999" size="4" pattern="[0-9]{4}"
                       placeholder="XXXX" name="cardNumberPartTwo" required/>
                <input class="payment-card_number" type="number" min="0000" max="9999" size="4" pattern="[0-9]{4}"
                       placeholder="XXXX" name="cardNumberPartThree" required/>
                <input class="payment-card_number" type="number" min="0000" max="9999" size="4" pattern="[0-9]{4}"
                       placeholder="XXXX" name="cardNumberPartFour" required/>
            </div>

            <c:if test="${wrongCardNumber}">
                <p style="color: red; margin: 25px">${wrongCardNumber}!</p>
            </c:if>

            <div class="payment-card_list">
                <div class="payment_label"><label for="nameOnCard">${cardName}</label></div>
                <input class="payment-card_sum_input" type="text" pattern="[A-Z]{,30}" id="nameOnCard"
                       placeholder="name" name="nameOnCard"
                       required/>
                <input class="payment-card_sum_input" type="text" pattern="[A-Z]{,30}" id="lastnameOnCard"
                       placeholder="lastname"
                       name="lastnameOnCard"
                       required/>
            </div>


            <div class="payment-card_list">
                <div class="payment_label"><label for="cvv">CVV:</label></div>
                <input class="payment-card_number" type="number" min="000" max="999" pattern="[0-9]{3}" id="cvv"
                       placeholder="cvv" name="cvv"
                       required/>
            </div>
            <c:if test="${wrongCvvOnCard}">
                <p style="color: red; margin: 25px">${wrongCvvOnCard}!</p>
            </c:if>
            <c:if test="${wrongNameOnCard}">
                <p style="color: red; margin: 25px">${wrongNameOnCard}!</p>
            </c:if>
            <c:if test="${wrongLastnameOnCard}">
                <p style="color: red; margin: 25px">${wrongLastnameOnCard}!</p>
            </c:if>

            <button style="margin-top: 50px;" class="button__center" type="submit">${refillBalance}</button>
        </form>
    </div>
</div>
</body>
</html>