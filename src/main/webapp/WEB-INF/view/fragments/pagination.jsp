<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<div>
    <div class="pagination">
        <form action="controller?command=nextPage" method="post">
            <input type="hidden" name="currentPaginationPage" value="${currentPaginationPage}">

            <c:if test="${isPreviousPossible}">
                <input class="page-button" type="submit" name="pageAction" value="<<<">
            </c:if>

                <c:forEach items="${paginationList}" var="pageNumber">
                    <c:if test="${pageNumber == currentPaginationPage}">
                        <input class="active-page-button" type="submit" name="pageAction" value="${pageNumber}">
                    </c:if>
                    <c:if test="${pageNumber != currentPaginationPage}">
                        <input class="page-button" type="submit" name="pageAction" value="${pageNumber}">
                    </c:if>
                </c:forEach>

            <c:if test="${isNextPossible}">
                <input class="page-button" type="submit" name="pageAction" value=">>>">
            </c:if>
        </form>
    </div>
</div>