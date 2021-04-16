<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


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
                <ul class="header__list">
                    <li class="header__item">
                        <select class="select_header">
                            <option data-content="English">en</option>
                            <option data-content="Russian">ru</option>
                            <option data-content="Belorussian">be</option>
                        </select>
                    </li>
                    <c:if test="${empty name}">
                        <li class="header__item">
                            <a href="/musicwebapp/controller?command=aboutUs" class="header__link">About us</a>
                        </li>
                    </c:if>

                    <c:if test="${not empty name}">
                        <li class="header__item">
                            <a href="/musicwebapp/controller?command=allMusic" class="header__link">All music</a>
                        </li>
                        <li class="header__item">
                            <a href="/musicwebapp/controller?command=userMusic" class="header__link__button">My
                                music</a>
                        </li>
                        <li>
                            <a href="/musicwebapp/controller?command=cart">
                                <img src="img/svg/Cart_header.svg" alt="Cart" class="header__cart-pic">
                            </a>
                        </li>
                        <li class="header__item">
                            <a href="/musicwebapp/controller?command=userAccount" class="header__link">Account</a>
                        </li>
                        <li class="header__item">
                            <a href="/musicwebapp/controller?command=logout" class="header__link">Log out</a>
                        </li>
                    </c:if>

                </ul>
            </nav>
        </div>
    </div>
</header>
</body>