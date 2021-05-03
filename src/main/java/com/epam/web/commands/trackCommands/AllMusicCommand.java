package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class AllMusicCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AllMusicCommand.class);
    private static final String ALL_MUSIC_PAGE_COMMAND = "/WEB-INF/view/userPages/allMusicPage.jsp";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    //    private static final String ATTRIBUTE_ALBUM_LIST = "albums";
//    private static final String ATTRIBUTE_ALBUM = "album";
//    private static final String ATTRIBUTE_PLAYLIST_LIST = "playlists";
//    private static final String ATTRIBUTE_PLAYLIST = "playlist";
    private static final String USER_ID = "userId";

    private static final String ATTRIBUTE_ROW_COUNT = "rowCount";
    private static final String PAGE_ACTION = "page";
    private static final int PAGE = 1;
    private static final int PAGE_LIMIT = 7; //сделать изменяемым и задаваемым юзером
    private static final String PAGE_NEXT = "next";
    private static final String PAGE_PREVIOUS = "previous";
    private static final String ATTRIBUTE_IS_NEXT_POSSIBLE = "isNextPossible";
    private static final String ATTRIBUTE_IS_PREVIOUS_POSSIBLE = "isPreviousPossible";
    private static final String ATTRIBUTE_CURRENT_PAGINATION_PAGE = "currentPaginationPage";
    private static final String ATTRIBUTE_PAGINATION_LIST = "paginationList";

    private final TrackService trackService;
    private final MusicCollectionService musicCollectionService;

    public AllMusicCommand(TrackService trackService, MusicCollectionService musicCollectionService) {
        this.trackService = trackService;
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(USER_ID);

        String pageAction = request.getParameter(PAGE_ACTION);
        Integer currentPaginationPage = (Integer) session.getAttribute(ATTRIBUTE_CURRENT_PAGINATION_PAGE);
        int page;
        LOGGER.debug("currentPage " + currentPaginationPage);
        LOGGER.debug("pageAction " + pageAction);
        if (currentPaginationPage == null) {
            session.setAttribute(ATTRIBUTE_CURRENT_PAGINATION_PAGE, 1);
            currentPaginationPage = 1;
        }

        if (pageAction == null) {
            page = 1;
        } else if (PAGE_NEXT.equals(pageAction)) {
            page = currentPaginationPage + PAGE;
        } else if (PAGE_PREVIOUS.equals(pageAction)) {
            page = currentPaginationPage - PAGE;
        } else {
            page = Integer.parseInt(pageAction);
        }

        session.setAttribute(ATTRIBUTE_CURRENT_PAGINATION_PAGE, page);

        boolean isNextPossible = trackService.checkPaginationAction(page, true);
        session.setAttribute(ATTRIBUTE_IS_NEXT_POSSIBLE, isNextPossible);
        boolean isPreviousPossible = trackService.checkPaginationAction(page, false);
        session.setAttribute(ATTRIBUTE_IS_PREVIOUS_POSSIBLE, isPreviousPossible);

        List<TrackDto> trackList = trackService.getTracksPage(userId, PAGE_LIMIT, page);

        List<Integer> paginationList = trackService.getPaginationList();
        request.setAttribute(ATTRIBUTE_PAGINATION_LIST, paginationList);

        request.setAttribute(ATTRIBUTE_TRACK_LIST, trackList);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());


//        List<MusicCollection> albums = musicCollectionService.getAllAlbums();
//        request.setAttribute(ATTRIBUTE_ALBUM_LIST, albums);
//        request.setAttribute(ATTRIBUTE_ALBUM, new MusicCollection());
//        List<MusicCollection> playlists = musicCollectionService.getAllPlaylists();
//        request.setAttribute(ATTRIBUTE_PLAYLIST_LIST, playlists);
//        request.setAttribute(ATTRIBUTE_PLAYLIST, new MusicCollection());
        return CommandResult.forward(ALL_MUSIC_PAGE_COMMAND);
    }
}
