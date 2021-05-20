package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddTrackToCollectionCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddTrackToCollectionCommand.class);
    private static final String SHOW_ALBUMS_PAGE_COMMAND = "/controller?command=allAlbums";
    private static final String PARAMETER_TRACK_ID = "id";
    private static final String PARAMETER_ALBUM_ID = "albumId";


    private final MusicCollectionService musicCollectionService;

    public AddTrackToCollectionCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String trackIdParameter = request.getParameter(PARAMETER_TRACK_ID);
        String albumIdParameter = request.getParameter(PARAMETER_ALBUM_ID);

        if(trackIdParameter != null && albumIdParameter != null){
            Long trackId = Long.valueOf(trackIdParameter);
            Long albumId = Long.valueOf(albumIdParameter);
            musicCollectionService.addTrackToCollection(trackId, albumId);
        }

        return CommandResult.redirect(SHOW_ALBUMS_PAGE_COMMAND);
    }
}
