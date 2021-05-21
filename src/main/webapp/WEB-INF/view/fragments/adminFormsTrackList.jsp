<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.button.addToCart" var="add"/>
<fmt:message bundle="${local}" key="local.purchased" var="purhased"/>
<fmt:message bundle="${local}" key="local.button.deleteFromCart" var="delete"/>

<div class="track-list">
    <table>
        <c:forEach items="${trackList}" var="track">
            <tr style="align-content: center">
                <td><input type="hidden" name="${track.id}"/></td>
                <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="note-pic"></td>
                <td>${track.title}</td>
                <c:forEach items="${track.artists}" var="artist">
                    <input type="hidden" name="${artist.id}"/>
                    <td>${artist.name}</td>
                </c:forEach>
                <td>$ ${track.price} </td>
                <td>
                    <a class="header__link"
                       href="<c:url value='controller?command=commentsPage&id=${track.id}'/>">
                        <img src="img/svg/comment_icon_2.svg" alt="Comments" class="header__cart-pic">
                            ${track.commentsAmount}
                    </a>
                </td>

                <td>
                    <a class="header__link__button"
                       href="<c:url value='controller?command=editTrack&id=${track.id}'/>">Edit</a>
                </td>

                <c:if test="${not empty album.id}">
                <td>
                    <a class="header__link__button"
                       href="<c:url value='controller?command=deleteAlbumTrack&id=${album.id}&trackId=${track.id}'/>">Delete from album</a>
                </td>
                </c:if>

                <c:if test="${not empty playlist.id}">
                    <td>
                        <a class="header__link__button"
                           href="<c:url value='controller?command=deletePlaylistTrack&id=${playlist.id}&trackId=${track.id}'/>">Delete from playlist</a>
                    </td>
                </c:if>

            </tr>
        </c:forEach>
    </table>
</div>

