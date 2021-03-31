package com.epam.web.entities;

public class TrackAndCollection extends Entity {

    private Long trackId;
    private Long collectionId;

    public TrackAndCollection(Long id, Long trackId, Long collectionId) {
        super(id);
        this.trackId = trackId;
        this.collectionId = collectionId;
    }
}
