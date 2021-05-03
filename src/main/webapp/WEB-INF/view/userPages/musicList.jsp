<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.h1.trackList" var="trackListInfo" />
<fmt:message bundle="${local}" key="local.purchased" var="purhased" />

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<h1 class="headlines">${trackListInfo}</h1>

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

                        <td>
                            <audio class="audio" controls>
                                <source src="${track.filename}" type="audio/mpeg">
                            </audio>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>

</body>
</html>
