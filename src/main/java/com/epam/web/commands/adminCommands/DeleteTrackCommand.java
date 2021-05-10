package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public class DeleteTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTrackCommand.class);
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "/controller?command=allMusic";
    private static final String PARAMETER_TRACK_ID = "trackId";


    private final TrackService trackService;

    public DeleteTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String trackId = request.getParameter(PARAMETER_TRACK_ID);
        trackService.deleteTrackById(trackId);
        return CommandResult.redirect(SHOW_TRACK_LIST_PAGE_COMMAND);
    }
}
