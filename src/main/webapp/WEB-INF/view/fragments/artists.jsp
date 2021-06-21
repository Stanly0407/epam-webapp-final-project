<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.a.edit" var="edit"/>
<fmt:message bundle="${local}" key="local.a.delete" var="delete"/>

<html>

<body>

<div class="header">
    <jsp:include page="header.jsp"/>
</div>
<div class="container-login">
    <jsp:include page="search.jsp"/>
    <br/>
    <br/>
    <div class="card__list">
    <c:forEach items="${artists}" var="artist">
        <div class="card">
            <a class="header__link" href="<c:url value='controller?command=artistMusic&id=${artist.id}'/>">
                <figure class="artist-img">
                <img class="img_card" src="${artist.filename}" alt="artistPoster">
                </figure>
                <div class="container_card">
                    <input type="hidden" name="${artist.id}"/>
                    <h4><b>${artist.name}</b></h4>
                </div>
            </a>
            <div style="margin: 8px;">
                <c:if test="${sessionScope.role eq 'ADMIN'}">
                    <div><a style="font-size: 14px; line-height: 24px;color:  #48D5CB; text-decoration: none;"
                            href="<c:url value='controller?command=editArtist&id=${artist.id}'/>">${edit}</a>
                    </div>
                </c:if>
            </div>
        </div>
    </c:forEach>
    </div>

</body>
</html>
