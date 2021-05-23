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

<head>
    <script src="http://code.jquery.com/jquery-2.2.4.js"
            type="text/javascript"></script>
</head>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="edit-track-form">
    <div>
        <form enctype='multipart/form-data' action="/musicwebapp/uploadNew?command=addEditTrack" method="post">

            <input class="common-input" type="hidden" value="${track.id}" name="trackId" required/>

            <div class="common-label"><label for="releaseDate">${releaseLabel}</label></div>
            <input class="common-input" type="date" id="releaseDate" value="${track.releaseDate}" name="releaseDate"
                   required/>

            <div class="common-label"><label for="title">${titleLabel}</label></div>
            <input class="common-input" type="text" id="title" placeholder="${enterTitle}" value="${track.title}"
                   name="title" required/>

            <div class="common-label"><label for="price">${priceLabel}</label></div>
            <input class="common-input" type="text" id="price" placeholder="${enterPrice}" value="${track.price}"
                   name="price" required/>

            <c:if test="${not empty track.id}">
                <div class="common-label">
                    <p>Current Artists:</p>
                    <c:forEach items="${track.artists}" var="currentArtist">
                        <p style="color: #CF469D">${currentArtist.name}</p>
                    </c:forEach>
                </div>
            </c:if>

            <ul id="authorSelector">
                <li>
                    <select class="select-artist" name="artistId">
                        <option selected disabled>${selectArtist}</option>
                        <c:forEach items="${artists}" var="artist">
                            <option value="${artist.id}">${artist.name}</option>
                        </c:forEach>
                    </select>
                </li>
            </ul>
            <ul id="additionalAuthorSelector">
            </ul>

            <div class="common-label"><label for="file">${uploadInfo}</label></div>
            <label class="file_upload">
                <input name="filename" id="file" type="file" accept=".mp3"/>
            </label>

            <button class="button-main" type="submit">${save}</button>
        </form>

        <div style="margin-top: 20px;">
        <c:if test="${not empty track.id}">
            <a class="pink_button"
               href="<c:url value='controller?command=deleteTrackPreventing&id=${track.id}'/>">Delete</a>
        </c:if>
        </div>

    </div>
    <div>
        <button class="button-main" style="margin-top: 130px; margin-left: 18px" onclick="addArtist()">Add Artist</button>
    </div>
</div>

</body>
</html>

