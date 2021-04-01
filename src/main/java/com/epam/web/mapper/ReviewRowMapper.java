package com.epam.web.mapper;

import com.epam.web.entities.Review;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ReviewRowMapper implements RowMapper<Review> {

    @Override
    public Review map(ResultSet resultSet) throws SQLException {
        Long id = resultSet.getLong(Review.ID);
        String content = resultSet.getString(Review.CONTENT);
        Long trackId = resultSet.getLong(Review.TRACK_ID);
        Long userId = resultSet.getLong(Review.USER_ID);
        return new Review(id, content, trackId, userId);
    }
}
