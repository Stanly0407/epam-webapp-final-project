<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.button.block" var="block"/>
<fmt:message bundle="${local}" key="local.button.unblock" var="unblock"/>
<fmt:message bundle="${local}" key="admin.userList.login" var="login"/>
<fmt:message bundle="${local}" key="admin.userList.name" var="name"/>
<fmt:message bundle="${local}" key="admin.userList.lastname" var="lastname"/>
<fmt:message bundle="${local}" key="admin.userList.balance" var="balance"/>
<fmt:message bundle="${local}" key="admin.userList.comments" var="comments"/>
<fmt:message bundle="${local}" key="admin.userList.purchasedTracks" var="purchasedTracks"/>
<fmt:message bundle="${local}" key="admin.userList.discount" var="discount"/>
<fmt:message bundle="${local}" key="admin.userList.freeTracks" var="freeTracks"/>
<fmt:message bundle="${local}" key="admin.userList.addDiscount" var="addDiscount"/>
<fmt:message bundle="${local}" key="admin.userList.deleteDiscount" var="deleteDiscount"/>
<fmt:message bundle="${local}" key="admin.userList.addFreeTracks" var="addFreeTracks"/>
<fmt:message bundle="${local}" key="admin.userList.deleteFreeTracks" var="deleteFreeTracks"/>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div class="track-list">
    <table>
        <tr>
            <th></th>
            <th></th>
            <th>${login}</th>
            <th>${name}</th>
            <th>${lastname}</th>
            <th>${balance}</th>
            <th>${comments}</th>
            <th>${purchasedTracks}</th>
            <th><p style="text-align: center;">${discount}</p></th>
            <th><p style="text-align: center;">${freeTracks}</p></th>
            <th></th>
        </tr>
        <c:forEach items="${userList}" var="user">
            <tr style="align-content: center">
                <td><input type="hidden" name="${user.id}"/></td>
                <td><img src="img/svg/user_icon.svg" alt="Cart" class="header__cart-pic"></td>
                <td>${user.login}</td>
                <td>${user.name} </td>
                <td>${user.lastname}</td>
                <td>$ ${user.balance}</td>
                <td><p style="text-align: center;"> ${user.commentsAmount}</p></td>
                <td><p style="text-align: center;">${user.purchasedTracksAmount}</p></td>

                <c:if test="${empty user.bonusDiscount}">
                    <td>
                        <form action="/musicwebapp/controller?command=addDiscount" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <input type="number" min="1" max="100" class="user_bonus_input" name="discountAmount"/>
                            <button class="button-main" style="height: 40px; font-size: 15px" type="submit">${addDiscount}</button>
                        </form>
                    </td>
                </c:if>
                <c:if test="${not empty user.bonusDiscount}">
                    <td>
                        <div class="inline_blocks">
                            <div style="width: 40px; color: aquamarine">
                                <p>${user.bonusDiscount.amount}</p>
                            </div>
                            <div>
                                <a class="header__link__button" style="font-size: 15px"
                                   href="<c:url value='controller?command=deleteBonus&id=${user.bonusDiscount.id}'/>">${deleteDiscount}</a>
                            </div>
                        </div>
                    </td>
                </c:if>

                <c:if test="${empty user.bonusFreeTracks}">
                    <td>
                        <form action="/musicwebapp/controller?command=addFreeTracks" method="post">
                            <input type="hidden" name="userId" value="${user.id}"/>
                            <input type="number" min="1" max="99" id="discount" class="user_bonus_input"
                                   name="freeTracksAmount"/>
                            <button class="button-main" style="height: 40px; font-size: 15px" type="submit">${addFreeTracks}</button>
                        </form>
                    </td>
                </c:if>
                <c:if test="${not empty user.bonusFreeTracks}">
                    <td>
                        <div class="inline_blocks">
                            <div style="width: 40px; color: aquamarine">
                                <p>${user.bonusFreeTracks.amount}</p>
                            </div>
                            <div>
                                <a class="header__link__button" style="font-size: 15px"
                                   href="<c:url value='controller?command=deleteBonus&id=${user.bonusFreeTracks.id}'/>">${deleteFreeTracks}</a>
                            </div>
                        </div>
                    </td>
                </c:if>

                <c:if test="${user.status}">
                    <td><a class="header__link__button" style="font-size: 15px"
                           href="<c:url value='controller?command=changeUserStatus&id=${user.id}&status=${user.status}'/>">${unblock}</a>
                    </td>
                </c:if>
                <c:if test="${!user.status}">
                    <td><a class="pink_button" style="font-size: 15px"
                           href="<c:url value='controller?command=changeUserStatus&id=${user.id}&status=${user.status}'/>">${block}</a>
                    </td>
                </c:if>

            </tr>
        </c:forEach>
    </table>
</div>


</body>
</html>
