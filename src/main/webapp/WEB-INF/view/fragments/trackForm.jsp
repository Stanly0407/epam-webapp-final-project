<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.h1.addTrack" var="addTrack"/>
<fmt:message bundle="${local}" key="local.td.releaseDate" var="releaseLabel"/>
<fmt:message bundle="${local}" key="local.td.title" var="titleLabel"/>
<fmt:message bundle="${local}" key="local.td.price" var="priceLabel"/>
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

<%--<h1 class="headlines">${addTrack}</h1>--%>

<div class="edit-track-form">
    <form enctype='multipart/form-data' action="/musicwebapp/uploadNew?command=addEditTrack" method="post">
        <br/>
        <input class="common-input" type="hidden" value="${track.id}" name="trackId" required/>

        <div class="common-label"><label for="releaseDate">${releaseLabel}</label></div>
        <input class="common-input" type="date" id="releaseDate" value="${track.releaseDate}" name="releaseDate"
               required/>
        <br/> <br/>
        <div class="common-label"><label for="title">${titleLabel}</label></div>
        <input class="common-input" type="text" id="title" placeholder="${enterTitle}" value="${track.title}"
               name="title" required/>
        <br/> <br/>
        <div class="common-label"><label for="price">${priceLabel}</label></div>
        <input class="common-input" type="text" id="price" placeholder="${enterPrice}" value="${track.price}"
               name="price" required/>
        <br/>
        <br/>
        <br/>
        <select class="select-artist" name="artistId">
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
            <input name="filename" id="file" type="file" accept=".mp3"/>
        </label>
        <br/>
        <br/>
        <button class="button-main" type="submit">${save}</button>
    </form>
    <br>
    <c:if test="${not empty track.id}">
            <a style="color: red" class="header__link" href="<c:url value='controller?command=deleteTrackPreventing&id=${track.id}'/>">Delete</a>
    </c:if>
    <br/>
    <br/>
</div>
</body>
</html>
