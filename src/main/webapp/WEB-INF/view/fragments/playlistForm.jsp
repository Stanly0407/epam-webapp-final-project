<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.h1.addPlaylist" var="addPlaylist"/>
<fmt:message bundle="${local}" key="local.td.releaseDate" var="release"/>
<fmt:message bundle="${local}" key="local.upload" var="uploadInfo"/>
<fmt:message bundle="${local}" key="local.button.save" var="save"/>
<fmt:message bundle="${local}" key="local.td.title" var="title"/>
<fmt:message bundle="${local}" key="local.placeholder.enterTitle" var="enterTitle"/>
<script type="text/javascript">
    <%@include file="/WEB-INF/js/pageScripts.js"%>
</script>

<html>
<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<h1 class="headlines-type">Edit Playlist</h1>

<c:if test="${alreadyExist}">
    <p style="color: red; margin-left: 20%;">Ошибка. Данный трек уже добавлен в плейлист. Error. This track has already been added to the playlist.</p>
</c:if>

<c:if test="${not empty playlist.id && not empty trackList}">
    <jsp:include page="../fragments/adminFormsTrackList.jsp"/>
</c:if>

<c:if test="${not empty playlist.id &&  empty trackList}">
    <p>Playlist is empty</p>
</c:if>

<div class="edit-track-form">
    <form enctype='multipart/form-data' action="/musicwebapp/uploadNew?command=addEditPlaylist" method="post">
        <input class="common-input" type="hidden" value="${playlist.id}" name="playlistId" required/>


        <div class="common-label"><label for="releaseDate">${release}</label></div>
        <input class="common-input" type="date" id="releaseDate" name="releaseDate" required/>


        <div class="common-label"><label for="playlistTitle">${title}</label></div>
        <input class="common-input" type="text" id="playlistTitle" name="playlistTitle" placeholder="${enterTitle}" required/>

        <div>
            <p>Add poster</p>
        </div>

        <div class="common-label"><label for="file">${uploadInfo}</label></div>
        <label class="file_upload">
            <input name="filename" id="file" type="file" required accept="jpg"/>
        </label>

        <button class="button-main" type="submit">${save}</button>
    </form>
    <br/>
    <br/>
</div>
</body>
</html>
