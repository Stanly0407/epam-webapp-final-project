package com.epam.web.commands.admin;

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

public class ChoosePlaylistFormCommand implements Command {

    private static final String CHOOSE_PLAYLIST_FOR_TRACK_PAGE = "/WEB-INF/view/adminPages/choosePlaylistForm.jsp";
    private static final String USER_ID = "userId";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String PARAMETER_TRACK_ID = "id";
    private static final String ATTRIBUTE_PLAYLISTS = "playlists";
    private static final String ATTRIBUTE_PLAYLIST = "playlist";

    private final MusicCollectionService musicCollectionService;
    private final TrackService trackService;

    public ChoosePlaylistFormCommand(MusicCollectionService musicCollectionService, TrackService trackService) {
        this.musicCollectionService = musicCollectionService;
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        String trackIdParameter = request.getParameter(PARAMETER_TRACK_ID);
        Long trackId;
        if (trackIdParameter != null) {
            trackId = Long.valueOf(trackIdParameter);
            TrackDto track = trackService.getTrackDtoById(trackId, userId);
            request.setAttribute(ATTRIBUTE_TRACK, track);
            List<MusicCollection> playlists = musicCollectionService.getAllPlaylists();
            request.setAttribute(ATTRIBUTE_PLAYLISTS, playlists);
            request.setAttribute(ATTRIBUTE_PLAYLIST, new MusicCollection());
        }
        return CommandResult.forward(CHOOSE_PLAYLIST_FOR_TRACK_PAGE);
    }
}
