package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;


public class EditPlaylistFormCommand implements Command {

    private static final String EDIT_PLAYLIST_FORM_PAGE = "/WEB-INF/view/fragments/playlistForm.jsp";
    private static final String ADMIN_MAIN_PAGE_PAGE = "/WEB-INF/view/adminPages/adminMainPage.jsp";
    private static final String PARAMETER_USER_ID = "userId";
    private static final String PARAMETER_PLAYLIST_ID = "playlistId";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_TRACKS = "tracksList";
    private static final String ATTRIBUTE_PLAYLIST = "playlist";

    private final TrackService trackService;
    private final MusicCollectionService musicCollectionService;


    public EditPlaylistFormCommand(TrackService trackService, MusicCollectionService musicCollectionService) {
        this.trackService = trackService;
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String page;
        String playlistIdParameter = request.getParameter(PARAMETER_PLAYLIST_ID);
        if (playlistIdParameter == null) {
            page = ADMIN_MAIN_PAGE_PAGE;
        } else {
            Long userId = (Long) session.getAttribute(PARAMETER_USER_ID);
            Long playlistId = Long.valueOf(playlistIdParameter);
            Optional<MusicCollection> playlistOptional = musicCollectionService.getMusicCollectionById(playlistId);
            MusicCollection playlist = playlistOptional.get();
            request.setAttribute(ATTRIBUTE_PLAYLIST, playlist);
            List<TrackDto> playlistTracks = trackService.getCollectionTracks(playlistId, userId);
            request.setAttribute(ATTRIBUTE_TRACKS, playlistTracks);
            request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
            page = EDIT_PLAYLIST_FORM_PAGE;
        }
        return CommandResult.forward(page);
    }
}
