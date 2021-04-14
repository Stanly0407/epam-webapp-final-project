<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container-login">
    <h1 align="center" style="margin-bottom: 35px">Edit track: </h1>

    <form action="/musicwebapp/controller?command=topUpBalance" method="post">
        <%--    <input type="hidden" name="command" value="editSaveTrack">--%>
        <table>
            <tr>
                <td>PAYMENT AMOUNT: $</td>
                <td><input class="common-input" type="text" name="paymentAmount"/></td>
                <br>
            </tr>
            <tr>
                <td>Credit Card Number: </td>
                <td>  <input width="20px" class="common-input" type="tel" placeholder="xxxx" required></td>
                <td>  <input width="20px" class="common-input" type="tel" placeholder="xxxx" required></td>
                <td>  <input width="20px" class="common-input" type="tel" placeholder="xxxx" required></td>
                <td>  <input width="20px" class="common-input" type="tel" placeholder="xxxx" required></td>
            </tr>
            <tr>
                <td>Name on Card:</td>
                <td><input class="common-input" type="text" name="nameOnCard" required/></td>
            </tr>
            <tr>
                <td>Exp Month/Year: </td>
                <td><input class="common-input" type="text" name="month" required/> /</td>
                <td><input class="common-input" type="text" name="year" required/></td>
            </tr>
            <tr>
                <td>CVV:</td>
                <td><input class="common-input" type="text" pattern="{3}" name="cvv" required/></td>
            </tr>
        </table>
        <button class="button__center" type="submit">Pay</button>
    </form>


</div>
</body>
</html>