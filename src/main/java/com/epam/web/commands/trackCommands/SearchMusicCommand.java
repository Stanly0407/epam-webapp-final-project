package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class SearchMusicCommand implements Command {

    private static final String SHOW_USER_TRACK_LIST_PAGE_COMMAND = "/WEB-INF/view/userPages/userTrackListPage.jsp";
    private static final String ATTRIBUTE_SEARCH_SUBJECT = "searchSubject";
    private static final String ATTRIBUTE_SEARCH_CONDITION = "searchCondition";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";

    private final TrackService trackService;

    public SearchMusicCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String searchSubject = (String) session.getAttribute(ATTRIBUTE_SEARCH_SUBJECT);
        String searchCondition = (String) session.getAttribute(ATTRIBUTE_SEARCH_CONDITION);
        List<TrackDto> trackList = trackService.getMusicByCondition(searchSubject, searchCondition);
        request.setAttribute(ATTRIBUTE_TRACK_LIST, trackList);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        return CommandResult.forward(SHOW_USER_TRACK_LIST_PAGE_COMMAND);
    }
}
