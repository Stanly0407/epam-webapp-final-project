package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.MusicCollectionService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public class AddPlaylistCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddPlaylistCommand.class);
    private static final String CONTENT_TYPE = "UTF-8";
    private static final String PARAMETER_PLAYLIST_TITLE = "playlistTitle";
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "/controller?command=allMusic";
    private static final String PLAYLIST_DIRECTORY = "D:/EPAM-training/final_project/project_data/img/playlists/";
    private static final String PARAMETER_PLAYLIST_RELEASE_DATE = "releaseDate";

    private final MusicCollectionService musicCollectionService;

    public AddPlaylistCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String playlistTitle = null;
        String filename = null;
        String releaseDate = null;
        try {
            List<FileItem> data = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : data) {
                if (item.isFormField()) {
                    String parameterName = item.getFieldName();
                    String value = item.getString(CONTENT_TYPE);
                    switch (parameterName) {
                        case PARAMETER_PLAYLIST_TITLE:
                            playlistTitle = value;
                            break;
                        case PARAMETER_PLAYLIST_RELEASE_DATE:
                            releaseDate = value;
                            break;
                        default:
                            throw new ServiceException("Unknown parameter..." + parameterName);
                    }
                } else if (!item.isFormField()) {
                    filename = item.getName();
                    LOGGER.debug("filename " + filename);
                    item.write(new File(PLAYLIST_DIRECTORY + filename));
                }
            }
            musicCollectionService.addPlaylist(releaseDate, playlistTitle, filename);
        } catch (Exception e) {
            LOGGER.error(e + " error message" + e.getMessage());
        }
        return CommandResult.redirect(SHOW_TRACK_LIST_PAGE_COMMAND); //CHANGE
    }
}
