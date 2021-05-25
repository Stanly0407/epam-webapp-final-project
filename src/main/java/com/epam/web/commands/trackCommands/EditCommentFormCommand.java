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

public class EditCommentFormCommand implements Command {
    private static final String TRACK_COMMENTS_PAGE = "/WEB-INF/view/userPages/commentsPage.jsp";
    private static final String USER_ID = "userId";
    private static final String COMMENT_ID = "id";
    private static final String HIDDEN_INPUT_COMMENT_ID = "commentId";
    private static final String TRACK_ID = "trackId";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_COMMENTS = "comments";
    private static final String ATTRIBUTE_COMMENT = "comment";
    private static final String ATTRIBUTE_COMMENT_CONTENT = "editableContent";
    private static final String ATTRIBUTE_BUTTON_EDIT = "buttonEdit";
    private static final String ATTRIBUTE_COMMENTED_TRACK_ID = "currentCommentedTrackId";
    private static final String ATTRIBUTE_COMMENT_ID = "currentEditableCommentId";

    private final TrackService trackService;
    private final CommentService commentService;

    public EditCommentFormCommand(TrackService trackService, CommentService commentService) {
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
        String commentIdString = request.getParameter(COMMENT_ID);
        Long commentId;
        if (commentIdString == null) {
            commentId = (Long) session.getAttribute(ATTRIBUTE_COMMENT_ID);
        } else {
            commentId = Long.valueOf(commentIdString);
            session.setAttribute(ATTRIBUTE_COMMENT_ID, commentId);
        }
        String editableContent = commentService.getEditableContent(commentId);
        request.setAttribute(ATTRIBUTE_COMMENT_CONTENT, editableContent);
        TrackDto trackDto = trackService.getTrackDtoById(trackId, userId);
        List<CommentDto> comments = commentService.getCommentsByTrackIdExcludedChosen(trackId, userId, commentId);
        request.setAttribute(ATTRIBUTE_TRACK, trackDto);
        request.setAttribute(ATTRIBUTE_COMMENTS, comments);
        request.setAttribute(ATTRIBUTE_COMMENT, new CommentDto());
        request.setAttribute(ATTRIBUTE_BUTTON_EDIT, true);
        request.setAttribute(HIDDEN_INPUT_COMMENT_ID, commentId);
        return CommandResult.forward(TRACK_COMMENTS_PAGE);
    }
}
