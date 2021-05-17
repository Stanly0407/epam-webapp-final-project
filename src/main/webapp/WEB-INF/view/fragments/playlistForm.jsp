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

<h1 class="headlines">${addPlaylist}</h1>

<div class="edit-track-form">
    <form enctype='multipart/form-data' action="/musicwebapp/uploadNew?command=addNewPlaylist" method="post">
        <br/> <br/>
        <div class="common-label"><label for="releaseDate">${release}</label></div>
        <input class="common-input" type="date" id="releaseDate" name="releaseDate" required/>
        <br/> <br/>
        <div class="common-label"><label for="playlistTitle">${title}</label></div>
        <input class="common-input" type="text" id="playlistTitle" name="playlistTitle" placeholder="${enterTitle}" required/>
        <br/>
        <br/><br/>
        <div class="common-label"><label for="file">${uploadInfo}</label></div>
        <label class="file_upload">
            <input name="filename" id="file" type="file" required accept="jpg"/>
        </label>
        <br/>
        <br/>
        <button class="button-main" type="submit">${save}</button>
    </form>
    <br/>
    <br/>
</div>
</body>
</html>
