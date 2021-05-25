package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class CollectionMusicCommand implements Command {
    private static final String ARTIST_TRACKS_PAGE_COMMAND = "/WEB-INF/view/userPages/userTrackListPage.jsp";
    private static final String ATTRIBUTE_TRACKS = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String USER_ID = "userId";
    private static final String COLLECTION_ID = "id";

    private final TrackService trackService;

    public CollectionMusicCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        String collectionIdString = request.getParameter(COLLECTION_ID);
        Long collectionId = Long.valueOf(collectionIdString);
        List<TrackDto> trackList = trackService.getCollectionTracks(collectionId, userId);
        if (!trackList.isEmpty()) {
            request.setAttribute(ATTRIBUTE_TRACKS, trackList);
            request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        }
        return CommandResult.forward(ARTIST_TRACKS_PAGE_COMMAND);
    }
}
