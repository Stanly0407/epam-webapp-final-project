<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div>
    <form action="/musicwebapp/controller?command=searchMusic" method="post">
        <div class="search___block">
            <input class="search-input" type="text" id="searchMusic"
                   placeholder="Start searching tracks, artists, albums or playlists..."
                   name="searchSubject" required/>
            <button class="button-main" type="submit">Search</button>

            <div class="form_radio_group">
                <div class="form_radio_group-item">
                    <input id="radio-1" type="radio" value="Track" name="searchCondition" checked>
                    <label for="radio-1">Track</label>
                </div>
                <div class="form_radio_group-item">
                    <input id="radio-2" type="radio" value="Artist" name="searchCondition">
                    <label for="radio-2">Artist</label>
                </div>
                <div class="form_radio_group-item">
                    <input id="radio-3" type="radio" value="Album" name="searchCondition">
                    <label for="radio-3">Album</label>
                </div>
                <div class="form_radio_group-item">
                    <input id="radio-4" type="radio" value="Playlist" name="searchCondition">
                    <label for="radio-4">Playlist</label>
                </div>
            </div>


        </div>
    </form>
</div>