package com.epam.web.commands;

import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.service.UserService;

public class CommandFactory {

    private static final String LOGIN_COMMAND = "login";
    private static final String SHOW_MAIN_PAGE_COMMAND = "mainPage";

    public Command create(String type) {
        switch (type) {
            case LOGIN_COMMAND:
                return new LoginCommand(new UserService(new DaoHelperFactory()));
            //return new LoginCommand(new UserService(new UserDaoImpl()));
            case SHOW_MAIN_PAGE_COMMAND:
                return new ShowPageCommand("/WEB-INF/mainPage.jsp");
            default:
                throw new IllegalArgumentException("Unknown command type = " + type);
        }

    }
}
