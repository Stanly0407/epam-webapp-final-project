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
    private static final String SHOW_USER_MAIN_PAGE_COMMAND = "userMainPage";
    private static final String SHOW_ADMIN_MAIN_PAGE_COMMAND = "adminMainPage";

    private final UserService userService;

    public LoginCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter(PARAMETER_LOGIN);
        String password = request.getParameter(PARAMETER_PASSWORD);
        Optional<User> optionalUser = userService.login(login, password);
        LOGGER.debug("User = " + optionalUser);
        HttpSession session = request.getSession(false);
        String showPageCommandType = null;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getRole().getValue().equals(ADMIN_ROLE)) {
                session.setAttribute("name", user.getName());
                showPageCommandType = SHOW_ADMIN_MAIN_PAGE_COMMAND;
            } else {
                session.setAttribute("name", user.getName());
                showPageCommandType = SHOW_USER_MAIN_PAGE_COMMAND;
            }
        }
        LOGGER.debug("login session = " + session.getAttribute("name") + "||| session ID: " + session.getId());

        return CommandResult.redirect("/controller?command=" + showPageCommandType);
    }

}

