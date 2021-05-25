package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTrackCommand implements Command {

    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "/controller?command=allMusic";
    private static final String PARAMETER_TRACK_ID = "trackId";

    private final TrackService trackService;

    public DeleteTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String trackId = request.getParameter(PARAMETER_TRACK_ID);
        trackService.deleteTrackById(trackId);
        return CommandResult.redirect(SHOW_TRACK_LIST_PAGE_COMMAND);
    }
}
