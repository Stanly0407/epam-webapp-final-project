package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


public class ShowEditTrackFormCommand implements Command {

    private static final String EDIT_TRACK_FORM_PAGE = "/WEB-INF/view/adminPages/adminEditTrackFormPage.jsp";
    private static final String PARAMETER_NAME_ID = "id";
    private static final String ATTRIBUTE_NAME_TRACK = "track";

    private final TrackService trackService;

    public ShowEditTrackFormCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String idParameter = request.getParameter(PARAMETER_NAME_ID);
        Long id = Long.valueOf(idParameter);
        Optional<TrackDto> optionalTrack = trackService.getTrack(id);
        TrackDto track = optionalTrack.get();
        request.setAttribute(ATTRIBUTE_NAME_TRACK, track);
        return CommandResult.forward(EDIT_TRACK_FORM_PAGE);
    }
}
