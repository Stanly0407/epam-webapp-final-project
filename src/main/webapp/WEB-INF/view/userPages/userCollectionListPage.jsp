<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<jsp:include page="../fragments/search.jsp"/>

<h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">LIST:</h1>

<c:if test="${not empty collectionList}">

    <div>
        <table style="color: #b3d4fc;">
            <tr>
                <th width="200"></th>
                <th width="200"></th>
                <th width="120"></th>

                <th width="80"></th>

            </tr>
            <c:forEach items="${collectionList}" var="collection">
                <tr style="align-content: center">
                    <td>   <input type="hidden" name="${collection.id}"/></td>
                    <td>${collection.title}</td>
                    <td>${collection.releaseDate}</td>
                    <c:if test="${collection.type == 'ALBUM'}">
                        <td>${collection.artist.name}</td>
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
