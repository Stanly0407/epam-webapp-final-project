<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.local}" />
<fmt:setBundle basename="pagecontent" var="local" />
<fmt:message bundle="${local}" key="local.placeholder.startSearching" var="startSearching" />
<fmt:message bundle="${local}" key="local.button.search" var="SearchButton" />
<fmt:message bundle="${local}" key="local.radio.track" var="Track" />
<fmt:message bundle="${local}" key="local.radio.artist" var="Artist" />
<fmt:message bundle="${local}" key="local.radio.album" var="Album" />
<fmt:message bundle="${local}" key="local.radio.playlist" var="Playlist" />


<div>
    <form action="/musicwebapp/controller?command=searchMusic" method="post">
        <div class="search___block">
            <input class="search-input" type="text" id="searchMusic"
                   placeholder="${startSearching}"
                   name="searchSubject" required/>
            <button class="button-main" type="submit">${SearchButton}</button>

            <div class="form_radio_group">
                <div class="form_radio_group-item">
                    <input id="radio-1" type="radio" value="Track" name="searchCondition" checked>
                    <label for="radio-1">${Track}</label>
                </div>
                <div class="form_radio_group-item">
                    <input id="radio-2" type="radio" value="Artist" name="searchCondition">
                    <label for="radio-2">${Artist}</label>
                </div>
                <div class="form_radio_group-item">
                    <input id="radio-3" type="radio" value="Album" name="searchCondition">
                    <label for="radio-3">${Album}</label>
                </div>
                <div class="form_radio_group-item">
                    <input id="radio-4" type="radio" value="Playlist" name="searchCondition">
                    <label for="radio-4">${Playlist}</label>
                </div>
            </div>
        </div>
    </form>
</div>