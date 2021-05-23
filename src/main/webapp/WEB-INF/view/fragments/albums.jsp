<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.a.edit" var="edit"/>
<fmt:message bundle="${local}" key="local.a.delete" var="delete"/>

<div class="card__list">
    <c:forEach items="${albums}" var="album">
        <div class="card">
            <a class="header__link" href="<c:url value='controller?command=collectionMusic&id=${album.id}'/>">
                <img class="img_card" src="${album.filename}" alt="Album">
                <div class="container_card">

                    <input type="hidden" name="${album.id}"/>
                    <h4><b>${album.artist.name}</b></h4>
                    <p>${album.title}</p>

                    <div style="margin-top: 8px;">
                        <c:if test="${sessionScope.role eq 'ADMIN'}">
                            <div><a style="font-size: 14px; line-height: 24px;color:  #48D5CB; text-decoration: none;"
                                    href="<c:url value='controller?command=editAlbum&id=${album.id}'/>">${edit}</a>
                            </div>
                            <div><a style="font-size: 14px; line-height: 24px;color:  #48D5CB; text-decoration: none;"
                                    href="<c:url value='controller?command=deleteAlbum&id=${album.id}'/>">${delete}</a>
                            </div>
                        </c:if>
                    </div>

                </div>
            </a>
        </div>
    </c:forEach>
</div>