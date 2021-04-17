package com.epam.web.mapper;

import com.epam.web.entities.Comment;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class CommentRowMapper implements RowMapper<Comment> {

    @Override
    public Comment map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Comment.ID);
        LocalDateTime commentDate = resultSet.getObject(Comment.COMMENT_DATE, LocalDateTime.class);
        String content = resultSet.getString(Comment.CONTENT);
        Long trackId = resultSet.getLong(Comment.TRACK_ID);
        Long userId = resultSet.getLong(Comment.USER_ID);
        return new Comment(id, commentDate, content, trackId, userId);
    }
}
