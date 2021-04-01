<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body >

<div class="header">
    <jsp:include page="fragments/header.jsp"/>
</div>
<h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Track List:</h1>

<c:if test="${not empty trackList}">
<%--    <form action="/musicwebapp/controller" method="get">--%>
<%--        <input type="hidden" name="command" value="trackList">--%>

        <div >

    <table style="color: #b3d4fc;">
        <tr>
            <th width="40">ID</th>
            <th width="200">TITLE</th>
            <th width="250">DESCRIPTION</th>
            <th width="120">PRICE</th>
            <th width="120">ARTIST_ID</th>

            <th width="80"></th>
            <th width="80"></th>
        </tr>
        <c:forEach items="${trackList}" var="track">
            <tr style="align-content: center">
                <td>${track.id}</td>
                <td>${track.title}</td>
                <td>${track.description}</td>
                <td>${track.price} $</td>
                <td>${track.artistId}</td>

                <td><a class="header__link" href="<c:url value='controller?command=editTrack/${track.id}'/>">Edit</a></td>
                <td><a class="header__link" href="<c:url value='controller?command=deleteTrack/${track.id}'/>">Delete</a></td>
            </tr>
        </c:forEach>
    </table>


        </div>

        </c:if>
<%--        <button class="button-main" type="submit">Login</button>--%>
    </form>



</body>
</html>
