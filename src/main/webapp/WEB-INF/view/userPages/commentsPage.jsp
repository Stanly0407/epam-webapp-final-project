<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.button.addToCart" var="add" />
<fmt:message bundle="${local}" key="local.purchased" var="purhased" />
<fmt:message bundle="${local}" key="local.button.deleteFromCart" var="delete" />
<fmt:message bundle="${local}" key="local.placeholder.enterComment" var="enterComment" />
<fmt:message bundle="${local}" key="local.button.save" var="save" />
<fmt:message bundle="${local}" key="local.commentsInfo" var="commentsInfo" />
<fmt:message bundle="${local}" key="local.button.submit" var="submit" />
<fmt:message bundle="${local}" key="local.a.edit" var="edit" />
<fmt:message bundle="${local}" key="local.a.delete" var="delete" />
<fmt:message bundle="${local}" key="local.h1.noCommentsInfo" var="noCommentsInfo" />

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div>
    <table style="color: #b3d4fc;">
        <tr style="align-content: center">
            <td><input type="hidden" name="${track.id}"/></td>
            <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="header__cart-pic"></td>
            <td>${track.title}</td>
            <c:forEach items="${track.artists}" var="artist">
                <input type="hidden" name="${artist.id}"/>
                <td>${artist.name}</td>
            </c:forEach>
            <td>$ ${track.price} </td>

            <c:if test="${track.status == 'PURCHASED'}">
                <td class="purchased">${purhased}</td>
            </c:if>
            <c:if test="${track.status == 'AVAILABLE'}">
                <td><a class="header__link__button"
                       href="<c:url value='controller?command=addTrack&id=${track.id}'/>">${add}</a></td>
            </c:if>
            <c:if test="${track.status == 'ORDERED'}">
                <td><a class="header__link"
                       href="<c:url value='controller?command=deleteTrack&id=${track.id}'/>">${delete}</a></td>
            </c:if>
            </td>
        </tr>
    </table>
</div>
<div class="container-comments">
<c:choose>
    <c:when test="${buttonEdit}">
        <form action="/musicwebapp/controller?command=saveEditedComment&commentId=${commentId}" method="post">
            <input type="hidden" name="${commentId}"/>
            <textarea class="comments-textarea" placeholder="${enterComment}" name="commentContent">${editableContent}</textarea>
        <br>
        <button class="button-comment" type="submit">${save}</button>
        </form>
    </c:when>
    <c:otherwise>

        <form action="/musicwebapp/controller?command=addComment&id=${track.id}" method="post">
            <textarea class="comments-textarea" placeholder="${enterComment}" name="commentContent" ></textarea>
            <br>
            <button class="button-comment" type="submit">${submit}</button>
        </form>

    </c:otherwise>
</c:choose>

    <h1 class="headlines_type">${commentsInfo}</h1>
    <c:choose>
        <c:when test="${not empty comments}">
            <c:forEach items="${comments}" var="comment">
                <div class="container__comment">
                    <input type="hidden" name="${comment.id}"/>
                    <input type="hidden" name="${comment.userId}"/>
                    <p class="p-comment-user"><b>${comment.name} ${comment.lastname}</b></p>
                    <p class="p-comment-date">${comment.commentDate}</p>
                    <p class="p-comment-content">${comment.content}</p>
                    <div class="edit-delete-link" >
                        <c:if test="${comment.currentUserAuthor}">
                            <p>
                                <a class="comment-link" href="<c:url value='controller?command=editComment&id=${comment.id}&trackId=${track.id}'/>">${edit}</a>
                            |
                            <a class="comment-link" href="<c:url value='controller?command=deleteComment&id=${comment.id}'/>">
                                ${delete}</a></p>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p class="refill_balance_headline">${noCommentsInfo}</p>
        </c:otherwise>
    </c:choose>
</div>
<br>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
