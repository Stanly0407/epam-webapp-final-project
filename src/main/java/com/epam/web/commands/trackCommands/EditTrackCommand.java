package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EditTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(EditTrackCommand.class);
    private static final String PARAMETER_ID = "id";
    private static final String PARAMETER_TITLE = "title";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_PRICE = "price";
    private static final String PARAMETER_FILENAME = "filename";
    private static final String PARAMETER_ARTIST_ID = "artistId";

    private static final String CONTROLLER_COMMAND = "/controller?command=";
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "trackList";

    private final TrackService trackService;

    public EditTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String id = request.getParameter(PARAMETER_ID);
        String title = request.getParameter(PARAMETER_TITLE);
        String description = request.getParameter(PARAMETER_DESCRIPTION);
        String price = request.getParameter(PARAMETER_PRICE);
        String filename = request.getParameter(PARAMETER_FILENAME);
        LOGGER.debug("PARAMETER_ID " + id);
        trackService.editTrack(title, description, price, filename, id);

        return CommandResult.redirect(CONTROLLER_COMMAND + SHOW_TRACK_LIST_PAGE_COMMAND);
    }
}
