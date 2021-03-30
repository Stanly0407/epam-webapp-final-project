package com.epam.web.commands;

import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.service.UserService;

public class CommandFactory {

    private static final String LOGIN_COMMAND = "login";
    private static final String LOGOUT_COMMAND = "logout";
    private static final String SHOW_USER_MAIN_PAGE_COMMAND = "userMainPage";
    private static final String SHOW_ADMIN_MAIN_PAGE_COMMAND = "adminMainPage";


    public Command create(String type) {
        switch (type) {
            case LOGIN_COMMAND:
                return new LoginCommand(new UserService(new DaoHelperFactory()));
            case SHOW_USER_MAIN_PAGE_COMMAND:
                return new ShowPageCommand("/WEB-INF/view/userPages/userMainPage.jsp");
            case SHOW_ADMIN_MAIN_PAGE_COMMAND:
                return new ShowPageCommand("/WEB-INF/view/adminPages/adminMainPage.jsp");
            case LOGOUT_COMMAND:
                return new LogoutCommand();
            default:
                throw new IllegalArgumentException("Unknown command type = " + type);
        }

    }
}
