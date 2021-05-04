<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.button.addToCart" var="add"/>
<fmt:message bundle="${local}" key="local.purchased" var="purhased"/>
<fmt:message bundle="${local}" key="local.button.deleteFromCart" var="delete"/>

<div class="track-list">
    <c:if test="${not empty trackList}">
        <div>
            <table>
                <c:forEach items="${trackList}" var="track">
                    <tr style="align-content: center">
                        <td><input type="hidden" name="${track.id}"/></td>
                        <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="header__cart-pic"></td>
                        <td>${track.title}</td>
                        <c:forEach items="${track.artists}" var="artist">
                            <input type="hidden" name="${artist.id}"/>
                            <td>${artist.name}</td>
                        </c:forEach>
                        <td>$ ${track.price} </td>
                        <td>
                            <a class="header__link"
                               href="<c:url value='controller?command=commentsPage&id=${track.id}'/>">
                                <img src="img/svg/comments.svg" alt="Comments" class="header__cart-pic">
                                    ${track.commentsAmount}
                            </a>
                        </td>
                        <c:if test="${track.status == 'PURCHASED'}">
                            <td class="purchased">${purhased}</td>
                        </c:if>
                        <c:if test="${track.status == 'AVAILABLE'}">
                            <td><a class="header__link__button"
                                   href="<c:url value='controller?command=addTrack&id=${track.id}'/>">${add}</a></td>
                        </c:if>
                        <c:if test="${track.status == 'ORDERED'}">
                            <td><a class="header__link"
                                   href="<c:url value='controller?command=deleteTrack&id=${track.id}'/>">${delete}</a>
                            </td>
                        </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
    <br>
    <br>

</div>