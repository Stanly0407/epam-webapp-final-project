package com.epam.web.commands.admin;

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

public class AddEditPlaylistCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddEditPlaylistCommand.class);
    private static final String CONTENT_TYPE = "UTF-8";
    private static final String PARAMETER_PLAYLIST_TITLE = "playlistTitle";
    private static final String PARAMETER_PLAYLIST_ID = "playlistId";
    private static final String SHOW_PLAYLISTS_PAGE_COMMAND = "/controller?command=allPlaylists";
    private static final String PLAYLIST_DIRECTORY = "D:/EPAM-training/final_project/project_data/img/playlists/";
    private static final String PARAMETER_PLAYLIST_RELEASE_DATE = "releaseDate";
    private static final String EMPTY_FIELD = "";

    private final MusicCollectionService musicCollectionService;

    public AddEditPlaylistCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String playlistId = null;
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
                        case PARAMETER_PLAYLIST_ID:
                            if (!EMPTY_FIELD.equals(value)) {
                                playlistId = value;
                            } else {
                                playlistId = null;
                            }
                            break;
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
                    if (!EMPTY_FIELD.equals(filename)) {
                        item.write(new File(PLAYLIST_DIRECTORY + filename));
                    } else {
                        filename = null;
                    }
                }
            }
            musicCollectionService.addEditPlaylist(playlistId, releaseDate, playlistTitle, filename);
        } catch (Exception e) {
            throw new ServiceException("ERROR: " + e + "wrong upload. error message: " + e.getMessage());
        }
        return CommandResult.redirect(SHOW_PLAYLISTS_PAGE_COMMAND);
    }

}
