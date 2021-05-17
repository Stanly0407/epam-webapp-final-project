<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.button.addToCart" var="add"/>
<fmt:message bundle="${local}" key="local.purchased" var="purhased"/>
<fmt:message bundle="${local}" key="local.button.deleteFromCart" var="delete"/>
<fmt:message bundle="${local}" key="local.placeholder.enterComment" var="enterComment"/>
<fmt:message bundle="${local}" key="local.button.save" var="save"/>
<fmt:message bundle="${local}" key="local.commentsInfo" var="commentsInfo"/>
<fmt:message bundle="${local}" key="local.button.submit" var="submit"/>
<fmt:message bundle="${local}" key="local.a.edit" var="edit"/>
<fmt:message bundle="${local}" key="local.a.delete" var="delete"/>
<fmt:message bundle="${local}" key="local.h1.noCommentsInfo" var="noCommentsInfo"/>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="track-list">


    <c:if test="${not empty track.id}">
        <h1 class="headlines_type" style="color: red">Вы действительно хотите безвозвратно удалить трек?</h1>
        <table style="color: #b3d4fc;">
            <tr style="align-content: center">
                <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="header__cart-pic"></td>
                <td>${track.title}</td>
                <c:forEach items="${track.artists}" var="artist">
                    <input type="hidden" name="${artist.id}"/>
                    <td>${artist.name}</td>
                </c:forEach>
                <td>$ ${track.price} </td>
                <td>
                    <form action="/musicwebapp/uploadNew?command=deleteTrack" method="post">
                        <input type="hidden" name="${track.id}"/>
                        <button class="button-main" type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </table>
    </c:if>
</div>
</body>
</html>
