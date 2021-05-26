package com.epam.web.commands.admin;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTrackFromPlaylistCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTrackFromPlaylistCommand.class);
    private static final String PAGE = "/controller?command=editPlaylist&id=";
    private static final String PARAMETER_TRACK_ID = "trackId";
    private static final String PARAMETER_COLLECTION_ID = "id";

    private final MusicCollectionService musicCollectionService;

    public DeleteTrackFromPlaylistCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String trackIdString = request.getParameter(PARAMETER_TRACK_ID);
        String playlistIdString = request.getParameter(PARAMETER_COLLECTION_ID);
        LOGGER.debug("trackId    =   " + trackIdString + "   playlistIdString  =  " + playlistIdString);
        Long playlistId = null;
        Long trackId = null;
        if (trackIdString != null && playlistIdString != null) {
            trackId = Long.valueOf(trackIdString);
            playlistId = Long.valueOf(playlistIdString);
            musicCollectionService.deleteTrackFromCollection(trackId, playlistId);
        }
        return CommandResult.redirect(PAGE + playlistId);
    }
}
