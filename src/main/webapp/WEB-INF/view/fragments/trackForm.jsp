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
<script type="text/javascript">
    <%@include file="/WEB-INF/js/pageScripts.js"%>
</script>


<html>
<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<h1 class="headlines">${addTrack}</h1>

<div class="edit-track-form">
    <form enctype='multipart/form-data' action="/musicwebapp/uploadNew?command=addNewTrack" method="post">
        <br/>
        <div class="common-label"><label for="releaseDate">${release}</label></div>
        <input class="common-input" type="date" id="releaseDate" name="releaseDate" required/>
        <br/> <br/>
        <div class="common-label"><label for="title">${title}</label></div>
        <input class="common-input" type="text" id="title" placeholder="${enterTitle}" name="title" required/>
        <br/> <br/>
        <div class="common-label"><label for="price">${price}</label></div>
        <input class="common-input" type="text" id="price" placeholder="${enterPrice}" name="price" required/>
        <br/>
        <br/>
        <br/>
        <select class="select-artist" name="artistIds" required>
            <option selected disabled>${selectArtist}</option>
            <c:forEach items="${artists}" var="artist">
                <option value="${artist.id}">${artist.name}</option>
            </c:forEach>
        </select>
        <br/>
        <br/>
        <br/>
        <div class="common-label"><label for="file">${uploadInfo}</label></div>
        <label class="file_upload">
            <input name="filename" id="file" type="file" required accept=".mp3"/>
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
