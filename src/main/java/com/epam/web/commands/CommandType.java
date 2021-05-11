package com.epam.web.commands;

public enum CommandType {

    LOGIN("login"),
    LOGIN_PAGE("loginPage"),

    LOGOUT("logout"),
    CHANGE_LANGUAGE("changeLanguage"),

    ADMIN_MAIN_PAGE("adminMainPage"),
    ADMIN_TRACK_LIST("adminTrackList"),
    EDIT_TRACK("editTrack"),
    TRACK_FORM("trackForm"),
    ADD_EDIT_TRACK("addEditTrack"),
    ARTIST_FORM("artistForm"),
    ADD_NEW_ARTIST("addNewArtist"),
    ALBUM_FORM("albumForm"),
    ADD_NEW_ALBUM("addNewAlbum"),
    PLAYLIST_FORM("playlistForm"),
    ADD_NEW_PLAYLIST("addNewPlaylist"),
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
    PAYMENT_HISTORY("paymentHistory"),
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

    USE_DISCOUNT("useDiscount"),
    USE_BONUS_FREE_TRACKS("useBonusFreeTracks");

    private String value;

    CommandType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }


}
