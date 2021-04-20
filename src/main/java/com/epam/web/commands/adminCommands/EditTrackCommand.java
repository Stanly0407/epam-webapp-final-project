package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTrackCommand implements Command {

    private static final String PARAMETER_TRACK_ID = "id";
    private static final String PARAMETER_RELEASE_DATE = "releaseDate";
    private static final String PARAMETER_TITLE = "title";
    private static final String PARAMETER_PRICE = "price";
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "/controller?command=adminTrackList";

    private final TrackService trackService;

    public EditTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String id = request.getParameter(PARAMETER_TRACK_ID);
        String releaseDate = request.getParameter(PARAMETER_RELEASE_DATE);
        String title = request.getParameter(PARAMETER_TITLE);
        String price = request.getParameter(PARAMETER_PRICE);
        trackService.editTrack(releaseDate, title, price, id);
        return CommandResult.redirect(SHOW_TRACK_LIST_PAGE_COMMAND);
    }
}
