package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.ArtistService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


public class EditTrackFormCommand implements Command {

    private static final String EDIT_TRACK_FORM_PAGE = "/WEB-INF/view/fragments/trackForm.jsp";
    private static final String ADMIN_MAIN_PAGE_PAGE = "/WEB-INF/view/adminPages/adminMainPage.jsp";
    private static final String PARAMETER_TRACK_ID = "id";
    private static final String PARAMETER_USER_ID = "userId";
    private static final String ATTRIBUTE_NAME_TRACK = "track";
    private static final String ATTRIBUTE_ARTISTS = "artists";
    private static final String ATTRIBUTE_ARTIST = "artist";

    private final TrackService trackService;
    private final ArtistService artistService;


    public EditTrackFormCommand(TrackService trackService, ArtistService artistService) {
        this.trackService = trackService;
        this.artistService = artistService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String page;
        String trackIdParameter = request.getParameter(PARAMETER_TRACK_ID);
        if (trackIdParameter == null) {
            page = ADMIN_MAIN_PAGE_PAGE;
        } else {
            page = EDIT_TRACK_FORM_PAGE;
            Long trackId = Long.valueOf(trackIdParameter);
            Long userId = (Long) session.getAttribute(PARAMETER_USER_ID);
            List<Artist> artists = artistService.getAllArtists();
            request.setAttribute(ATTRIBUTE_ARTISTS, artists);
            request.setAttribute(ATTRIBUTE_ARTIST, new Artist());
            TrackDto track = trackService.getTrackDtoById(trackId, userId);
            request.setAttribute(ATTRIBUTE_NAME_TRACK, track);
        }
        return CommandResult.forward(page);
    }
}
