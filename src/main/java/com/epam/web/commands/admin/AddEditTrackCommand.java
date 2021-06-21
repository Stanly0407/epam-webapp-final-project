package com.epam.web.commands.admin;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddEditTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddEditTrackCommand.class);
    private static final String CONTENT_TYPE = "UTF-8";
    private static final String PARAMETER_RELEASE_DATE = "releaseDate";
    private static final String PARAMETER_TITLE = "title";
    private static final String PARAMETER_PRICE = "price";
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "/controller?command=allMusic";
    private static final String PARAMETER_ARTIST_ID = "artistId";
    private static final String PARAMETER_TRACK_ID = "trackId";
    private static final String MUSICS_DIRECTORY = "D:/EPAM-training/final_project/project_data/audio/";
    private static final String EMPTY_FIELD = "";

    private final TrackService trackService;

    public AddEditTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String trackId = null;
        String releaseDate = null;
        String filename = null;
        String title = null;
        String price = null;
        String artistId;
        List<String> artistArray = new ArrayList<>();
        try {
            List<FileItem> data = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : data) {
                if (item.isFormField()) {
                    String parameterName = item.getFieldName();
                    String value = item.getString(CONTENT_TYPE);
                    switch (parameterName) {
                        case PARAMETER_TRACK_ID:
                            if (!EMPTY_FIELD.equals(value)) {
                                trackId = value;
                            } else {
                                trackId = null;
                            }
                            LOGGER.debug("trackId " + trackId);
                            break;
                        case PARAMETER_TITLE:
                            title = value;
                            break;
                        case PARAMETER_RELEASE_DATE:
                            releaseDate = value;
                            break;
                        case PARAMETER_PRICE:
                            price = value;
                            break;
                        case PARAMETER_ARTIST_ID:
                            artistId = value;
                            artistArray.add(artistId);
                            break;
                        default:
                            throw new ServiceException("Unknown file upload parameter..." + parameterName);
                    }
                } else if (!item.isFormField()) {
                    filename = item.getName();
                    LOGGER.debug("filename " + filename);
                    if (!EMPTY_FIELD.equals(filename)) {
                        item.write(new File(MUSICS_DIRECTORY + filename));
                    } else {
                        filename = null;
                    }
                }
            }
            LOGGER.debug("artistArray " + artistArray);
            trackService.addEditTrack(trackId, releaseDate, title, price, artistArray, filename);
        } catch (Exception e) {
            throw new ServiceException("ERROR: " + e + "wrong upload. error message: " + e.getMessage());
        }
        return CommandResult.redirect(SHOW_TRACK_LIST_PAGE_COMMAND);
    }
}
