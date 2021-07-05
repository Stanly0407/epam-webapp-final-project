<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=utf-8" isELIgnored="false" %>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>

<div style="padding-left: 50px;">
<jsp:include page="../fragments/search.jsp"/>
</div>

<jsp:include page="../fragments/pagination.jsp"/>

<jsp:include page="../fragments/trackList.jsp"/>

</body>
</html>
