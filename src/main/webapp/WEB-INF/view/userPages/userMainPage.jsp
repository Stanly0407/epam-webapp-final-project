<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container-login" align="center">

    <b style="color: aliceblue">Hello, dear ${name}!</b><br>
    <br/>
    <jsp:include page="../fragments/search.jsp"/>



    <%--        <select class="select_search" name="searchCondition">--%>
    <%--            <option selected value="Track">Track</option>--%>
    <%--            <option value="Artist">Artist</option>--%>
    <%--            <option value="Album">Album</option>--%>
    <%--            <option value="Collection">Collection</option>--%>
    <%--        </select>--%>
    <br/><br/>

    <div class="common-label"><label>New Tracks</label></div>
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
            <c:forEach items="${trackList}" var="track">
                <tr style="align-content: center">
                    <td><input type="hidden" name="${track.id}"/></td>
                    <td>${track.title}</td>
                    <c:forEach items="${track.artists}" var="artist">
                        <input type="hidden" name="${artist.id}"/>
                        <td>${artist.name}</td>
                    </c:forEach>

                    <td>$ ${track.price} </td>

                    <c:if test="${track.status == 'PURCHASED'}">
                        <td> Purchased </td>
                    </c:if>
                    <c:if test="${track.status == 'AVAILABLE'}">
                        <td><a class="header__link" href="<c:url value='controller?command=addTrack&id=${track.id}'/>">Add to Cart</a></td>
                    </c:if>
                    <c:if test="${track.status == 'ORDERED'}">
                        <td><a class="header__link" href="<c:url value='controller?command=deleteTrack&id=${track.id}'/>">Delete from Cart</a></td>
                    </c:if>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>

    <br/><br/>

    <div class="common-label"><label for="searchMusic">New Albums</label></div>

    <div>
        <table style="color: #b3d4fc;">
            <tr>

            </tr>
            <c:forEach items="${albumList}" var="album">
                <tr style="align-content: center">
                    <td><input type="hidden" name="${album.id}"/></td>
                    <td>${album.title}</td>
                    <td>${album.artist.name}</td>
                    <td><a class="header__link" href="<c:url value='controller?command=see&id=${album.id}'/>">SEE</a>
                    </td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <br/><br/>

    <div class="common-label"><label for="searchMusic">New Playlists</label></div>

    <div>
        <table style="color: #b3d4fc;">
            <tr>

            </tr>
            <c:forEach items="${collectionList}" var="collection">
                <tr style="align-content: center">
                    <td><input type="hidden" name="${collection.id}"/></td>
                    <td>${collection.title}</td>

                    <td><a class="header__link"
                           href="<c:url value='controller?command=addTrackToCart&id=${collection.id}'/>">SEE</a></td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>

</div>

</body>
</html>
