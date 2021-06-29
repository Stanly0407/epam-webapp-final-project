package com.epam.web.commands.admin;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTrackToPlaylistCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddTrackToPlaylistCommand.class);

    private static final String SHOW_PLAYLISTS_PAGE_COMMAND = "/controller?command=allPlaylists";
    private static final String PAGE = "/controller?command=editPlaylist&alreadyExist=true&id=";
    private static final String PARAMETER_TRACK_ID = "id";
    private static final String PARAMETER_PLAYLIST_ID = "playlistId";

    private final MusicCollectionService musicCollectionService;

    public AddTrackToPlaylistCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String trackIdParameter = request.getParameter(PARAMETER_TRACK_ID);
        String playlistIdParameter = request.getParameter(PARAMETER_PLAYLIST_ID);
        LOGGER.debug(" trackIdParameter " + trackIdParameter + "  playlistIdParameter  " + playlistIdParameter);
        if (trackIdParameter != null && playlistIdParameter != null) {
            Long trackId = Long.valueOf(trackIdParameter);
            Long playlistId = Long.valueOf(playlistIdParameter);
            if (musicCollectionService.checkTrackInPlaylist(playlistId, trackId)) {
                LOGGER.debug("(alreadyExist) add to collection -> FALSE");
                return CommandResult.redirect(PAGE + playlistId);
            }
            musicCollectionService.addTrackToCollection(trackId, playlistId);
        }
        return CommandResult.redirect(SHOW_PLAYLISTS_PAGE_COMMAND);
    }
}
