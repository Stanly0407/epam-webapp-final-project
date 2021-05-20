package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.ArtistService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.List;

public class AddEditArtistCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddEditArtistCommand.class);
    private static final String CONTENT_TYPE = "UTF-8";
    private static final String PARAMETER_ARTIST_NAME = "artistName";
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "/controller?command=allMusic";
    private static final String ARTIST_DIRECTORY = "D:/EPAM-training/final_project/project_data/img/artists/";
    private static final String PARAMETER_ARTIST_ID = "artistId";

    private final ArtistService artistService;

    public AddEditArtistCommand(ArtistService artistService) {
        this.artistService = artistService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String artistName = null;
        String filename = null;
        String artistId = null;
        try {
            List<FileItem> data = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : data) {
                if (item.isFormField()) {
                    String parameterName = item.getFieldName();
                    String value = item.getString(CONTENT_TYPE);
                    if (parameterName.equals(PARAMETER_ARTIST_NAME)) {
                        artistName = value;
                    } else if (parameterName.equals(PARAMETER_ARTIST_ID)) {
                        artistId = value;
                    }
                } else if (!item.isFormField()) {
                    filename = item.getName();
                    LOGGER.debug("filename " + filename);
                    item.write(new File(ARTIST_DIRECTORY + filename));
                }
            }
            artistService.addArtist(artistId, artistName, filename);
        } catch (Exception e) {
            LOGGER.error(e + " error message" + e.getMessage());
        }
        return CommandResult.redirect(SHOW_TRACK_LIST_PAGE_COMMAND);
    }
}
