<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.h1.addTrack" var="addTrack"/>
<fmt:message bundle="${local}" key="local.td.releaseDate" var="release"/>
<fmt:message bundle="${local}" key="local.td.title" var="title"/>
<fmt:message bundle="${local}" key="local.td.price" var="price"/>
<fmt:message bundle="${local}" key="local.upload" var="uploadInfo"/>
<fmt:message bundle="${local}" key="local.button.save" var="save"/>
<fmt:message bundle="${local}" key="local.placeholder.enterTitle" var="enterTitle"/>
<fmt:message bundle="${local}" key="local.placeholder.enterPrice" var="enterPrice"/>
<fmt:message bundle="${local}" key="local.select.default.selectArtist" var="selectArtist"/>
<fmt:message bundle="${local}" key="local.h1.addNewArtist" var="addNewArtistHeadline"/>
<fmt:message bundle="${local}" key="local.label.artistNameLabel" var="artistNameLabel"/>
<script type="text/javascript">
    <%@include file="/WEB-INF/js/pageScripts.js"%>
</script>

<html>
<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<h1 class="headlines">${addNewArtistHeadline}</h1>

<div class="edit-track-form">
    <form enctype='multipart/form-data' action="/musicwebapp/uploadNew?command=addEditArtist" method="post">

        <input class="common-input" type="hidden" value="${artist.id}" name="artistId" required/>

        <div class="common-label"><label for="artistName">${artistNameLabel}</label></div>

        <input class="common-input" type="text" id="artistName" name="artistName" value="${artist.name}" required/>


        <div class="common-label"><label for="file">${uploadInfo}</label></div>
        <label class="file_upload">
            <input name="filename" id="file" type="file" required accept="jpg"/>
        </label>

        <button class="button-main" type="submit">${save}</button>
    </form>

</div>
</body>
</html>
