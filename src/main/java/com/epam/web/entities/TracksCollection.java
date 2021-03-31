package com.epam.web.entities;

public class TracksCollection extends Entity {

    public static final String TABLE = "collection";
    public static final String ID = "id";
    public static final String COLLECTION_TYPE = "type";
    public static final String TITLE = "title";
    public static final String ARTIST_ID = "artist_id";

    private TracksCollectionType type;
    private String title;
    private Long artistId;

    public TracksCollection(Long id, TracksCollectionType type, String title, Long artistId) {
        super(id);
        this.type = type;
        this.title = title;
        this.artistId = artistId;
    }

    public TracksCollectionType getType() {
        return type;
    }

    public void setType(TracksCollectionType type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getArtistId() {
        return artistId;
    }

    public void setArtistId(Long artistId) {
        this.artistId = artistId;
    }

    @Override
    public String toString() {
        return "Collection{" +
                "type=" + type +
                ", title='" + title + '\'' +
                ", artistId=" + artistId +
                '}';
    }
}
