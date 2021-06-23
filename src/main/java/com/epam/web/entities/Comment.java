package com.epam.web.entities;

import java.time.LocalDateTime;
import java.util.Objects;

public class Comment extends Entity {
    public static final String TABLE = "track_comment";
    public static final String ID = "id";
    public static final String COMMENT_DATE = "comment_date";
    public static final String CONTENT = "content";
    public static final String TRACK_ID = "track_id";
    public static final String USER_ID = "user_id";

    private LocalDateTime commentDate;
    private String content;
    private Long trackId;
    private Long userId;

    public Comment() {
    }

    public Comment(Long id, LocalDateTime commentDate, String content, Long trackId, Long userId) {
        super(id);
        this.commentDate = commentDate;
        this.content = content;
        this.trackId = trackId;
        this.userId = userId;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(LocalDateTime commentDate) {
        this.commentDate = commentDate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Comment comment = (Comment) o;
        return Objects.equals(commentDate, comment.commentDate) &&
                Objects.equals(content, comment.content) &&
                Objects.equals(trackId, comment.trackId) &&
                Objects.equals(userId, comment.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(commentDate, content, trackId, userId);
    }
}
