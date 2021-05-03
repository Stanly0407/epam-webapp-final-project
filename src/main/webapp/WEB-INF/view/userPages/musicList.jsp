<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>

<fmt:message bundle="${local}" key="local.purchased" var="purhased"/>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div style="margin-left: auto">
    <img src="img/svg/Music_style.svg" alt="my_music" class="music-img">
</div>

<c:if test="${not empty purchasedTracks}">
    <div class="music-card-list">
        <c:forEach items="${purchasedTracks}" var="track">
            <div class="music-card">
                <input type="hidden" name="${track.id}"/>
                <div style="display: flex">
                    <div>
                        <img src="img/svg/Mood.svg" alt="like" class="music-cart-pic">
                    </div>
                    <div style="margin: 9px; color: whitesmoke">
                        <p> <c:forEach items="${track.artists}" var="artist">
                            <input type="hidden" name="${artist.id}"/>
                            ${artist.name} -
                        </c:forEach>
                        <b>${track.title}</b></p>
                    </div>
                </div>
                <audio class="audio" controls>
                    <source src="${track.filename}" type="audio/mpeg">
                </audio>
            </div>
        </c:forEach>
    </div>
</c:if>

</body>
</html>
