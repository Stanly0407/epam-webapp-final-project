package com.epam.web.service;

import com.epam.web.dao.*;
import com.epam.web.dto.TrackDto;
import com.epam.web.dto.TrackStatus;
import com.epam.web.entities.Artist;
import com.epam.web.entities.Comment;
import com.epam.web.entities.Order;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class TracksServiceTest {
    private static OrderDao mockOrderDao;
    private static ArtistDao mockArtistDao;
    private static TrackDao mockTrackDao;
    private static CommentDao mockCommentDao;
    private static TrackService trackService;
    private static final Long TEST_ID = 1L;

    @BeforeClass
    public static void init() {
        DaoHelper mockDaoHelper = Mockito.mock(DaoHelper.class);
        DaoHelperFactory mockDaoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(mockDaoHelperFactory.create()).thenReturn(mockDaoHelper);
        mockOrderDao = Mockito.mock(OrderDao.class);
        mockArtistDao = Mockito.mock(ArtistDao.class);
        mockTrackDao = Mockito.mock(TrackDao.class);
        mockCommentDao = Mockito.mock(CommentDao.class);
        when(mockDaoHelper.createOrderDao()).thenReturn(mockOrderDao);
        when(mockDaoHelper.createArtistDao()).thenReturn(mockArtistDao);
        when(mockDaoHelper.createTrackDao()).thenReturn(mockTrackDao);
        when(mockDaoHelper.createCommentDao()).thenReturn(mockCommentDao);
        trackService = new TrackService(mockDaoHelperFactory);
    }

    @Test
    public void testGetPurchasedTracks() throws DaoException, ServiceException {
        List<Track> tracks = new ArrayList<>();
        tracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_First",
                new BigDecimal("10.00"), "Track_Artist_First.mp3"));
        tracks.add(new Track(TEST_ID, LocalDate.of(2021, 5, 25), "Track_Artist_Second",
                new BigDecimal("20.00"), "Track_Artist_Second.mp3"));

        List<Artist> trackArtists = new ArrayList<>();
        trackArtists.add(new Artist(TEST_ID, "Artist_First", "/musicwebapp/artists/filename_one.jpg"));
        trackArtists.add(new Artist(TEST_ID, "Artist_Second", "/musicwebapp/artists/filename_two.jpg"));

        Order testOrder = new Order(TEST_ID, LocalDateTime.of(2021, 5, 5, 0, 0, 0), true, 1L);

        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(TEST_ID, LocalDateTime.of(2021, 5, 23, 0, 0, 0), "testOneComment", TEST_ID, TEST_ID));
        comments.add(new Comment(TEST_ID, LocalDateTime.of(2021, 5, 23, 0, 0, 0), "testTwoComment", TEST_ID, TEST_ID));

        when(mockTrackDao.findAllPaidTracks(anyLong())).thenReturn(tracks);
        when(mockArtistDao.getByTrackId(anyLong())).thenReturn(trackArtists);
        when(mockOrderDao.getOrderStatusForTrack(anyLong(), anyLong())).thenReturn(Optional.of(testOrder));
        when(mockCommentDao.findCommentsByTrackId(anyLong())).thenReturn(comments);

        List<TrackDto> actual = new ArrayList<>();
        TrackDto trackDtoFirst = new TrackDto.Builder()
                .id(TEST_ID).releaseDate(LocalDate.of(2021, 5, 25)).title("Track_Artist_First").commentsAmount(2)
                .price(new BigDecimal("10.00")).filename("Track_Artist_First.mp3").artists(trackArtists).status(TrackStatus.PURCHASED)
                .build();
        TrackDto trackDtoSecond = new TrackDto.Builder()
                .id(TEST_ID).releaseDate(LocalDate.of(2021, 5, 25)).title("Track_Artist_Second").commentsAmount(2)
                .price(new BigDecimal("20.00")).filename("Track_Artist_Second.mp3").artists(trackArtists).status(TrackStatus.PURCHASED)
                .build();
        actual.add(trackDtoFirst);
        actual.add(trackDtoSecond);

        List<TrackDto> expected = trackService.getPurchasedTracks(TEST_ID);

        Assert.assertEquals(expected, actual);
    }


}
