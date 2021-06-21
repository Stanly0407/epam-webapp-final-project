package com.epam.web.commands;

public enum CommandType {

    LOGIN("login"),
    LOGIN_PAGE("loginPage"),
    LOGOUT("logout"),
    CHANGE_LANGUAGE("changeLanguage"),
    ADMIN_MAIN_PAGE("adminMainPage"),
    EDIT_TRACK("editTrack"),
    TRACK_FORM("trackForm"),
    ADD_EDIT_TRACK("addEditTrack"),
    ARTIST_FORM("artistForm"),
    EDIT_ARTIST("editArtist"),
    ADD_EDIT_ARTIST("addEditArtist"),
    ALBUM_FORM("albumForm"),
    ADD_EDIT_ALBUM("addEditAlbum"),
    EDIT_ALBUM("editAlbum"),
    DELETE_ALBUM_TRACK("deleteAlbumTrack"),
    DELETE_PLAYLIST_TRACK("deletePlaylistTrack"),
    PLAYLIST_FORM("playlistForm"),
    EDIT_PLAYLIST("editPlaylist"),
    ADD_EDIT_PLAYLIST("addEditPlaylist"),
    ADD_TO_ALBUM("addToAlbum"),
    ADD_TO_PLAYLIST("addToPlaylist"),
    CHOOSE_ALBUM("chooseAlbum"),
    CHOOSE_PLAYLIST("choosePlaylist"),
    USER_LIST("userList"),
    CHANGE_USER_STATUS("changeUserStatus"),
    DELETE_TRACK("deleteTrack"),
    DELETE_TRACK_PREVENTING("deleteTrackPreventing"),
    DELETE_USER_COMMENT("deleteUserComment"),
    USER_MAIN_PAGE("userMainPage"),
    USER_ACCOUNT("userAccount"),
    REFILL_BALANCE_PAGE("refillBalancePage"),
    REFILL_BALANCE("refillBalance"),
    SEARCH_MUSIC("searchMusic"),
    SEARCH_MUSIC_RESULT("searchMusicResult"),
    USER_MUSIC("userMusic"),
    COMMENTS_PAGE("commentsPage"),
    CART("cart"),
    ADD_TRACK("addTrack"),
    DELETE_TRACK_FROM_CART("deleteTrackFromCart"),
    PAY_ORDER("payOrder"),
    PURCHASED_TRACKS("purchasedTracks"),
    ALL_MUSIC("allMusic"),
    NEXT_PAGE("nextPage"),
    ADD_COMMENT("addComment"),
    EDIT_COMMENT("editComment"),
    DELETE_COMMENT("deleteComment"),
    SAVE_EDITED_COMMENT("saveEditedComment"),
    ALL_ARTISTS("allArtists"),
    ARTIST_MUSIC("artistMusic"),
    COLLECTION_MUSIC("collectionMusic"),
    ALL_ALBUMS("allAlbums"),
    ALL_PLAYLISTS("allPlaylists"),
    ACTIVATE_DISCOUNT("activateDiscount"),
    DEACTIVATE_DISCOUNT("deactivateDiscount"),
    ACTIVATE_FREE_TRACKS("activateFreeTracks"),
    DEACTIVATE_FREE_TRACKS("deactivateFreeTracks"),
    ADD_DISCOUNT("addDiscount"),
    DELETE_BONUS("deleteBonus"),
    ADD_FREE_TRACKS("addFreeTracks");

    private String value;

    CommandType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
