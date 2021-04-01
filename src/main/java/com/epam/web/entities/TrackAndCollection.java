package com.epam.web.entities;

public class TrackAndCollection extends Entity {

    public static final String TABLE = "track_collection";
    public static final String ID = "id";
    public static final String TRACK_ID = "track_id";
    public static final String COLLECTION_ID = "collection_id";

    private Long trackId;
    private Long collectionId;

    public TrackAndCollection(Long id, Long trackId, Long collectionId) {
        super(id);
        this.trackId = trackId;
        this.collectionId = collectionId;
    }

    public Long getTrackId() {
        return trackId;
    }

    public void setTrackId(Long trackId) {
        this.trackId = trackId;
    }

    public Long getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Long collectionId) {
        this.collectionId = collectionId;
    }
}
