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

<div class="track-list">
    <table>
        <c:forEach items="${userList}" var="user">
            <tr style="align-content: center">
                <td><input type="hidden" name="${user.id}"/></td>
                <td><img src="img/svg/user_icon.svg" alt="Cart" class="header__cart-pic"></td>
                <td>${user.login}</td>
                <td>${user.name} </td>
                <td>${user.lastname}</td>
                <td>$ ${user.balance}</td>
                <td>${user.commentsAmount}</td>
                <td>${user.purchasedTracksAmount}</td>

                <c:if test="${empty user.bonusDiscount}">
                    <td>
                        <form action="/musicwebapp/controller?command=addDiscount" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <div><label>Discount = </label></div>
                            <input type="number" min="1" max="100"  name="discountAmount"/>
                            <button class="button-main" type="submit">Add Discount</button>
                        </form>
                    </td>
                </c:if>
                <c:if test="${not empty user.bonusDiscount}">
                    <td>
                        <p>${user.bonusDiscount.amount}</p>
                        <a class="header__link__button"
                           href="<c:url value='controller?command=deleteBonus&id=${user.bonusDiscount.id}'/>">Delete
                            Discount</a>
                    </td>
                </c:if>

                <c:if test="${empty user.bonusFreeTracks}">
                    <td>
                        <form action="/musicwebapp/controller?command=addFreeTracks" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <div><label for="discount">Free Tracks = </label></div>
                            <input type="number" min="1" max="99" id="discount" name="freeTracksAmount"/>
                            <button class="button-main" type="submit">Add Discount</button>
                        </form>
                    </td>
                </c:if>
                <c:if test="${not empty user.bonusFreeTracks}">
                    <td>
                        <p>${user.bonusFreeTracks.amount}</p>
                        <a class="header__link__button"
                           href="<c:url value='controller?command=deleteBonus&id=${user.bonusFreeTracks.id}'/>">Delete
                            Free Tracks</a>
                    </td>
                </c:if>

                <c:if test="${user.status}">
                    <td><a class="header__link__button"
                           href="<c:url value='controller?command=changeUserStatus&id=${user.id}&status=${user.status}'/>">${unblock}</a>
                    </td>
                </c:if>
                <c:if test="${!user.status}">
                    <td><a class="header__link__button" style="color: orangered"
                           href="<c:url value='controller?command=changeUserStatus&id=${user.id}&status=${user.status}'/>">${block}</a>
                    </td>
                </c:if>

            </tr>
        </c:forEach>
    </table>
</div>


</body>
</html>
