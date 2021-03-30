<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="fragments/header.jsp"/>
</div>

<div class="container-login">
    <form action="/musicwebapp/controller?command=login" method="post">

        <div class="common-label"><label for="login">Login</label></div>
        <input class="common-input" type="text" id="login" placeholder="Enter Login" name="login" required/>
        <br/><br/>

        <div class="common-label"><label for="password">Password</label></div>
        <input class="common-input" type="password" id="password" placeholder="Enter Password" name="password" required>
        <br/>
        <button class="button-main" type="submit">Login</button>
    </form>
</div>


</body>
</html>
