package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CreateTrackCommand.class);

    private static final String PARAMETER_TITLE = "title";
    private static final String PARAMETER_DESCRIPTION = "description";
    private static final String PARAMETER_PRICE = "price";
    private static final String PARAMETER_FILENAME = "filename";
    private static final String PARAMETER_ARTIST_ID = "artistId";

    private static final String CONTROLLER_COMMAND = "/controller?command=";
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "trackListPage";

    private final TrackService trackService;

    public CreateTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String title = request.getParameter(PARAMETER_TITLE);
        String description = request.getParameter(PARAMETER_DESCRIPTION);
        String price = request.getParameter(PARAMETER_PRICE); // validate!!
        String filename = request.getParameter(PARAMETER_FILENAME);
        String artistId = request.getParameter(PARAMETER_ARTIST_ID); // artist name --- findArtistIdByName  -> transaction?

        trackService.createTrack(title, description, price, filename, artistId);

        return CommandResult.redirect(CONTROLLER_COMMAND + SHOW_TRACK_LIST_PAGE_COMMAND);
    }
}
