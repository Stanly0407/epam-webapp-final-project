<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.loginName" var="loginName" />
<fmt:message bundle="${local}" key="local.password" var="password" />
<fmt:message bundle="${local}" key="local.placeholder.loginName" var="EnterLogin" />
<fmt:message bundle="${local}" key="local.placeholder.password" var="EnterPassword" />
<fmt:message bundle="${local}" key="local.button.login" var="LogInButton" />

<html>

<body>
<div class="header">
    <jsp:include page="fragments/header.jsp"/>
</div>

<div class="container-login">
    <form action="/musicwebapp/controller?command=login" method="post">
        <div class="common-label"><label for="login">${loginName}</label></div>
        <input class="common-input" type="text" id="login" placeholder="${EnterLogin}" name="login" required/>
        <br/>
        <br/>
        <div class="common-label"><label for="password">${password}</label></div>
        <input class="common-input" type="password" id="password" placeholder="${EnterPassword}" name="password" required>
        <br/>
        <button class="button-main" type="submit">${LogInButton}</button>
    </form>
</div>

<div class="footer">
    <jsp:include page="fragments/footer.jsp"/>
</div>
</body>
</html>
