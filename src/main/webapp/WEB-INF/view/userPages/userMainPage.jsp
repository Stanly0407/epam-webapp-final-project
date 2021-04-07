<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container-login" align="center">

    <b style="color: aliceblue">Hello, dear ${name}!</b><br>
    <br/><br/>

    <div class="common-label"><label for="searchMusic">Search music</label></div>

    <div class="search___block">
        <form action="/musicwebapp/controller?command=searchMusic" method="post">

        <input class="common-input" type="text" id="searchMusic" placeholder="Track, artist, album, collection" name="searchSubject" required/>

        <select name="searchCondition">
            <option selected value="Track">Track</option>
            <option value="Artist">Artist</option>
            <option value="Album">Album</option>
            <option value="Collection">Collection</option>
        </select>

        <button class="button-main" type="submit">Search</button>
    </form>
</div>

    <br/><br/>    <br/><br/>


    <div class="common-label"><label for="searchMusic">New Tracks</label></div>
    <div>
        <table style="color: #b3d4fc;">
            <tr>
                <th width="1"></th>
                <th width="200">TITLE</th>
                <th width="250">DESCRIPTION</th>
                <th width="120">PRICE</th>
                <th width="120">ARTIST</th>

                <th width="80"></th>
                <th width="80"></th>
            </tr>
            <c:forEach items="${trackList}" var="track">
                <tr style="align-content: center">
                    <td>   <input type="hidden" name="${track.id}"/></td>
                    <td>${track.title}</td>
                    <td>${track.description}</td>
                    <td>${track.price} $</td>
                    <td>${track.artistName}</td>

                    <td><a class="header__link" href="<c:url value='controller?command=addTrackToCart&id=${track.id}'/>">ADD TO CART</a></td>
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
<%--                <th width="1"></th>--%>
<%--                <th width="200">TITLE</th>--%>
<%--                <th width="120">ARTIST</th>--%>

<%--                <th width="80"></th>--%>
            </tr>
            <c:forEach items="${albumList}" var="album">
                <tr style="align-content: center">
                    <td>   <input type="hidden" name="${album.id}"/></td>
                    <td>${album.title}</td>
                    <td>${album.artistName}</td>

                    <td><a class="header__link" href="<c:url value='controller?command=see&id=${album.id}'/>">SEE</a></td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>
    <br/><br/>

    <div class="common-label"><label for="searchMusic">New Collections</label></div>

    <div>
        <table style="color: #b3d4fc;">
            <tr>
<%--                <th width="1"></th>--%>
<%--                <th width="200">TITLE</th>--%>

<%--                <th width="80"></th>--%>
            </tr>
            <c:forEach items="${collectionList}" var="collection">
                <tr style="align-content: center">
                    <td>   <input type="hidden" name="${collection.id}"/></td>
                    <td>${collection.title}</td>

                    <td><a class="header__link" href="<c:url value='controller?command=addTrackToCart&id=${collection.id}'/>">SEE</a></td>
                    </td>
                </tr>
            </c:forEach>
        </table>
    </div>
    <br>

</div>

</body>
</html>
