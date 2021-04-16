<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container-login" align="center">

    <b style="color: aliceblue">Hello, ${name}!</b><br>
    <br/>
    <jsp:include page="../fragments/search.jsp"/>
    <br/>
    <br/>

    <div class="common-label"><label>New Tracks</label></div>
    <jsp:include page="../fragments/trackList.jsp"/>
    <br/>
    <br/>

    <div class="common-label"><label>New Albums</label></div>
    <jsp:include page="../fragments/albums.jsp"/>
    <br/><br/>

    <div class="common-label"><label>New Playlists</label></div>
    <jsp:include page="../fragments/playlists.jsp"/>
    <br/><br/>
</div>

<jsp:include page="../fragments/footer.jsp"/>

</body>
</html>
