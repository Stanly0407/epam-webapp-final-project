<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div align="center" >
<div class="common-label"><label for="searchMusic">Search music</label></div>
<form action="/musicwebapp/controller?command=searchMusic" method="post">
    <div class="search___block">
        <input class="common-input" type="text" id="searchMusic" placeholder="Track, artist, album or playlist..."
               name="searchSubject" required/>
        <button class="button-main" type="submit">Search</button>
    </div>
    <br/>

    <div class="search___radio">
        <input type="radio" checked="checked" value="Track" name="searchCondition"> Track<br>
        <input type="radio" value="Artist" name="searchCondition"> Artist<br>
        <input type="radio" value="Album" name="searchCondition"> Album<br>
        <input type="radio" value="Playlist" name="searchCondition"> Playlist<br>
    </div>
</form>
</div>