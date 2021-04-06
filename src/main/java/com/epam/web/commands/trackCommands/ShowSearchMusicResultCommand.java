package com.epam.web.commands.trackCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class ShowSearchMusicResultCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(ShowSearchMusicResultCommand.class);

    private static final String SEARCH_MUSIC_COMMAND = "/controller?command=searchMusicResult";

    public ShowSearchMusicResultCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String searchSubject = request.getParameter("searchSubject");
        String searchCondition = request.getParameter("searchCondition");
        HttpSession session = request.getSession();
        session.setAttribute("searchSubject", searchSubject);
        session.setAttribute("searchCondition", searchCondition);

        return CommandResult.redirect(SEARCH_MUSIC_COMMAND);
    }
}
