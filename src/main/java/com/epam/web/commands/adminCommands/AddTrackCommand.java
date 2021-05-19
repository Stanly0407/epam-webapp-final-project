package com.epam.web.commands.adminCommands;

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

public class AddTrackCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddTrackCommand.class);
    private static final String CONTENT_TYPE = "UTF-8";
    private static final String PARAMETER_RELEASE_DATE = "releaseDate";
    private static final String PARAMETER_TITLE = "title";
    private static final String PARAMETER_PRICE = "price";
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "/controller?command=allMusic";
    private static final String PARAMETER_ARTIST_ID = "artistId";
    private static final String PARAMETER_TRACK_ID = "trackId";
    private static final String MUSICS_DIRECTORY = "D:/EPAM-training/final_project/project_data/audio/";

    private final TrackService trackService;

    public AddTrackCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String trackId = null;
        String releaseDate = null;
        String filename = null;
        String title = null;
        String price = null;
       String artistIds = null;
        List<String> artistArray = new ArrayList<>();
        //  String[] artistIds = request.getParameterValues(PARAMETER_ARTIST_IDS);
        try {
            List<FileItem> data = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : data) {
                if (item.isFormField()) {
                    String parameterName = item.getFieldName();
                    String value = item.getString(CONTENT_TYPE);
                    switch (parameterName) {
                        case PARAMETER_TRACK_ID:
                            trackId = value;
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
                            artistIds = value;
                            artistArray.add(artistIds);
                            break;
                        default:
                            throw new ServiceException("Unknown parameter..." + parameterName);
                    }
                } else if (!item.isFormField()) {
                    filename = item.getName();
                    LOGGER.debug("filename " + filename);
                    if(!filename.equals("")){
                        item.write(new File(MUSICS_DIRECTORY + filename));
                    } else {
                        filename = null;
                    }
                }
            }
            trackService.addEditTrack(trackId, releaseDate, title, price, artistArray, filename);

        } catch (Exception e) {
            LOGGER.error(e + " error message" + e.getMessage());
            throw new ServiceException("wrong upload. error message: " + e.getMessage());
        }
        LOGGER.debug("PARAMETER_RELEASE_DATE " + releaseDate);
        LOGGER.debug("artistIds " + artistIds);

        return CommandResult.redirect(SHOW_TRACK_LIST_PAGE_COMMAND);
    }
}
