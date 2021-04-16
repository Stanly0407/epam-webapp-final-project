<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card__list">
    <c:forEach items="${albums}" var="album">
        <div class="card">
            <a class="header__link" href="<c:url value='controller?command=see&id=${album.id}'/>">
                <img class="img_card" src="img/svg/album.svg" alt="Album">
                <div class="container_card">
                    <input type="hidden" name="${album.id}"/>
                    <h4><b>${album.artist.name}</b></h4>
                    <p>${album.title}</p>
                </div>
            </a>
        </div>
    </c:forEach>
</div>