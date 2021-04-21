<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.h1.paymentHistory" var="paymentHistory" />
<fmt:message bundle="${local}" key="local.th.paymentDate" var="paymentDate" />
<fmt:message bundle="${local}" key="local.th.tracksAmount" var="tracksAmount" />
<fmt:message bundle="${local}" key="local.th.sum" var="totalSum" />
<fmt:message bundle="${local}" key="local.button.showPurchasedTracks" var="showPurchasedTracks" />

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<h1 class="headlines">${paymentHistory}</h1>

<c:if test="${not empty orders}">
    <div>
        <table style="color: #b3d4fc;">
            <tr>
                <th width="4"></th>
                <th width="200">${paymentDate}</th>
                <th width="120">${tracksAmount}</th>
                <th width="120">${totalSum}</th>
                <th width="80"></th>
            </tr>
            <c:forEach items="${orders}" var="order">
                <tr style="align-content: center">
                    <td><input type="hidden" name="${order.id}"/></td>
                    <td>${order.orderDate}</td>
                    <td>${order.tracksAmount}</td>
                    <td>${order.totalSum}</td>
                    <td><a class="header__link__button" href="<c:url value='controller?command=purchasedTracks&id=${order.id}'/>">
                            ${showPurchasedTracks}</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

</body>
</html>
