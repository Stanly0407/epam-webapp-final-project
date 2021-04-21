<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container-login">

    <b class="headlines">Hello, ${name}!</b><br>
    <br/>
    <jsp:include page="../fragments/search.jsp"/>
    <br/>
    <div class="new_releases_headline"><label>New Tracks</label></div>
    <jsp:include page="../fragments/trackList.jsp"/>
    <div class="new_releases_headline"><label>New Albums</label></div>
    <jsp:include page="../fragments/albums.jsp"/>
    <div class="new_releases_headline"><label>New Playlists</label></div>
    <jsp:include page="../fragments/playlists.jsp"/>

</div>

<jsp:include page="../fragments/footer.jsp"/>

</body>
</html>
