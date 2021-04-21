package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dao.MusicCollectionDao;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ArtistMusicCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ArtistMusicCommand.class);
    private static final String ARTIST_TRACKS_PAGE_COMMAND = "/WEB-INF/view/userPages/userTrackListPage.jsp";
    private static final String ATTRIBUTE_ARTIST_TRACKS = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String USER_ID = "userId";
    private static final String ARTIST_ID = "id";

    private final TrackService trackService;

    public ArtistMusicCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        String artistIdString = request.getParameter(ARTIST_ID);
        Long artistId = Long.valueOf(artistIdString);
        List<TrackDto> trackList = trackService.getArtistTracks(artistId, userId);
        if (!trackList.isEmpty()) {
            request.setAttribute(ATTRIBUTE_ARTIST_TRACKS, trackList);
            request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        }
        return CommandResult.forward(ARTIST_TRACKS_PAGE_COMMAND);
    }
}
