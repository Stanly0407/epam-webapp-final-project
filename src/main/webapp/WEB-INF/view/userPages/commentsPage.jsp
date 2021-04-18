<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

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
                <td> Purchased</td>
            </c:if>
            <c:if test="${track.status == 'AVAILABLE'}">
                <td><a class="header__link" href="<c:url value='controller?command=addTrack&id=${track.id}'/>">Add to
                    Cart</a></td>
            </c:if>
            <c:if test="${track.status == 'ORDERED'}">
                <td><a class="header__link" href="<c:url value='controller?command=deleteTrack&id=${track.id}'/>">Delete
                    from Cart</a></td>
            </c:if>
            </td>
        </tr>
    </table>
</div>

<h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Comments:</h1>

<div class="container-comments">
<c:choose>
    <c:when test="${buttonEdit}">
        <form action="/musicwebapp/controller?command=saveEditedComment&commentId=${commentId}" method="post">
            <input type="hidden" name="${commentId}"/>
            <textarea class="comments-textarea" placeholder="Enter comment" name="commentContent">${editableContent}</textarea>
        <br>
        <button class="button-comment" type="submit">Save</button>
        </form>
    </c:when>
    <c:otherwise>
        <form action="/musicwebapp/controller?command=addComment&id=${track.id}" method="post">
            <textarea class="comments-textarea" placeholder="Enter comment" name="commentContent" ></textarea>
            <br>
            <button class="button-comment" type="submit">Submit</button>
        </form>
    </c:otherwise>
</c:choose>

    <br>
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
                                <a class="comment-link" href="<c:url value='controller?command=editComment&id=${comment.id}&trackId=${track.id}'/>">EDIT </a>
                            |
                            <a class="comment-link" href="<c:url value='controller?command=deleteComment&id=${comment.id}'/>">
                                DELETE</a></p>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:when>
        <c:otherwise>
            <p style="margin: auto">There are no comments here yet</p>
        </c:otherwise>
    </c:choose>
</div>
<br>
<jsp:include page="../fragments/footer.jsp"/>
</body>
</html>
