package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.MusicCollectionDto;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowUserMainPageCommand implements Command {

    private static final String USER_TRACK_LIST_PAGE = "/WEB-INF/view/userPages/userMainPage.jsp";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_ALBUM_LIST = "albumList";
    private static final String ATTRIBUTE_ALBUM = "album";
    private static final String ATTRIBUTE_COLLECTION_LIST = "collectionList";
    private static final String ATTRIBUTE_COLLECTION = "collection";

    private static final String ALBUM_TYPE = "ALBUM";
    private static final String COLLECTION_TYPE = "COLLECTION";

    private final TrackService trackService;
    private final MusicCollectionService musicCollectionService;

    public ShowUserMainPageCommand(TrackService trackService, MusicCollectionService musicCollectionService) {
        this.trackService = trackService;
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<TrackDto> trackList = trackService.getNewTracks();
        request.setAttribute(ATTRIBUTE_TRACK_LIST, trackList);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        List<MusicCollectionDto> albumList = musicCollectionService.getNewMusicCollections(ALBUM_TYPE);
        request.setAttribute(ATTRIBUTE_ALBUM_LIST, albumList);
        request.setAttribute(ATTRIBUTE_ALBUM, new MusicCollectionDto());
        List<MusicCollectionDto> collectionList = musicCollectionService.getNewMusicCollections(COLLECTION_TYPE);
        request.setAttribute(ATTRIBUTE_COLLECTION_LIST, collectionList);
        request.setAttribute(ATTRIBUTE_COLLECTION, new MusicCollectionDto());
        return CommandResult.forward(USER_TRACK_LIST_PAGE);
    }

}
