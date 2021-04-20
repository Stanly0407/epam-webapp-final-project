<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container-payment-card">
    <h1 class="headlines">Refill balance: </h1>

    <form action="/musicwebapp/controller?command=topUpBalance" method="post">
        <%--    <input type="hidden" name="command" value="editSaveTrack">--%>
        <table class="table-payment-card">
            <tr class="payment__td__th">
                <td class="payment__td__th">PAYMENT AMOUNT: $</td>
                <td><input class="payment-input" type="text" name="paymentAmount"/></td>
            </tr>
            <tr class="payment__td__th">
                <td class="payment__td__th">Credit Card Number:</td>
                <td class="credit-card-td"><input class="payment-input" type="tel" placeholder="xxxx" required></td>
                <td class="credit-card-td"><input class="payment-input" type="tel" placeholder="xxxx" required></td>
                <td class="credit-card-td"><input class="payment-input" type="tel" placeholder="xxxx" required></td>
                <td class="credit-card-td"><input class="payment-input" type="tel" placeholder="xxxx" required></td>
            </tr>
            <tr class="payment__td__th">
                <td class="payment__td__th">Name on Card:</td>
                <td><input class="payment-input-name" type="text" name="nameOnCard" required/></td>
                <td><input class="payment-input-name" type="text" name="lastnameOnCard" required/></td>
            </tr>
            <tr class="payment__td__th">
                <td class="payment__td__th">Exp Month/Year:</td>
                <td><input class="payment-input-date" type="text" name="month" required/> /</td>
                <td><input class="payment-input-date" type="text" name="year" required/></td>
            </tr>
            <tr class="payment__td__th">
                <td class="payment__td__th">CVV:</td>
                <td><input class="payment-input-date" type="text" pattern="{3}" name="cvv" required/></td>
            </tr>
        </table>
        <button class="button__center" type="submit">Pay</button>
    </form>

</div>
</body>
</html>