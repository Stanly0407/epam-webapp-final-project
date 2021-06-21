package com.epam.web.commands.admin;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.Artist;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.ArtistService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class TrackFormPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(TrackFormPageCommand.class);
    private static final String SHOW_TRACK_EDIT_FORM_PAGE = "/WEB-INF/view/fragments/trackForm.jsp";
    private static final String ATTRIBUTE_ARTISTS = "artists";
    private static final String ATTRIBUTE_ARTIST = "artist";

    private final ArtistService artistService;

    public TrackFormPageCommand(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<Artist> artists = artistService.getAllArtists();
        LOGGER.debug("ATTRIBUTE_ARTISTS === " + artists);
        request.setAttribute(ATTRIBUTE_ARTISTS, artists);
        request.setAttribute(ATTRIBUTE_ARTIST, new Artist());
        return CommandResult.forward(SHOW_TRACK_EDIT_FORM_PAGE);
    }
}
