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

    <div class="search___block">  <form action="/musicwebapp/controller?command=searchMusic" method="post">

        <input class="common-input" type="text" id="searchMusic" placeholder="Track, artist, album, collection" name="condition" required/>

        <select name="searchCondition">
            <option selected value="Track">Track</option>
            <option value="Artist">Artist</option>
            <option value="Album">Album</option>
            <option value="Collection">Collection</option>
        </select>

        <button class="button-main" type="submit">Search</button>
    </form>
</div>

</div>

</body>
</html>
