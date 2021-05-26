package com.epam.web.commands.track;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AllAlbumsCommand implements Command {

    private static final String ALL_ALBUMS_PAGE = "/WEB-INF/view/userPages/userCollectionListPage.jsp";
    private static final String ATTRIBUTE_ALBUM_LIST = "albums";
    private static final String ATTRIBUTE_ALBUM = "album";

    private final MusicCollectionService musicCollectionService;

    public AllAlbumsCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<MusicCollection> collectionList = musicCollectionService.getAllAlbums();
        request.setAttribute(ATTRIBUTE_ALBUM_LIST, collectionList);
        request.setAttribute(ATTRIBUTE_ALBUM, new MusicCollection());
        return CommandResult.forward(ALL_ALBUMS_PAGE);
    }
}
