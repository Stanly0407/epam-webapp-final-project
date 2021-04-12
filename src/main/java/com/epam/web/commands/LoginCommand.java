package com.epam.web.commands;


import com.epam.web.entities.User;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class LoginCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LoginCommand.class);

    private static final String PARAMETER_LOGIN = "login";
    private static final String PARAMETER_PASSWORD = "password";
    private static final String ADMIN_ROLE = "ADMIN";
    private static final String CONTROLLER_COMMAND = "/controller?command=";
    private static final String SHOW_USER_MAIN_PAGE_COMMAND = "userMainPage";
    private static final String SHOW_ADMIN_MAIN_PAGE_COMMAND = "adminMainPage";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String USER = "userId";

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter(PARAMETER_LOGIN);
        String password = request.getParameter(PARAMETER_PASSWORD);
        Optional<User> optionalUser = userService.login(login, password);
        HttpSession session = request.getSession(false);
        String showPageCommandType = null;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            session.setAttribute(USER, user.getId());
            session.setAttribute(ATTRIBUTE_NAME, user.getName());
            if (user.getRole().getValue().equals(ADMIN_ROLE)) {
                showPageCommandType = SHOW_ADMIN_MAIN_PAGE_COMMAND;
            } else {
                showPageCommandType = SHOW_USER_MAIN_PAGE_COMMAND;
            }
        }
        LOGGER.debug("login session = " + session.getAttribute(ATTRIBUTE_NAME) + "||| session ID: " + session.getId());
        LOGGER.debug("login " + session.getAttribute(USER));

        return CommandResult.redirect(CONTROLLER_COMMAND + showPageCommandType);
    }

}

