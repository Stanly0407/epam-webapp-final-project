<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="cart.button.addToCart" var="add"/>
<fmt:message bundle="${local}" key="cart.purchased" var="purhased"/>
<fmt:message bundle="${local}" key="cart.button.deleteFromCart" var="delete"/>
<fmt:message bundle="${local}" key="local.a.edit" var="edit"/>
<fmt:message bundle="${local}" key="track.button.addToAlbum" var="addToAlbum"/>
<fmt:message bundle="${local}" key="track.button.addToPlaylist" var="addToPlaylist"/>
<fmt:message bundle="${local}" key="track.button.deleteFromAlbum" var="deleteFromAlbum"/>
<fmt:message bundle="${local}" key="track.button.deleteFromPlaylist" var="deleteFromPlaylist"/>

<div class="track-list">
    <c:if test="${not empty trackList}">
        <div>
            <table>
                <c:forEach items="${trackList}" var="track">
                    <tr style="align-content: center">
                        <td><input type="hidden" name="${track.id}"/></td>
                        <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="note-pic"></td>
                        <td><p>${track.title}</p></td>
                        <td><c:forEach items="${track.artists}" var="artist">
                            <input type="hidden" name="${artist.id}"/>
                           <p>  ${artist.name}</p> <br>
                        </c:forEach></td>
                        <td>$ ${track.price} </td>
                        <td style="margin-right: 35px">
                            <a class="header__link"
                               href="<c:url value='controller?command=commentsPage&id=${track.id}'/>">
                               <div style="display: flex"> <img src="img/svg/comment_icon_2.svg" alt="Comments" class="header__cart-pic">
                                   ${track.commentsAmount}
                            </a></div>
                        </td>

                        <c:if test="${sessionScope.role eq 'USER'}">
                            <c:if test="${track.status eq 'PURCHASED'}">
                                <td class="purchased">${purhased}</td>
                            </c:if>
                            <c:if test="${track.status eq 'AVAILABLE'}">
                                <td><a class="header__link__button"
                                       href="<c:url value='controller?command=addTrack&id=${track.id}'/>">${add}</a>
                                </td>
                            </c:if>
                            <c:if test="${track.status eq 'ORDERED'}">
                                <td><a class="header__link"
                                       href="<c:url value='controller?command=deleteTrackFromCart&id=${track.id}'/>">${delete}</a>
                                </td>
                            </c:if>
                        </c:if>

                        <c:if test="${sessionScope.role eq 'ADMIN'}">
                            <td><a class="header__link__button"
                                   href="<c:url value='controller?command=editTrack&id=${track.id}'/>">${edit}</a></td>
                            <c:if test="${empty album.id}">
                                <td style="margin-right: 20px"><a class="header__link__button"
                                       href="<c:url value='controller?command=chooseAlbum&id=${track.id}'/>">${addToAlbum}</a>
                                </td>
                            </c:if>
                            <c:if test="${ empty playlist.id}">
                                <td  style="margin-right: 20px"><a class="header__link__button"
                                       href="<c:url value='controller?command=choosePlaylist&id=${track.id}'/>">${addToPlaylist}</a>
                                </td>
                            </c:if>

                            <c:if test="${not empty album.id}">
                                <td  style="margin-right: 20px">
                                    <a class="header__link__button"
                                       href="<c:url value='controller?command=deleteCollectionTrack&id=${album.id}trackId=${track.id}'/>">${deleteFromAlbum}</a>
                                </td>
                            </c:if>
                            <c:if test="${not empty playlist.id}">
                                <td>
                                    <a class="header__link__button"
                                       href="<c:url value='controller?command=deleteCollectionTrack&id=${playlist.id}trackId=${track.id}'/>">${deleteFromPlaylist}</a>
                                </td>
                            </c:if>

                        </c:if>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </c:if>
</div>
