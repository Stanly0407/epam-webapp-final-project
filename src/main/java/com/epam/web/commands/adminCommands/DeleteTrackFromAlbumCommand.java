package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteTrackFromAlbumCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTrackFromAlbumCommand.class);
    private static final String PAGE = "/controller?command=editAlbum&id=";
    private static final String PARAMETER_TRACK_ID = "trackId";
    private static final String PARAMETER_COLLECTION_ID = "id";

    private final MusicCollectionService musicCollectionService;

    public DeleteTrackFromAlbumCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String trackIdString = request.getParameter(PARAMETER_TRACK_ID);
        String albumIdString = request.getParameter(PARAMETER_COLLECTION_ID);
        LOGGER.debug("trackId    =   " + trackIdString + "   albumIdString  =  " + albumIdString);
        Long albumId = null;
        Long trackId = null;
        if (trackIdString != null && albumIdString != null) {
            trackId = Long.valueOf(trackIdString);
            albumId = Long.valueOf(albumIdString);
            musicCollectionService.deleteTrackFromCollection(trackId, albumId);
        }
        return CommandResult.redirect(PAGE + albumId);
    }
}
