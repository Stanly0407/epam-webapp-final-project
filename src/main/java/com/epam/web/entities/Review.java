package com.epam.web.entities;

public class Review extends Entity {

    public static final String TABLE = "review";
    public static final String ID = "id";
    public static final String CONTENT = "content";
    public static final String TRACK_ID = "track_id";
    public static final String USER_ID = "user_id";

    private String content;
    private Long trackId;
    private  Long userId;

    public Review(Long id, String content, Long trackId, Long userId) {
        super(id);
        this.content = content;
        this.trackId = trackId;
        this.userId = userId;
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
}
