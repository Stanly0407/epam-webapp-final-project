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

public class AddEditAlbumCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddEditAlbumCommand.class);
    private static final String CONTENT_TYPE = "UTF-8";
    private static final String PARAMETER_ARTIST_ID = "artistId";
    private static final String PARAMETER_ALBUM_ID = "albumId";
    private static final String PARAMETER_ALBUM_TITLE = "albumTitle";
    private static final String PARAMETER_ALBUM_RELEASE_DATE = "releaseDate";
    private static final String SHOW_ALBUMS_PAGE_COMMAND = "/controller?command=allAlbums";
    private static final String ALBUMS_DIRECTORY = "D:/EPAM-training/final_project/project_data/img/albums/";
    private static final String EMPTY_FIELD = "";

    private final MusicCollectionService musicCollectionService;

    public AddEditAlbumCommand(MusicCollectionService musicCollectionService) {
        this.musicCollectionService = musicCollectionService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String albumId = null;
        String albumTitle = null;
        String filename = null;
        String artistId = null;
        String releaseDate = null;
        try {
            List<FileItem> data = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : data) {
                if (item.isFormField()) {
                    String parameterName = item.getFieldName();
                    String value = item.getString(CONTENT_TYPE);
                    switch (parameterName) {
                        case PARAMETER_ALBUM_ID:
                            if (!EMPTY_FIELD.equals(value)) {
                                albumId = value;
                            } else {
                                albumId = null;
                            }
                            break;
                        case PARAMETER_ALBUM_TITLE:
                            albumTitle = value;
                            break;
                        case PARAMETER_ARTIST_ID:
                            artistId = value;
                            break;
                        case PARAMETER_ALBUM_RELEASE_DATE:
                            releaseDate = value;
                            break;
                        default:
                            throw new ServiceException("Unknown parameter..." + parameterName);
                    }
                } else if (!item.isFormField()) {
                    filename = item.getName();
                    LOGGER.debug("filename " + filename);
                    if (!EMPTY_FIELD.equals(filename)) {
                        item.write(new File(ALBUMS_DIRECTORY + filename));
                    } else {
                        filename = null;
                    }
                }
            }
            musicCollectionService.addEditAlbum(albumId, releaseDate, albumTitle, filename, artistId);
        } catch (Exception e) {
            throw new ServiceException("ERROR: " + e + "wrong upload. error message: " + e.getMessage());
        }
        return CommandResult.redirect(SHOW_ALBUMS_PAGE_COMMAND);
    }
}
