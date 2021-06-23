package com.epam.web.dto;

import java.time.LocalDateTime;
import java.util.Objects;

public class CommentDto {

    private Long id;
    private LocalDateTime commentDate;
    private String content;
    private Long trackId;
    private Long userId;
    private String name;
    private String lastname;
    private boolean currentUserAuthor;

    public Long getId() {
        return id;
    }

    public LocalDateTime getCommentDate() {
        return commentDate;
    }

    public String getContent() {
        return content;
    }

    public Long getTrackId() {
        return trackId;
    }

    public Long getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public boolean getCurrentUserAuthor() {
        return currentUserAuthor;
    }

    public String getLastname() {
        return lastname;
    }

    public static class Builder {
        private CommentDto newComment;

        public Builder() {
            newComment = new CommentDto();
        }

        public Builder id(Long id) {
            newComment.id = id;
            return this;
        }

        public Builder commentDate(LocalDateTime commentDate) {
            newComment.commentDate = commentDate;
            return this;
        }

        public Builder content(String content) {
            newComment.content = content;
            return this;
        }

        public Builder trackId(Long trackId) {
            newComment.trackId = trackId;
            return this;
        }

        public Builder userId(Long userId) {
            newComment.userId = userId;
            return this;
        }

        public Builder name(String name) {
            newComment.name = name;
            return this;
        }

        public Builder lastname(String lastname) {
            newComment.lastname = lastname;
            return this;
        }

        public Builder currentUserAuthor(boolean currentUserAuthor) {
            newComment.currentUserAuthor = currentUserAuthor;
            return this;
        }

        public CommentDto build() {
            return newComment;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        CommentDto that = (CommentDto) o;
        return currentUserAuthor == that.currentUserAuthor &&
                Objects.equals(id, that.id) &&
                Objects.equals(commentDate, that.commentDate) &&
                Objects.equals(content, that.content) &&
                Objects.equals(trackId, that.trackId) &&
                Objects.equals(userId, that.userId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(lastname, that.lastname);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, commentDate, content, trackId, userId, name, lastname, currentUserAuthor);
    }

}
