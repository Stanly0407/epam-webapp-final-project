package com.epam.web.dao;

import com.epam.web.entities.Comment;
import com.epam.web.exceptions.DaoException;
import com.epam.web.mapper.RowMapper;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;


public class CommentDao extends AbstractDao<Comment> implements Dao<Comment> {

    private static final String FIND_COMMENTS_BY_TRACK_ID = "SELECT id, comment_date, content, track_id, user_id FROM track_comment WHERE track_id = ? ORDER BY comment_date DESC";
    private static final String FIND_COMMENT_BY_ID = "SELECT id, comment_date, content, track_id, user_id FROM track_comment WHERE id = ?";
    private static final String INSERT_COMMENT_TO_TRACK = "INSERT INTO track_comment (content, track_id, user_id) value (?, ?, ?)";
    private static final String DELETE_COMMENT = "DELETE FROM track_comment WHERE id = ?";
    private static final String UPDATE_COMMENT = "UPDATE track_comment SET content = ? WHERE id = ?";
    private static final String FIND_COMMENTS_BY_TRACK_ID_EXCLUDED_ONE = "SELECT id, comment_date, content, track_id, user_id FROM track_comment WHERE track_id = ? AND id NOT IN (?) ORDER BY comment_date DESC";

    public CommentDao(Connection connection, RowMapper<Comment> mapper) {
        super(connection, mapper);
    }

    public List<Comment> findCommentsByTrackId(Long trackId) throws DaoException {
        return executeQuery(FIND_COMMENTS_BY_TRACK_ID, trackId);
    }

    public List<Comment> findCommentsByTrackIdExcludedOne(Long commentId, Long trackId) throws DaoException {
        return executeQuery(FIND_COMMENTS_BY_TRACK_ID_EXCLUDED_ONE, commentId, trackId);
    }

    public Optional<Comment> getById(Long id) throws DaoException {
        return executeForSingleResult(FIND_COMMENT_BY_ID, id);
    }


    public void addNewCommentToTrack(String commentContent, Long trackId, Long userId) throws DaoException {
        executeUpdate(INSERT_COMMENT_TO_TRACK, commentContent, trackId, userId);
    }

    public void editComment(String commentContent, Long commentId) throws DaoException {
        executeUpdate(UPDATE_COMMENT, commentContent, commentId);
    }

    @Override
    public void save(Comment entity) {
    }

    @Override
    public void removeById(Long id) throws DaoException {
        executeUpdate(DELETE_COMMENT, id);
    }

    @Override
    protected String getTableName() {
        return Comment.TABLE;
    }
}
