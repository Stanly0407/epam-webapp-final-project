<%@ page contentType="text/html;charset=utf-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container" align="center">

        <b style="color: aliceblue">Hello, ${name}!</b><br>

    <button class="button-main" type="submit"  >
            <a href="/musicwebapp/controller?command=adminTrackList" class="header__link__button" >trackList</a>
    </button>

</div>

</body>
</html>
