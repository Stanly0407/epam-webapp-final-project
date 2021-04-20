<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${not empty trackList}">

    <div>
        <table style="color: #b3d4fc;">
            <c:forEach items="${trackList}" var="track">
                <tr style="align-content: center">
                    <td><input type="hidden" name="${track.id}"/></td>
                    <td><img src="img/svg/Note_song icon.svg" alt="Cart" class="header__cart-pic"></td>
                    <td>${track.title}</td>
                    <c:forEach items="${track.artists}" var="artist">
                        <input type="hidden" name="${artist.id}"/>
                        <td>${artist.name}</td>
                    </c:forEach>

                    <td>$ ${track.price} </td>

                    <td>
                        <a class="header__link" href="<c:url value='controller?command=commentsPage&id=${track.id}'/>">
                            <img src="img/svg/comments.svg" alt="Comments" class="header__cart-pic">
                                ${track.commentsAmount}
                        </a>
                    </td>

                    <c:if test="${track.status == 'PURCHASED'}">
                        <td class="purchased"> Purchased</td>
                    </c:if>
                    <c:if test="${track.status == 'AVAILABLE'}">
                        <td><a class="header__link__button" href="<c:url value='controller?command=addTrack&id=${track.id}'/>">Add
                            to Cart</a></td>
                    </c:if>
                    <c:if test="${track.status == 'ORDERED'}">
                        <td><a class="header__link"
                               href="<c:url value='controller?command=deleteTrack&id=${track.id}'/>">Delete from
                            Cart</a></td>
                    </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
</c:if>