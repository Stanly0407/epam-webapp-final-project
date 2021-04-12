package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AdminTrackListCommand implements Command {

    private static final String ADMIN_TRACK_LIST_PAGE = "/WEB-INF/view/adminPages/adminTrackListPage.jsp";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";

    private final TrackService trackService;

    public AdminTrackListCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<Track> trackList = trackService.getAllTracks();
        request.setAttribute(ATTRIBUTE_TRACK_LIST, trackList);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        return CommandResult.forward(ADMIN_TRACK_LIST_PAGE);
    }
}
