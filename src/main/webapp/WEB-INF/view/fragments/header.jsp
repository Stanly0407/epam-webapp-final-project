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

            <a href="../../../index.jsp">
                <img src="img/svg/Logo_1-01.svg" alt="Music Wizard" class="header__logo-pic">
            </a>

            <nav class="header__nav">
                <ul class="header__list">
                    <li class="header__item">
<%--<div class="header__select">--%>
                         <select>
                                <option  data-content="English">en</option>
                                <option  data-content="Russian">ru</option>
                                <option  data-content="Belorussian">be</option>
                            </select>

                        <%--        </div>--%>
                    </li>
                    <li class="header__item">
                        <a href="#!" class="header__link">Home</a>
                    </li>
                    <li class="header__item">
                        <a href="#!" class="header__link">About us</a>
                    </li>
<%--                    <li class="header__item">--%>
<%--                        <a href="#!" class="header__link">Log out</a>--%>
<%--                    </li>--%>
                </ul>
            </nav>
        </div>
    </div>
</header>
</body>