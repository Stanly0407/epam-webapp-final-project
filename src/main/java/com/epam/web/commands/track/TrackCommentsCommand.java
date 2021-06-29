package com.epam.web.commands.track;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.CommentDto;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.CommentService;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class TrackCommentsCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(TrackCommentsCommand.class);
    private static final String TRACK_COMMENTS_PAGE = "/WEB-INF/view/userPages/commentsPage.jsp";
    private static final String USER_ID = "userId";
    private static final String TRACK_ID = "id";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_COMMENTS = "comments";
    private static final String ATTRIBUTE_COMMENT = "comment";
    private static final String ATTRIBUTE_COMMENTED_TRACK_ID = "currentCommentedTrackId";

    private final TrackService trackService;
    private final CommentService commentService;

    public TrackCommentsCommand(TrackService trackService, CommentService commentService) {
        this.trackService = trackService;
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        String trackIdString = request.getParameter(TRACK_ID);
        Long trackId;
        if (trackIdString == null) {
            trackId = (Long) session.getAttribute(ATTRIBUTE_COMMENTED_TRACK_ID);
        } else {
            trackId = Long.valueOf(trackIdString);
            session.setAttribute(ATTRIBUTE_COMMENTED_TRACK_ID, trackId);
        }
        LOGGER.debug("trackId " + trackId);
        TrackDto trackDto = trackService.getTrackDtoById(trackId, userId);
        List<CommentDto> comments = commentService.getCommentsByTrackId(trackId, userId);
        request.setAttribute(ATTRIBUTE_TRACK, trackDto);
        request.setAttribute(ATTRIBUTE_COMMENTS, comments);
        request.setAttribute(ATTRIBUTE_COMMENT, new CommentDto());
        return CommandResult.forward(TRACK_COMMENTS_PAGE);
    }
}
