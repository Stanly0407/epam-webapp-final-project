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

public class AllMusicCommand implements Command {
    private static final String ALL_MUSIC_PAGE_COMMAND = "/WEB-INF/view/userPages/allMusicPage.jsp";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_ALBUM_LIST = "albums";
    private static final String ATTRIBUTE_ALBUM = "album";
    private static final String ATTRIBUTE_PLAYLIST_LIST = "playlists";
    private static final String ATTRIBUTE_PLAYLIST = "playlist";
    private static final String USER_ID = "userId";

    private final TrackService trackService;
    private final MusicCollectionService musicCollectionService;

    public AllMusicCommand(TrackService trackService, MusicCollectionService musicCollectionService) {
        this.trackService = trackService;
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        List<TrackDto> trackList = trackService.getAllTracks(userId);
        request.setAttribute(ATTRIBUTE_TRACK_LIST, trackList);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        List<MusicCollection> albums = musicCollectionService.getAllAlbums();
        request.setAttribute(ATTRIBUTE_ALBUM_LIST, albums);
        request.setAttribute(ATTRIBUTE_ALBUM, new MusicCollection());
        List<MusicCollection> playlists = musicCollectionService.getAllPlaylists();
        request.setAttribute(ATTRIBUTE_PLAYLIST_LIST, playlists);
        request.setAttribute(ATTRIBUTE_PLAYLIST, new MusicCollection());
        return CommandResult.forward(ALL_MUSIC_PAGE_COMMAND);
    }
}
