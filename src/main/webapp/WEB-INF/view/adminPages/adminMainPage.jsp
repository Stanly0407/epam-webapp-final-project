<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.h1.addPlaylist" var="addPlaylist"/>
<fmt:message bundle="${local}" key="local.h1.addTrack" var="addTrack"/>
<fmt:message bundle="${local}" key="local.h1.addAlbum" var="addAlbum"/>
<fmt:message bundle="${local}" key="local.h1.addArtist" var="addArtist"/>


<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div class="container-login">
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=userList" class="header__link__button">User list</a>
    </button>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Track list</a>--%>
<%--    </button>--%>
<%--    <br>--%>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Album list</a>--%>
<%--    </button>--%>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Playlist list</a>--%>
<%--    </button>--%>
    <br>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=trackForm" class="header__link__button">${addTrack}</a>
    </button>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=trackForm" class="header__link__button">Edit track</a>--%>
<%--    </button>--%>
    <br>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=artistForm" class="header__link__button">${addArtist}</a>
    </button>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Edit artist</a>--%>
<%--    </button>--%>
    <br>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=albumForm" class="header__link__button">${addAlbum}</a>
    </button>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Edit album</a>--%>
<%--    </button>--%>
<%--    <br>--%>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=playlistForm" class="header__link__button">${addPlaylist}</a>
    </button>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Edit playlist</a>--%>
<%--    </button>--%>
    <br>
</div>

    <br/>
    <br/>
</div>
</body>
</html>
