<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="customTags" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>

<fmt:message bundle="${local}" key="local.a.edit" var="edit"/>


<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div>
    <p>Choose album for track:</p>
</div>

<div class="track-list">
    <table style="color: #b3d4fc;">
        <tr style="align-content: center">
            <td><input type="hidden" name="${track.id}"/></td>
            <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="header__cart-pic"></td>
            <td>${track.title}</td>
            <c:forEach items="${track.artists}" var="artist">
                <input type="hidden" name="${artist.id}"/>
                <td>${artist.name}</td>
            </c:forEach>
            <td>$ ${track.price} </td>

            <c:if test="${sessionScope.role eq 'ADMIN'}">
                <td><a class="header__link__button"
                       href="<c:url value='controller?command=editTrack&id=${track.id}'/>">${edit}</a></td>
            </c:if>
        </tr>
    </table>
</div>

<div class="edit-track-form">
    <div>
        <form action="/musicwebapp/controller?command=addToAlbum&id=${track.id}" method="post">
            <div>
                <select class="select-artist" name="albumId">
                    <option selected disabled>selectAlbum</option>
                    <c:forEach items="${albums}" var="album">
                        <option value="${album.id}">${album.artist.name} - ${album.title}</option>
                    </c:forEach>
                </select>
            </div>
            <div>
                <button class="button-main" style="margin-top: 130px; margin-left: 18px" type="submit">Add Track</button>
            </div>
        </form>
    </div>
</div>
</body>
</html>
