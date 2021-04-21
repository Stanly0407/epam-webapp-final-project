package com.epam.web.commands.userCommands;

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

public class UserMainPageCommand implements Command {

    private static final String USER_TRACK_LIST_PAGE = "/WEB-INF/view/userPages/userMainPage.jsp";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_ALBUM_LIST = "albums";
    private static final String ATTRIBUTE_ALBUM = "album";
    private static final String ATTRIBUTE_PLAYLIST_LIST = "playlists";
    private static final String ATTRIBUTE_PLAYLIST = "playlist";
    private static final String ALBUM_TYPE = "ALBUM";
    private static final String COLLECTION_TYPE = "PLAYLIST";
    private static final String USER_ID = "userId";

    private final TrackService trackService;
    private final MusicCollectionService musicCollectionService;

    public UserMainPageCommand(TrackService trackService, MusicCollectionService musicCollectionService) {
        this.trackService = trackService;
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        List<TrackDto> trackList = trackService.getNewTracks(userId);
        request.setAttribute(ATTRIBUTE_TRACK_LIST, trackList);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        List<MusicCollection> albums = musicCollectionService.getNewMusicCollections(ALBUM_TYPE);
        request.setAttribute(ATTRIBUTE_ALBUM_LIST, albums);
        request.setAttribute(ATTRIBUTE_ALBUM, new MusicCollection());
        List<MusicCollection> playlists = musicCollectionService.getNewMusicCollections(COLLECTION_TYPE);
        request.setAttribute(ATTRIBUTE_PLAYLIST_LIST, playlists);
        request.setAttribute(ATTRIBUTE_PLAYLIST, new MusicCollection());
        return CommandResult.forward(USER_TRACK_LIST_PAGE);
    }

}
