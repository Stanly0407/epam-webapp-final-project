<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div class="container-login">
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">User list</a>--%>
<%--    </button>--%>
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
        <a href="/musicwebapp/controller?command=trackForm" class="header__link__button">Add track</a>
    </button>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=trackForm" class="header__link__button">Edit track</a>--%>
<%--    </button>--%>
<%--    <br>--%>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Add artist</a>--%>
<%--    </button>--%>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Edit artist</a>--%>
<%--    </button>--%>
<%--    <br>--%>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Add album</a>--%>
<%--    </button>--%>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Edit album</a>--%>
<%--    </button>--%>
<%--    <br>--%>
<%--    <button class="button-main" type="submit">--%>
<%--        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Add playlist</a>--%>
<%--    </button>--%>
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
