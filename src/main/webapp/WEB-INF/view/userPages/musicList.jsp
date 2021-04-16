<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Track List:</h1>

<c:if test="${not empty purchasedTracks}">
    <div>
        <table style="color: #b3d4fc;">

            <c:forEach items="${purchasedTracks}" var="track">
                <tr style="align-content: center">
                    <td><input type="hidden" name="${track.id}"/></td>
                    <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="header__cart-pic"></td>
                    <td>${track.title}</td>
                    <c:forEach items="${track.artists}" var="artist">
                        <td><input type="hidden" name="${artist.id}"/></td>
                        <td>${artist.name}</td>
                    </c:forEach>

                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

</body>
</html>
