<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div>
    <div>
        <form action="controller?command=nextPage" method="post">
            <input type="hidden" name="rowcount" value="${currentPaginationPage}">
            <c:if test="${isPreviousPossible}">
                <input type="submit" name="page" value="previous">
            </c:if>
            <div class="form_radio_group">
                <c:forEach items="${paginationList}" var="pageNumber">
                    <c:when test="${pageNumber == currentPaginationPage}">
                        <input class="active-page-button" type="submit" name="page" value="${pageNumber}">
                    </c:when>
                    <c:otherwise>
                        <input class="page-button" type="submit" name="page" value="${pageNumber}">
                    </c:otherwise>
                </c:forEach>
            </div>
            <c:if test="${isNextPossible}">
                <input type="submit" name="page" value="next">
            </c:if>
        </form>
    </div>
</div>