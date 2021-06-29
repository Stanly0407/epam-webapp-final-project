package com.epam.web.service;

import com.epam.web.dao.CommentDao;
import com.epam.web.dao.DaoHelper;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.dao.UserDao;
import com.epam.web.dto.CommentDto;
import com.epam.web.entities.Comment;
import com.epam.web.entities.Role;
import com.epam.web.entities.User;
import com.epam.web.exceptions.DaoException;
import com.epam.web.exceptions.ServiceException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

public class CommentServiceTest {
    private static UserDao mockUserDao;
    private static CommentDao mockCommentDao;
    private static CommentService commentService;
    private static final Long TEST_ID = 1L;


    @BeforeClass
    public static void init() {
        DaoHelper mockDaoHelper = Mockito.mock(DaoHelper.class);
        DaoHelperFactory mockDaoHelperFactory = Mockito.mock(DaoHelperFactory.class);
        when(mockDaoHelperFactory.create()).thenReturn(mockDaoHelper);
        mockUserDao = Mockito.mock(UserDao.class);
        mockCommentDao = Mockito.mock(CommentDao.class);
        when(mockDaoHelper.createUserDao()).thenReturn(mockUserDao);
        when(mockDaoHelper.createCommentDao()).thenReturn(mockCommentDao);
        commentService = new CommentService(mockDaoHelperFactory);
    }

    @Test
    public void testGetCommentsByTrackId() throws DaoException, ServiceException {
        List<Comment> comments = new ArrayList<>();
        comments.add(new Comment(TEST_ID, LocalDateTime.of(2021, 5, 23, 0, 0, 0),
                "testOneComment", TEST_ID, TEST_ID));
        comments.add(new Comment(TEST_ID, LocalDateTime.of(2021, 5, 23, 0, 0, 0),
                "testTwoComment", TEST_ID, TEST_ID));

        List<CommentDto> expectedCommentDto = new ArrayList<>();
        CommentDto expectedFirstComment = new CommentDto.Builder()
                .id(TEST_ID).commentDate(LocalDateTime.of(2021, 5, 23, 0, 0, 0))
                .content("testOneComment").trackId(TEST_ID).userId(TEST_ID).name("name").lastname("lastname").currentUserAuthor(true)
                .build();
        CommentDto expectedSecondComment = new CommentDto.Builder()
                .id(TEST_ID).commentDate(LocalDateTime.of(2021, 5, 23, 0, 0, 0))
                .content("testTwoComment").trackId(TEST_ID).userId(TEST_ID).name("name").lastname("lastname").currentUserAuthor(true)
                .build();
        expectedCommentDto.add(expectedFirstComment);
        expectedCommentDto.add(expectedSecondComment);
        User testUser = new User(TEST_ID, "firstLogin", "name", "lastname", Role.USER,
                new BigDecimal("10.00"), false);

        when(mockCommentDao.findCommentsByTrackId(anyLong())).thenReturn(comments);
        when(mockUserDao.getById(anyLong())).thenReturn(Optional.of(testUser));

        List<CommentDto> actualCommentDto = commentService.getCommentsByTrackId(TEST_ID, TEST_ID);

        Assert.assertEquals(expectedCommentDto, actualCommentDto);
    }


}
