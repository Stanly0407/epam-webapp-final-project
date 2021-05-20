<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>
<fmt:message bundle="${local}" key="local.h1.ordered" var="oderedTracksSum"/>
<fmt:message bundle="${local}" key="local.h1.emptyCart" var="emptyCartMessage"/>
<fmt:message bundle="${local}" key="local.totalAmount" var="totalAmountInfo"/>
<fmt:message bundle="${local}" key="local.button.pay" var="pay"/>
<fmt:message bundle="${local}" key="bonus.common.info" var="bonusInfoMessage"/>
<fmt:message bundle="${local}" key="bonus.discount.info" var="discountInfo"/>
<fmt:message bundle="${local}" key="bonus.freeTrackInfo.info" var="bonusFreeTracksInfoFirst"/>
<fmt:message bundle="${local}" key="bonus.button.activate" var="activateBonus"/>
<fmt:message bundle="${local}" key="bonus.button.deactivate" var="deactivateBonus"/>
<fmt:message bundle="${local}" key="bonus.freeTrackInfo.checkInfo" var="checkFreeTracksBonusInfo"/>

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
            <h1 class="message_h1">${bonusInfoMessage}</h1>
        </c:if>
    </c:if>

    <c:if test="${not empty trackList}">
        <jsp:include page="../fragments/trackList.jsp"/>
    </c:if>

    <c:if test="${bonusDiscountExist}">
        <div style="display: flex;">
            <div>
                <label class="bonus_label" for="discount">
                    <b>${discountInfo} ${bonusDiscount.amount}%.</b>
                </label>
            </div>
            <div>
                <c:if test="${sessionScope.activatedDiscountBonus}">
                    <a href="/musicwebapp/controller?command=deactivateDiscount" id="discount"
                       class="header__link__button"
                       style="margin-left: 15px">${deactivateBonus}</a>
                </c:if>
                <c:if test="${!sessionScope.activatedDiscountBonus || empty sessionScope.activatedDiscountBonus}">
                    <a href="/musicwebapp/controller?command=activateDiscount" id="discount"
                       class="header__link__button"
                       style="margin-left: 15px">${activateBonus}</a>
                </c:if>
            </div>
        </div>
    </c:if>

    <c:if test="${bonusFreeTracksExist}">
        <div style="display: flex;">
            <div>
                <label class="bonus_label" for="freeTracks">
                    <b style="margin-right: 15px;">${bonusFreeTracksInfoFirst} ${bonusFreeTracks.amount}.</b>
                </label>
            </div>
            <div>
                <c:if test="${sessionScope.activatedFreeTracksBonus}">
                    <a href="/musicwebapp/controller?command=deactivateFreeTracks" id="freeTracks"
                       class="header__link__button">${deactivateBonus}</a>
                </c:if>
                <c:if test="${!sessionScope.activatedFreeTracksBonus || empty sessionScope.activatedFreeTracksBonus}">
                    <a href="/musicwebapp/controller?command=activateFreeTracks&amount=${bonusFreeTracks.amount}" id="freeTracks"
                       class="header__link__button">${activateBonus}</a>
                </c:if>
            </div>
        </div>
    </c:if>

    <c:if test="${checkMessage}">
        <h1 class="message_h1">${checkFreeTracksBonusInfo}.</h1>
    </c:if>

    <c:if test="${not empty trackList}">
        <div style="margin-left: 45%; margin-bottom: 40px; margin-top: 40px" class="common-label">${totalAmountInfo} ${orderAmount}</div>
        <div>
            <a href="/musicwebapp/controller?command=payOrder" class="header__link__button"
               style="margin-left: 45%">${pay}</a>
        </div>
    </c:if>

</div>
</body>
</html>
