package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteTrackFromCollectionCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTrackFromCollectionCommand.class);
    private static final String CURRENT_PAGE = "currentPage";
    private static final String PARAMETER_TRACK_ID = "trackId";
    private static final String PARAMETER_COLLECTION_ID = "id";

    private final MusicCollectionService musicCollectionService;

    public DeleteTrackFromCollectionCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        String trackIdString = request.getParameter(PARAMETER_TRACK_ID);
        String collectionIdString = request.getParameter(PARAMETER_COLLECTION_ID);
        if(trackIdString != null && collectionIdString != null){
            Long trackId = Long.valueOf(trackIdString);
            Long collectionId = Long.valueOf(collectionIdString);
            musicCollectionService.deleteTrackFromCollection(trackId, collectionId);
        }
        return CommandResult.redirect(currentPage);
    }
}
