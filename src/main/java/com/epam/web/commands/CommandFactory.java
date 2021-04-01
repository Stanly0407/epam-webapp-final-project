package com.epam.web.commands;

import com.epam.web.commands.trackCommands.CreateTrackCommand;
import com.epam.web.commands.trackCommands.ShowTrackListCommand;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.service.TrackService;
import com.epam.web.service.UserService;

public class CommandFactory {

    private static final String LOGIN_COMMAND = "login";
    private static final String LOGOUT_COMMAND = "logout";
    private static final String SHOW_USER_MAIN_PAGE_COMMAND = "userMainPage";
    private static final String SHOW_ADMIN_MAIN_PAGE_COMMAND = "adminMainPage";
    private static final String CREATE_TRACK_COMMAND = "createTrack";
    private static final String SHOW_TRACK_LIST_PAGE_COMMAND = "trackList"; // common page for admin and user, but with buttons for only admin (edit, delete)
    private static final String USER_MAIN_PAGE = "/WEB-INF/view/userPages/userMainPage.jsp";
    private static final String ADMIN_MAIN_PAGE = "/WEB-INF/view/adminPages/adminMainPage.jsp";

    public Command create(String type) {
        switch (type) {
            case LOGIN_COMMAND:
                return new LoginCommand(new UserService(new DaoHelperFactory()));
            case SHOW_USER_MAIN_PAGE_COMMAND:
                return new ShowPageCommand(USER_MAIN_PAGE);
            case SHOW_ADMIN_MAIN_PAGE_COMMAND:
                return new ShowPageCommand(ADMIN_MAIN_PAGE);
            case LOGOUT_COMMAND:
                return new LogoutCommand();
            case CREATE_TRACK_COMMAND:
                return new CreateTrackCommand(new TrackService(new DaoHelperFactory()));
            case SHOW_TRACK_LIST_PAGE_COMMAND:
                return new ShowTrackListCommand(new TrackService(new DaoHelperFactory()));
            default:
                throw new IllegalArgumentException("Unknown command type = " + type);
        }

    }
}
