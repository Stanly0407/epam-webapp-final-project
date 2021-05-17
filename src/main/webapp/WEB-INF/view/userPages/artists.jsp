<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-login">
    <jsp:include page="../fragments/search.jsp"/>
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
        </div>
    </c:forEach>
    </div>

</body>
</html>
