package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.CommentService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class EditCommentCommand implements Command {
    private static final String TRACK_COMMENTS_PAGE = "/controller?command=commentsPage&id=";
    private static final String COMMENT_ID = "commentId";
    private static final String ATTRIBUTE_COMMENTED_TRACK_ID = "currentCommentedTrackId";
    private static final String PARAMETER_EDITED_COMMENT_CONTENT = "commentContent";

    private final CommentService commentService;

    public EditCommentCommand(CommentService commentService) {
        this.commentService = commentService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String commentIdString = request.getParameter(COMMENT_ID);
        Long commentId = Long.valueOf(commentIdString);
        String newCommentContent = request.getParameter(PARAMETER_EDITED_COMMENT_CONTENT);
        commentService.editComment(newCommentContent, commentId);
        HttpSession session = request.getSession();
        Long currentTrackId = (Long) session.getAttribute(ATTRIBUTE_COMMENTED_TRACK_ID);
        return CommandResult.redirect(TRACK_COMMENTS_PAGE + currentTrackId);
    }
}
