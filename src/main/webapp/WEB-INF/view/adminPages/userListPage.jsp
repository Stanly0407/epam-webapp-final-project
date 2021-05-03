<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.button.block" var="block"/>
<fmt:message bundle="${local}" key="local.button.unblock" var="unblock"/>


<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div>
    <table>
        <c:forEach items="${userList}" var="user">
            <tr style="align-content: center">
                <td><input type="hidden" name="${user.id}"/></td>
                <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="header__cart-pic"></td>
                <td>${user.login}</td>
                <td>${user.name} </td>
                <td>${user.lastname}</td>
                <td>$ ${user.balance}</td>
                <c:if test="${user.status}">
                    <td><a class="header__link__button"
                           href="<c:url value='controller?command=changeUserStatus&id=${user.id}&status=${user.status}'/>">${unblock}</a></td>
                </c:if>
                <c:if test="${!user.status}">
                    <td><a class="header__link__button" style="color: orangered"
                           href="<c:url value='controller?command=changeUserStatus&id=${user.id}&status=${user.status}'/>">${block}</a></td>
                </c:if>
                </td>
            </tr>
        </c:forEach>
    </table>
</div>



</body>
</html>
