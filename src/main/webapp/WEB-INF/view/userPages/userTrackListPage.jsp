<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.h1.trackList" var="trackListInfo" />
<fmt:message bundle="${local}" key="local.h1.sorryInfo" var="sorryInfo" />

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<jsp:include page="../fragments/search.jsp"/>

<h1 class="headlines">${trackListInfo}</h1>
<c:if test="${empty trackList}">
    <h1 class="message_h1">${sorryInfo}</h1>
</c:if>
<jsp:include page="../fragments/trackList.jsp"/>

</body>
</html>
