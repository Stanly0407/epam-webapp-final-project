<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container" align="center">

    <b style="color: aliceblue">${user.name} ${user.lastname} </b><br> <br>

    <b style="color: aliceblue">Login: ${user.login}</b>
    <br><br>
    <a href="/musicwebapp/controller?command=changeUserPassword" class="header__link__button"> Change password</a>
    <br> <br> <br>

    <b style="color: aliceblue">Current balance: $ ${user.balance}</b><br> <br>
    <a href="/musicwebapp/controller?command=paymentHistory" class="header__link__button"> Payment history </a>
    <br> <br>
    <a href="/musicwebapp/controller?command=topUpBalancePage" class="header__link__button"> Top up my balance</a>


</div>

</body>
</html>
