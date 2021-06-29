<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.loginName" var="loginName"/>
<fmt:message bundle="${local}" key="local.password" var="password"/>
<fmt:message bundle="${local}" key="local.placeholder.loginName" var="EnterLogin"/>
<fmt:message bundle="${local}" key="local.placeholder.password" var="EnterPassword"/>
<fmt:message bundle="${local}" key="local.button.login" var="LogInButton"/>
<fmt:message bundle="${local}" key="local.messageError.unknownUser" var="messageUnknownUser"/>
<fmt:message bundle="${local}" key="local.messageError.noRole" var="noRoleUser"/>
<fmt:message bundle="${local}" key="local.messageError.headline.blockedUser" var="blockedUserHeadline"/>

<html>

<body>
<div class="header">
    <jsp:include page="fragments/header.jsp"/>
</div>

<div class="container-login">
    <c:if test="${noRole}">
        <p style="color: red">${noRoleUser}...</p>
    </c:if>
    <c:if test="${unknownUser}">
        <p style="color: red">${messageUnknownUser}...</p>
    </c:if>
    <c:if test="${blockedUser}">
        <h1 class="headlines_type" style="color: red">${blockedUserHeadline}.</h1>
    </c:if>
    <br/>

    <form action="/musicwebapp/controller?command=login" method="post">
        <div class="common-label"><label for="login">${loginName}</label></div>
        <input class="common-input" type="text" id="login" placeholder="${EnterLogin}" name="login" required/>
        <br/>
        <br/>
        <div class="common-label"><label for="password">${password}</label></div>
        <input class="common-input" type="password" id="password" placeholder="${EnterPassword}" name="password"
               required>
        <br/>
        <button class="button-main" type="submit">${LogInButton}</button>
    </form>
</div>


</body>
</html>
