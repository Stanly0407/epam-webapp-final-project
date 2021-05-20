package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Artist;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.ArtistService;
import com.epam.web.service.MusicCollectionService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


public class EditAlbumFormCommand implements Command {

    private static final String EDIT_ALBUM_FORM_PAGE = "/WEB-INF/view/fragments/albumForm.jsp";
    private static final String ADMIN_MAIN_PAGE_PAGE = "/WEB-INF/view/adminPages/adminMainPage.jsp";
    private static final String PARAMETER_USER_ID = "userId";
    private static final String PARAMETER_ALBUM_ID = "albumId";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_TRACKS = "tracksList";
    private static final String ATTRIBUTE_ALBUM = "album";

    private final TrackService trackService;
    private final MusicCollectionService musicCollectionService;


    public EditAlbumFormCommand(TrackService trackService, MusicCollectionService musicCollectionService) {
        this.trackService = trackService;
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String page;
        String albumIdParameter = request.getParameter(PARAMETER_ALBUM_ID);

        if (albumIdParameter == null) {
            page = ADMIN_MAIN_PAGE_PAGE;
        } else {
            Long userId = (Long) session.getAttribute(PARAMETER_USER_ID);
            Long albumId = Long.valueOf(albumIdParameter);
            Optional<MusicCollection> albumOptional = musicCollectionService.getMusicCollectionById(albumId);
            MusicCollection album = albumOptional.get();
            request.setAttribute(ATTRIBUTE_ALBUM, album);
            List<TrackDto> albumTracks = trackService.getCollectionTracks(albumId, userId);
            request.setAttribute(ATTRIBUTE_TRACKS, albumTracks);
            request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
            page = EDIT_ALBUM_FORM_PAGE;
        }
        return CommandResult.forward(page);
    }
}
