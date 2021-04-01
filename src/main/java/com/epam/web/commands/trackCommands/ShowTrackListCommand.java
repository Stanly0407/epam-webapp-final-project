package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.Track;
import com.epam.web.entities.TrackAndArtist;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowTrackListCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowTrackListCommand.class);

    private static final String TRACK_LIST_PAGE = "/WEB-INF/view/trackListPage.jsp";

    private static final String ATTRIBUTE_NAME = "trackList";
    private static final String ATTRIBUTE_NAME_TRACK= "track";

    private final TrackService trackService;

    public ShowTrackListCommand(TrackService trackService) {
        this.trackService = trackService;
    }


    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<Track> trackList = trackService.getAllTracks();

        request.setAttribute(ATTRIBUTE_NAME_TRACK, new Track());
        request.setAttribute(ATTRIBUTE_NAME, trackList);
        return CommandResult.forward(TRACK_LIST_PAGE);
    }
}
