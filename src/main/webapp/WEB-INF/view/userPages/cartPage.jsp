<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.h1.ordered" var="oderedTracksSum"/>
<fmt:message bundle="${local}" key="local.h1.emptyCart" var="emptyCartMessage"/>
<fmt:message bundle="${local}" key="local.totalAmount" var="totalAmountInfo"/>
<fmt:message bundle="${local}" key="local.button.pay" var="pay"/>

<html>
<body>
<div class="header">
    <jsp:include page="../fragments/header.jsp"/>
</div>
<div class="container-login">
    <h1 class="headlines--type">${oderedTracksSum}</h1>

    <c:if test="${empty trackList}">
        <h1 class="message_h1">${emptyCartMessage}</h1>
    <c:if test="${bonusMessage}">
        <h1 class="message_h1">У вас есть бонусы, закажите треки, чтобы узнать подробнее.</h1>
    </c:if>
    </c:if>


    <c:if test="${not empty trackList}">
        <jsp:include page="../fragments/trackList.jsp"/>
        <br/>

        <div>
                <br/>
                <c:if test="${bonusDiscountExist}">
                <label class="container" for="discount">У вас есть скидка в размере ${bonusDiscount.amount}%.
                    <a class="comment-link" href="<c:url value='controller?command=countDiscount&discountId=${bonusDiscount.id}'/>">
                    <c:if test="${sessionScope.activatedDiscountBonus}">
                        <input type="submit" class="header__link__button" id="discount" style="margin-left: 45%" name="Применить">
                    </c:if>
                        <c:if test="${!sessionScope.activatedDiscountBonus}">
                            <input type="submit" class="header__link__button" id="discount" style="margin-left: 45%" name="Отменить">
                        </c:if>
                    </a> </label>
                </c:if>
                <c:if test="${bonusFreeTracksExist}">
                    <label class="container" for="freeTracks">У вас есть возможность купить ${bonusFreeTracks.amount}
                        треков с наименьшей стоимостью из заказа бесплатно,
                        нажмите "применить", чтобы получить бесплатные треки в свою коллекцию музыки.
                        <a class="comment-link" href="<c:url value='controller?command=countFreeTracks&bonusId=${bonusDiscount.id}'/>">
                            <c:if test="${sessionScope.activatedFreeTracksBonus}">
                                <input type="submit" class="header__link__button" id="freeTracks" style="margin-left: 45%" name="Применить">
                            </c:if>
                            <c:if test="${!sessionScope.activatedFreeTracksBonus}">
                                <input type="submit" class="header__link__button" id="freeTracks" style="margin-left: 45%" name="Отменить">
                            </c:if>
                        </a> </label>
                </c:if>
                <c:if test="${checkMessage}">
                    <h1 class="message_h1">Для приобретения бесплатных треков необходимо, чтобы в корзине было столько
                        же или более треков.</h1>
                </c:if>
                <input type="submit" class="header__link__button" style="margin-left: 45%" name="Применить">
        </div>

        <div style="margin-left: 46%; margin-bottom: 40px" class="common-label">${totalAmountInfo} ${orderAmount}</div>


        <a href="/musicwebapp/controller?command=payOrder" class="header__link__button" style="margin-left: 45%">${pay}</a>

    </c:if>
</div>
<br>
</body>
</html>
