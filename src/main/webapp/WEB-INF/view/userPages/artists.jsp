<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card__list">
    <c:forEach items="${artists}" var="artist">
        <div class="card">
            <a class="header__link" href="<c:url value='controller?command=seeArtist&id=${artist.id}'/>">
                <img class="img_card" src="img/svg/musician.svg" alt="artistPoster">
                <div class="container_card">
                    <input type="hidden" name="${artist.id}"/>
                    <h4><b>${artist.name}</b></h4>
                </div>
            </a>
        </div>
    </c:forEach>
</div>