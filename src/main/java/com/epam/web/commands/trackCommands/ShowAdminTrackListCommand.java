package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class ShowAdminTrackListCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowAdminTrackListCommand.class);

    private static final String TRACK_LIST_PAGE = "/WEB-INF/view/adminTrackListPage.jsp";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK= "track";

    private final TrackService trackService;

    public ShowAdminTrackListCommand(TrackService trackService) {
        this.trackService = trackService;
    }


    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<TrackDto> trackList = trackService.getAllTracks();
        request.setAttribute(ATTRIBUTE_TRACK_LIST, trackList);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        return CommandResult.forward(TRACK_LIST_PAGE);
    }
}
