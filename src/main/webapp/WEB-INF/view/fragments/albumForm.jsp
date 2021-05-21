<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.h1.addAlbum" var="addAlbum"/>
<fmt:message bundle="${local}" key="local.td.releaseDate" var="release"/>
<fmt:message bundle="${local}" key="local.td.title" var="title"/>
<fmt:message bundle="${local}" key="local.upload" var="uploadInfo"/>
<fmt:message bundle="${local}" key="local.button.save" var="save"/>
<fmt:message bundle="${local}" key="local.placeholder.enterTitle" var="enterTitle"/>
<fmt:message bundle="${local}" key="local.select.default.selectArtist" var="selectArtist"/>
<fmt:message bundle="${local}" key="local.a.delete" var="delete"/>

<script type="text/javascript">
    <%@include file="/WEB-INF/js/pageScripts.js"%>
</script>

<html>
<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<h1 class="headlines-type">Edit Album</h1>

<c:if test="${not empty album.id && not empty trackList}">
    <jsp:include page="../fragments/adminFormsTrackList.jsp"/>
</c:if>

<c:if test="${not empty album.id &&  empty trackList}">
    <p>Album is empty</p>
</c:if>

<div class="edit-track-form">


    <form enctype='multipart/form-data' action="/musicwebapp/uploadNew?command=addEditAlbum" method="post">
        <input class="common-input" type="hidden" value="${album.id}" name="albumId" required/>

        <div class="common-label"><label for="releaseDate">${release}</label></div>
        <input class="common-input" type="date" id="releaseDate" name="releaseDate" value="${album.releaseDate}"
               required/>

        <div class="common-label"><label for="albumTitle">${title}</label></div>
        <input class="common-input" type="text" id="albumTitle" name="albumTitle" placeholder="${enterTitle}"
               value="${album.title}" required/>

        <c:if test="${not empty album.id}">
            <div class="common-label">
                <p style="color: #CF469D">${album.artist.name}</p>
            </div>
        </c:if>

        <div>
            <select class="select-artist" name="artistId" required>
                <option selected disabled>${selectArtist}</option>
                <c:forEach items="${artists}" var="artist">
                    <option value="${artist.id}">${artist.name}</option>
                </c:forEach>
            </select>
        </div>

        <div>
            <p>Add poster</p>
        </div>

        <div class="common-label"><label for="file">${uploadInfo}</label></div>
        <label class="file_upload">
            <input name="filename" id="file" type="file" required accept="jpg"/>
        </label>

        <button class="button-main" type="submit">${save}</button>
    </form>

</div>
</body>
</html>
