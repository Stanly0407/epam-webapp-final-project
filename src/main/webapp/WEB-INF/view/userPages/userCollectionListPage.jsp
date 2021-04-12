<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Track List:</h1>

<c:if test="${not empty collectionList}">

    <div>
        <table style="color: #b3d4fc;">
            <tr>
                <th width="4"></th>
                <th width="200">TITLE</th>
                <th width="120">ARTIST </th>

                <th width="80"></th>

            </tr>
            <c:forEach items="${collectionList}" var="track">
                <tr style="align-content: center">
                    <td>   <input type="hidden" name="${collection.id}"/></td>
                    <td>${collection.title}</td>
                    <td>${collection.releaseDate}</td>
                    <c:if test="${collection.type == 'ALBUM'}">
                    <td> <c:forEach items="${collection.artist}" var="artist">
                        <td>   <input type="hidden" name="${artist.id}"/></td>
                    <td>${artist.name}</td>
                    </c:forEach>
                    </c:if>
                    <td><a class="header__link" href="<c:url value='controller?command=see&id=${album.id}'/>">SEE</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

</body>
</html>
