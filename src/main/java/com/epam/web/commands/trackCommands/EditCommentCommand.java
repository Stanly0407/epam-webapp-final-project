package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.CommentDto;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.CommentService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class EditCommentCommand implements Command {
    private static final String TRACK_COMMENTS_PAGE = "/WEB-INF/view/userPages/commentsPage.jsp";
    private static final String USER_ID = "userId";
    private static final String COMMENT_ID = "id";

    private static final String TRACK_ID = "trackId";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_COMMENTS = "comments";
    private static final String ATTRIBUTE_COMMENT = "comment";

    private static final String ATTRIBUTE_COMMENT_CONTENT = "updatedContent";

    private final TrackService trackService;
    private final CommentService commentService;

    public EditCommentCommand(TrackService trackService, CommentService commentService) {
        this.trackService = trackService;
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();

        String commentIdString = request.getParameter(COMMENT_ID);
        Long commentId = Long.valueOf(commentIdString);

        String commentCurrentContent = commentService.getCommentContent(commentId);
        request.setAttribute(ATTRIBUTE_COMMENT_CONTENT, commentCurrentContent);

        Long userId = (Long) session.getAttribute(USER_ID);
        String trackIdString = request.getParameter(TRACK_ID);
        Long trackId = Long.valueOf(trackIdString);
        TrackDto trackDto = trackService.getTrackDtoById(trackId, userId);

        List<CommentDto> comments = commentService.getCommentsByTrackIdExcludedChosen(trackId, userId, commentId);
        request.setAttribute(ATTRIBUTE_TRACK, trackDto);
        request.setAttribute(ATTRIBUTE_COMMENTS, comments);
        request.setAttribute(ATTRIBUTE_COMMENT, new CommentDto());
        return CommandResult.forward(TRACK_COMMENTS_PAGE);
    }
}
