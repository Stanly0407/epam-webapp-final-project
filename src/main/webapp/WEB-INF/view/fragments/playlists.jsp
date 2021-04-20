<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="card__list">
    <c:forEach items="${playlists}" var="playlist">
        <div class="card">
            <a class="header__link" href="<c:url value='controller?command=see&id=${playlist.id}'/>">
                <img class="img_card" src="img/svg/State.jpg" alt="Playlist">

                <div class="container_card">
                    <input type="hidden" name="${playlist.id}"/>
                    <h4><b>${playlist.title}</b></h4>
                </div>
            </a>
        </div>
    </c:forEach>
</div>