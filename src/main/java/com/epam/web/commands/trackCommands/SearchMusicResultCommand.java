package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SearchMusicResultCommand implements Command {

    private static final String SHOW_USER_TRACK_LIST_PAGE_COMMAND = "/WEB-INF/view/userPages/userTrackListPage.jsp";
    private static final String SHOW_USER_COLLECTION_LIST_PAGE_COMMAND = "/WEB-INF/view/userPages/userCollectionListPage.jsp";
    private static final String ATTRIBUTE_SEARCH_SUBJECT = "searchSubject";
    private static final String ATTRIBUTE_SEARCH_CONDITION = "searchCondition";
    private static final String TRACK_SEARCH_CONDITION = "Track";
    private static final String ARTIST_SEARCH_CONDITION = "Artist";
    private static final String ALBUM_SEARCH_CONDITION = "Album";
    private static final String PLAYLIST_SEARCH_CONDITION = "Playlist";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_ALBUM_LIST = "albums";
    private static final String ATTRIBUTE_ALBUM = "album";
    private static final String ATTRIBUTE_PLAYLIST_LIST = "playlists";
    private static final String ATTRIBUTE_PLAYLIST = "playlist";
    private static final String USER_ID = "userId";

    private final TrackService trackService;
    private final MusicCollectionService musicCollectionService;

    public SearchMusicResultCommand(TrackService trackService, MusicCollectionService musicCollectionService) {
        this.trackService = trackService;
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        String searchSubject = (String) session.getAttribute(ATTRIBUTE_SEARCH_SUBJECT);
        String searchCondition = (String) session.getAttribute(ATTRIBUTE_SEARCH_CONDITION);
        String page;
        if (searchCondition.equals(TRACK_SEARCH_CONDITION) || searchCondition.equals(ARTIST_SEARCH_CONDITION)) {
            List<TrackDto> trackList = trackService.getMusicByCondition(searchSubject, searchCondition, userId);
            if (!trackList.isEmpty()) {
                request.setAttribute(ATTRIBUTE_TRACK_LIST, trackList);
                request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
            }
            page = SHOW_USER_TRACK_LIST_PAGE_COMMAND;
        } else {
            List<MusicCollection> collectionList = musicCollectionService.getMusicByCondition(searchSubject, searchCondition);
            if (!collectionList.isEmpty() && searchCondition.equals(ALBUM_SEARCH_CONDITION)) {
                request.setAttribute(ATTRIBUTE_ALBUM_LIST, collectionList);
                request.setAttribute(ATTRIBUTE_ALBUM, new MusicCollection());
            } else if (!collectionList.isEmpty() && searchCondition.equals(PLAYLIST_SEARCH_CONDITION)) {
                request.setAttribute(ATTRIBUTE_PLAYLIST_LIST, collectionList);
                request.setAttribute(ATTRIBUTE_PLAYLIST, new MusicCollection());
            }
            page = SHOW_USER_COLLECTION_LIST_PAGE_COMMAND;
        }
        return CommandResult.forward(page);
    }
}
