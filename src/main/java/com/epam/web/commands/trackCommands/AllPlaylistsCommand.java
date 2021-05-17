package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.MusicCollection;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class AllPlaylistsCommand implements Command {
    private static final String ALL_PLAYLISTS_PAGE = "/WEB-INF/view/userPages/userCollectionListPage.jsp";
    private static final String ATTRIBUTE_PLAYLIST_LIST = "playlists";
    private static final String ATTRIBUTE_PLAYLIST = "playlist";

    private final MusicCollectionService musicCollectionService;

    public AllPlaylistsCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<MusicCollection> collectionList = musicCollectionService.getAllPlaylists();
        request.setAttribute(ATTRIBUTE_PLAYLIST_LIST, collectionList);
        request.setAttribute(ATTRIBUTE_PLAYLIST, new MusicCollection());
        return CommandResult.forward(ALL_PLAYLISTS_PAGE);
    }
}
