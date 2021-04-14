<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<h1 style="margin-left: 50%; margin-top: 40px; margin-bottom: 50px">Ordered tracks:</h1>

<c:if test="${not empty orderedTracks}">
    <div>
        <table style="color: #b3d4fc;">
            <tr>
                <th width="1"></th>
                <th width="200">TITLE</th>
                <th width="1"></th>
                <th width="120">ARTIST</th>
                <th width="120">PRICE</th>

                <th width="80"></th>
            </tr>
            <c:forEach items="${orderedTracks}" var="track">
                <tr style="align-content: center">
                    <td><input type="hidden" name="${track.id}"/></td>
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
                        <td><a class="header__link" href="<c:url value='controller?command=addTrack&id=${track.id}'/>">Add
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

<br/>

<div style="margin-left: 50%;" class="common-label">Total amount: ${orderAmount}</div>

<a href="/musicwebapp/controller?command=payOrder" class="header__link__button">PAY</a>

</c:if>

</body>
</html>
