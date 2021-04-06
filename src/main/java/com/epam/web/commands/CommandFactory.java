package com.epam.web.commands;

import com.epam.web.commands.trackCommands.*;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.service.MusicCollectionService;
import com.epam.web.service.TrackService;
import com.epam.web.service.UserService;

public class CommandFactory {

    private static final String LOGIN_COMMAND = "login";
    private static final String LOGOUT_COMMAND = "logout";
    private static final String SHOW_USER_MAIN_PAGE_COMMAND = "userMainPage";

    private static final String SHOW_ADMIN_MAIN_PAGE_COMMAND = "adminMainPage";
    private static final String CREATE_EDIT_TRACK_COMMAND = "editSaveTrack";
    private static final String SHOW_ADMIN_TRACK_LIST_PAGE_COMMAND = "adminTrackList";

    private static final String SHOW_SEARCH_MUSIC_RESULT_COMMAND = "searchMusicResult";
    private static final String SEARCH_MUSIC_COMMAND = "searchMusic";

    private static final String ADMIN_MAIN_PAGE = "/WEB-INF/view/adminPages/adminMainPage.jsp";
    private static final String EDIT_TRACK_FORM_PAGE_COMMAND = "editTrack";


    public Command create(String type) {
        switch (type) {
            case LOGIN_COMMAND:
                return new LoginCommand(new UserService(new DaoHelperFactory()));
            case LOGOUT_COMMAND:
                return new LogoutCommand();
//            case SHOW_USER_MAIN_PAGE_COMMAND:
//                return new ShowPageCommand(USER_MAIN_PAGE);
            case SHOW_USER_MAIN_PAGE_COMMAND:
                return new ShowUserTrackListCommand(new TrackService(new DaoHelperFactory()), new MusicCollectionService(new DaoHelperFactory()));
            case SHOW_ADMIN_MAIN_PAGE_COMMAND:
                return new ShowPageCommand(ADMIN_MAIN_PAGE);

            case CREATE_EDIT_TRACK_COMMAND:
                return new EditTrackCommand(new TrackService(new DaoHelperFactory()));
            case SHOW_ADMIN_TRACK_LIST_PAGE_COMMAND:
                return new ShowAdminTrackListCommand(new TrackService(new DaoHelperFactory()));
            case EDIT_TRACK_FORM_PAGE_COMMAND:
                return new ShowEditTrackFormCommand(new TrackService(new DaoHelperFactory()));
            case SEARCH_MUSIC_COMMAND:
                return new ShowSearchMusicResultCommand();
            case SHOW_SEARCH_MUSIC_RESULT_COMMAND:
                return new SearchMusicCommand(new TrackService(new DaoHelperFactory()));

            default:
                throw new IllegalArgumentException("Unknown command type = " + type);
        }

    }
}
