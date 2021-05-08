<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.local}"/>
<fmt:setBundle basename="pagecontent" var="local"/>

<fmt:message bundle="${local}" key="local.aboutUs" var="aboutUs"/>
<fmt:message bundle="${local}" key="local.allArtists" var="allArtistsLink"/>
<fmt:message bundle="${local}" key="local.allMusic" var="allMusicLink"/>
<fmt:message bundle="${local}" key="local.button.myMusic" var="myMusicButton"/>
<fmt:message bundle="${local}" key="local.account" var="accountLink"/>
<fmt:message bundle="${local}" key="local.logOut" var="logOut"/>

<head>
    <title>Music Wizard</title>
    <link rel="stylesheet" href="static/reset.css">
    <link rel="stylesheet" href="static/style.css"/>
</head>
<body>
<header class="header">
    <div class="wrapper">
        <div class="header__wrapper">
            <a href="/musicwebapp/controller?command=userMainPage">
                <img src="img/svg/Logo_1-01.svg" alt="Music Wizard" class="header__logo-pic">
            </a>

            <nav class="header__nav">

                <ul class="header__list_2">

                    <c:if test="${sessionScope.role eq 'USER'}">
                    <li class="header__item">
                        <a href="/musicwebapp/controller?command=allMusic" class="header__link">${allMusicLink}</a>
                    </li>
                    <li class="header__item">
                        <a href="/musicwebapp/controller?command=allArtists"
                           class="header__link">${allArtistsLink}</a>
                    </li>
                    <li class="header__item">
                        <a href="/musicwebapp/controller?command=userAccount">
                            <a href="/musicwebapp/controller?command=allMusic" class="header__link">Альбомы</a>
                        </a>
                    </li>
                    <li class="header__item">
                        <a href="/musicwebapp/controller?command=allMusic" class="header__link">Плейлисты</a>
                    </li>
                </ul>
            </nav>

            <nav class="header__nav" style="margin-left: 350px;">
                <ul class="header__list">

                    <li class="header__item">
                        <a href="/musicwebapp/controller?command=userMusic"
                           class="header__link__button">${myMusicButton}</a>
                    </li>
                    <li>
                        <a href="/musicwebapp/controller?command=cart">
                            <img src="img/svg/Cart_header.svg" alt="Cart" class="header__cart-pic">
                        </a>
                    </li>
                    <li >
                        <a href="/musicwebapp/controller?command=userAccount">
                            <img src="img/svg/user_icon.svg" title="${allMusicLink}" alt="Cart"
                                 class="header__cart-pic">
                        </a>
                    </li>
                    </c:if>
                    <c:if test="${not empty sessionScope.role}">
                        <li style="margin-right: 22px;">
                            <a href="/musicwebapp/controller?command=logout" class="header__link">${logOut}</a>
                        </li>
                    </c:if>

                    <li class="header__item">
                        <a style="text-decoration: none"
                           href="/musicwebapp/controller?command=changeLanguage&language=ru">
                            <img src="img/svg/russia-lang.svg" alt="Lang" class="img_lang">
                        </a>
                        <a style="text-decoration: none"
                           href="/musicwebapp/controller?command=changeLanguage&language=en">
                            <img src="img/svg/united_kingdom-lang.svg" alt="Lang" class="img_lang">
                        </a>
                        <a style="text-decoration: none"
                           href="/musicwebapp/controller?command=changeLanguage&language=de">
                            <img src="img/svg/germany-lang.svg" alt="Lang" class="img_lang">
                        </a>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
</body>