<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>
<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Payment history</h1>

<c:if test="${not empty orders}">
    <div>
        <table style="color: #b3d4fc;">
            <tr>
                <th width="4"></th>
                <th width="200">Date of payment</th>
                <th width="120">Tracks amount</th>
                <th width="120">Total sum</th>
                <th width="80"></th>

            </tr>
            <c:forEach items="${orders}" var="order">
                <tr style="align-content: center">
                    <td><input type="hidden" name="${order.id}"/></td>
                    <td>${order.orderDate}</td>
                    <td>${order.tracksAmount}</td>
                    <td>${order.totalSum}</td>
                    <td><a class="header__link"
                           href="<c:url value='controller?command=purchasedTracks&id=${order.id}'/>">Show purchased
                        tracks</a></td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

</body>
</html>
