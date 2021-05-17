package com.epam.web.dto;

import java.time.LocalDateTime;

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

}
