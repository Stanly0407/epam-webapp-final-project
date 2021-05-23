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

<div class="headlines-type">
    <p>Choose album for track:</p>
</div>


<c:if test="${alreadyExist}">
    <p style="color: red; margin-left: 20%;">Ошибка. Данный трек уже добавлен в альбом. Error. This track has already been added to the album.</p>
</c:if>


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
