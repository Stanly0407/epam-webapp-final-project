<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.a.edit" var="edit"/>

<div class="card__list">
    <c:forEach items="${playlists}" var="playlist">
        <div class="card">
            <a class="header__link" href="<c:url value='controller?command=collectionMusic&id=${playlist.id}'/>">
                <img class="img_card" src="${playlist.filename}" alt="Playlist">
                <div class="container_card">
                    <input type="hidden" name="${playlist.id}"/>
                    <h4><b>${playlist.title}</b></h4>


                    <div style="margin-top: 8px;">
                        <c:if test="${sessionScope.role eq 'ADMIN'}">
                            <div><a style="font-size: 14px; line-height: 24px;color:  #48D5CB; text-decoration: none;"
                                    href="<c:url value='controller?command=editPlaylist&id=${playlist.id}'/>">${edit}</a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </a>
        </div>
    </c:forEach>
</div>