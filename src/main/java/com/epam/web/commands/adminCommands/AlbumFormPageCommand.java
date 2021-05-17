package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.Artist;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.ArtistService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AlbumFormPageCommand implements Command {
    private static final String SHOW_ADD_ALBUM_FORM_PAGE = "/WEB-INF/view/fragments/albumForm.jsp";
    private static final String ATTRIBUTE_ARTISTS = "artists";
    private static final String ATTRIBUTE_ARTIST = "artist";

    private final ArtistService artistService;

    public AlbumFormPageCommand(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<Artist> artists = artistService.getAllArtists();
        request.setAttribute(ATTRIBUTE_ARTISTS, artists);
        request.setAttribute(ATTRIBUTE_ARTIST, new Artist());
        return CommandResult.forward(SHOW_ADD_ALBUM_FORM_PAGE);
    }
}
