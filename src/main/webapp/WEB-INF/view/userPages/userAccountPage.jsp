<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container-login">

    <h1 class="user_headline">${user.name} ${user.lastname} </h1><br> <br>

    <b style="color: aliceblue">Login: ${user.login}</b>
    <br><br>
    <form action="/musicwebapp/controller?command=changePassword" method="post">
        <button  class="header__link__button" type="submit">Change password</button>
        <input class="common-input" type="password" placeholder="new password..." name="password" required/>

    </form>

    <br> <br> <br>

    <b style="color: aliceblue">Current balance: $ ${user.balance}</b><br> <br>
    <a href="/musicwebapp/controller?command=paymentHistory" class="header__link__button"> Payment history </a>
    <br> <br>
    <a href="/musicwebapp/controller?command=topUpBalancePage" class="header__link__button"> Refill account balance</a>


</div>

</body>
</html>
