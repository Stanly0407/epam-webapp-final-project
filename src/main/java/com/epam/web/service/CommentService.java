package com.epam.web.service;

import com.epam.web.dao.CommentDao;
import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.UserDao;
import com.epam.web.dto.CommentDto;
import com.epam.web.entities.Comment;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommentService {
    private static final Logger LOGGER = LogManager.getLogger(CommentService.class);

    private DaoHelperFactory daoHelperFactory;

    public CommentService(DaoHelperFactory daoHelperFactory) {
        this.daoHelperFactory = daoHelperFactory;
    }

    public void editComment(String content, Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            commentDao.updateComment(content, commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void deleteComment(Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            commentDao.removeById(commentId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<CommentDto> getCommentsByTrackId(Long trackId, Long currentUserId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            List<Comment> trackComments = commentDao.findCommentsByTrackId(trackId);
            return createCommentDtoList(trackComments, daoHelper, trackId, currentUserId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public List<CommentDto> getCommentsByTrackIdExcludedChosen(Long trackId, Long currentUserId, Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            List<Comment> trackComments = commentDao.findCommentsByTrackIdExcludedOne(trackId, commentId);
            return createCommentDtoList(trackComments, daoHelper, trackId, currentUserId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public String getEditableContent(Long commentId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            Optional<Comment> commentOptional = commentDao.getById(commentId);
            String commentContent = null;
            if (commentOptional.isPresent()) {
                Comment comment = commentOptional.get();
                commentContent = comment.getContent();
            }
            return commentContent;
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    public void addNewCommentToTrack(String commentContent, Long trackId, Long userId) throws ServiceException {
        try (DaoHelper daoHelper = daoHelperFactory.create()) {
            CommentDao commentDao = daoHelper.createCommentDao();
            commentDao.addNewCommentToTrack(commentContent, trackId, userId);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private List<CommentDto> createCommentDtoList(List<Comment> comments, DaoHelper daoHelper, Long trackId, Long currentUserId) throws DaoException {
        UserDao userDao = daoHelper.createUserDao();
        List<CommentDto> commentsList = new ArrayList<>();
        for (Comment comment : comments) {
            Long id = comment.getId();
            LocalDateTime commentDate = comment.getCommentDate();
            String content = comment.getContent();
            Long userId = comment.getUserId();
            boolean currentUserAuthor = false;
            if (userId.equals(currentUserId)) {
                currentUserAuthor = true;
            }
            Optional<User> userOptional = userDao.getById(userId);
            User user = new User();
            if (userOptional.isPresent()) {
                user = userOptional.get();
            }
            String name = user.getName();
            LOGGER.debug("comment user name = " + name);
            String lastname = user.getLastname();
            CommentDto commentDto = new CommentDto.Builder()
                    .id(id)
                    .commentDate(commentDate)
                    .content(content)
                    .trackId(trackId)
                    .userId(userId)
                    .name(name)
                    .lastname(lastname)
                    .currentUserAuthor(currentUserAuthor)
                    .build();
            commentsList.add(commentDto);
        }
        return commentsList;
    }

}
