<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div class="container-login">
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">User list</a>
    </button>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Track list</a>
    </button>
    <br>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Album list</a>
    </button>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Playlist list</a>
    </button>
    <br>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=trackForm" class="header__link__button">Add track</a>
    </button>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=trackForm" class="header__link__button">Edit track</a>
    </button>
    <br>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Add artist</a>
    </button>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Edit artist</a>
    </button>
    <br>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Add album</a>
    </button>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Edit album</a>
    </button>
    <br>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Add playlist</a>
    </button>
    <button class="button-main" type="submit">
        <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button">Edit playlist</a>
    </button>
    <br>
</div>

<div>
    <form action="/musicwebapp/controller?command=newTrack">
        <div class="common-label"><label for="releaseDate">Release Date</label></div>
        <input class="common-input" type="date" id="releaseDate" name="releaseDate" required/>
        <br/>
        <div class="common-label"><label for="title">Title</label></div>
        <input class="common-input" type="text" id="title" placeholder="Enter title" name="title" required/>
        <br/>
        <div class="common-label"><label for="price">Price</label></div>
        <input class="common-input" type="text" id="price" placeholder="Enter price" name="price" required/>
        <br/>
        <br/>
        <div class="common-label"><label for="file">Click on the "Choose File" button to upload a file:</label></div>
        <input class="header__link__button" type="file" id="file" name="filename">
        <br/>
        <input class="header__link__button" type="submit">

        <select name="artist">
            <c:forEach items="${artists}" var="artist">
                <option value="${artist}"></option>
            </c:forEach>
        </select>

    </form>
    <br/>
    <br/>
</div>
</body>
</html>
