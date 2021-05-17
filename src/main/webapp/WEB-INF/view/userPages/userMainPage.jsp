<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.userHello" var="userHello" />
<fmt:message bundle="${local}" key="local.newTracks" var="newTracks" />
<fmt:message bundle="${local}" key="local.newAlbums" var="newAlbums" />
<fmt:message bundle="${local}" key="local.newPlaylists" var="newPlaylists" />

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div class="container-login">

    <b class="headlines-type">${userHello}, ${name}!</b><br>
    <br/>
    <jsp:include page="../fragments/search.jsp"/>
    <br/>
    <div class="new_releases_headline"><label>${newTracks}</label></div>
    <jsp:include page="../fragments/trackList.jsp"/>
    <div class="new_releases_headline"><label>${newAlbums}</label></div>
    <jsp:include page="../fragments/albums.jsp"/>
    <div class="new_releases_headline"><label>${newPlaylists}</label></div>
    <jsp:include page="../fragments/playlists.jsp"/>
</div>

</body>
</html>
