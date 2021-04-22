<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
    <%@include file="/WEB-INF/js/pageScripts.js"%>
</script>


<html>
<body>

<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div class="edit-track-form">
    <form  enctype='multipart/form-data' action="/musicwebapp/uploadNew?command=addNewTrack" method="post">
        <br/>
        <div class="common-label"><label for="releaseDate">Release Date</label></div>
        <input class="common-input" type="date" id="releaseDate" name="releaseDate" required/>
        <br/> <br/>
        <div class="common-label"><label for="title">Title</label></div>
        <input class="common-input" type="text" id="title" placeholder="Enter title" name="title" required/>
        <br/> <br/>
        <div class="common-label"><label for="price">Price</label></div>
        <input class="common-input" type="text" id="price" placeholder="Enter price" name="price" required/>
        <br/>
        <br/>
        <br/>
        <select class="select-artist" name="artistIds"  required>
            <c:forEach items="${artists}" var="artist">
                <option value="${artist.id}">${artist.name}</option>
            </c:forEach>
        </select>
        <br/>
        <br/>
        <br/>
        <div class="common-label"><label for="file">Click on the "Choose File" button to upload a file:</label></div>
        <label class="change_password_button">
            <input name="filename" id="file" type="file" required accept=".mp3"/>
            Choose File
        </label>
        <br/>
        <br/>
        <button class="button-main" type="submit" >SAVE</button>
    </form>
    <br/>
<%--    <form>--%>
<%--        <label for="file-upload" class="custom-file-upload">--%>
<%--            <i class="fa fa-cloud-upload"></i> Upload Image--%>
<%--        </label>--%>
<%--        <input id="file-upload" name='upload_cont_img' type="file" style="display:none;">--%>
<%--    </form>--%>
    <br/>
</div>
</body>
</html>
