package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.CommentService;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddCommentToTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddCommentToTrackCommand.class);
    private static final String SHOW_COMMENTS_PAGE_COMMAND = "/controller?command=commentsPage";
    private static final String USER_ID = "userId";
    private static final String PARAMETER_COMMENT = "commentContent";
    private static final String ATTRIBUTE_COMMENTED_TRACK_ID = "currentCommentedTrackId";

    private final CommentService commentService;

    public AddCommentToTrackCommand(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);
        Long trackId = (Long) session.getAttribute(ATTRIBUTE_COMMENTED_TRACK_ID);
        LOGGER.debug("trackId " + trackId);
        String commentContent = request.getParameter(PARAMETER_COMMENT);
        commentService.addNewCommentToTrack(commentContent, trackId, userId);
        return CommandResult.redirect(SHOW_COMMENTS_PAGE_COMMAND);
    }
}
