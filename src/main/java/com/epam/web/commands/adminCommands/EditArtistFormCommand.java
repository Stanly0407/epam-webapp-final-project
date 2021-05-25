package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.Artist;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.ArtistService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class EditArtistFormCommand implements Command {
    private static final String EDIT_ARTIST_FORM_PAGE = "/WEB-INF/view/fragments/artistForm.jsp";
    private static final String ADMIN_MAIN_PAGE_PAGE = "/WEB-INF/view/adminPages/adminMainPage.jsp";
    private static final String PARAMETER_ARTIST_ID = "id";
    private static final String ATTRIBUTE_ARTIST = "artist";

    private final ArtistService artistService;

    public EditArtistFormCommand(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String page;
        String artistIdParameter = request.getParameter(PARAMETER_ARTIST_ID);
        if (artistIdParameter != null) {
            Long artistId = Long.valueOf(artistIdParameter);
            Artist artist = artistService.getArtistById(artistId);
            request.setAttribute(ATTRIBUTE_ARTIST, artist);
            page = EDIT_ARTIST_FORM_PAGE;
        } else {
            page = ADMIN_MAIN_PAGE_PAGE;
        }
        return CommandResult.forward(page);
    }
}
