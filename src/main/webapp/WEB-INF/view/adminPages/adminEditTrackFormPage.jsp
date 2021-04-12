<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>

<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>


<div class="container-login">
    <h1 align="center" style="margin-bottom: 35px">Edit track: </h1>

    <form action="/musicwebapp/controller?command=editSaveTrack" method="post">
    <%--    <input type="hidden" name="command" value="editSaveTrack">--%>
    <table>
        <tr>
            <td>ID:</td>
            <td><input class="common-input" type="text" name="id" readonly value="${track.id}"/></td>
        <tr>
            <td>Title:</td>
            <td><input class="common-input" type="text" name="title" value="${track.title}"/></td>
        </tr>
        <tr>
            <td>Description:</td>
            <td><input class="common-input" type="text" name="description" value="${track.description}"/></td>
        </tr>
        <tr>
            <td>Price:</td>
            <td><input class="common-input" type="text" name="price" value="${track.price}"/> $ </td>
        </tr>
        <tr>
            <td>Filename:</td>
            <td><input class="common-input" type="text" name="price" value="${track.filename}" /> </td>
        </tr>
        <tr>
            <td>Artist Id:</td>
            <td><input class="common-input" type="text" name="artistId" readonly value="${track.artistId}"/></td>
        </tr>
    </table>
        <button class="button__center" type="submit" >Save</button>
</form>
</div>
</body>
</html>