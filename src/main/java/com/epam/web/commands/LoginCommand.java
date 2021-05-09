package com.epam.web.commands;


import com.epam.web.entities.User;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.OrderService;
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
    private static final String ATTRIBUTE_USER = "userId";
    private static final String ATTRIBUTE_ROLE = "role";
    private static final String ATTRIBUTE_ORDER_ID = "orderId";
    private static final String ERROR_UNKNOWN_USER = "unknownUser";
    private static final String ERROR_BLOCKED_USER = "blockedUser";
    private static final String LOGIN_PAGE = "loginPage";

    private final UserService userService;
    private final OrderService orderService;

    public LoginCommand(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String login = request.getParameter(PARAMETER_LOGIN);
        String password = request.getParameter(PARAMETER_PASSWORD);

        Optional<User> optionalUser = userService.login(login, password);
        HttpSession session = request.getSession(false);
        String showPageCommandType;
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Long userId = user.getId();
            String userRole = user.getRole().toString();
            if (!user.getStatus()) {
                session.setAttribute(ATTRIBUTE_USER, userId);
                session.setAttribute(ATTRIBUTE_ROLE, userRole);
                session.setAttribute(ATTRIBUTE_NAME, user.getName());
                Long orderId = orderService.getCurrentCartId(userId);
                session.setAttribute(ATTRIBUTE_ORDER_ID, orderId);
                if (user.getRole().toString().equals(ADMIN_ROLE)) {
                    showPageCommandType = SHOW_ADMIN_MAIN_PAGE_COMMAND;
                } else {
                    showPageCommandType = SHOW_USER_MAIN_PAGE_COMMAND;
                }
            } else {
                LOGGER.debug("ERROR_BLOCKED_USER");
                session.setAttribute(ERROR_BLOCKED_USER, true);
                showPageCommandType = LOGIN_PAGE;
            }
        } else {
            LOGGER.debug("ERROR_UNKNOWN_USER");
            session.setAttribute(ERROR_UNKNOWN_USER, true);
            showPageCommandType = LOGIN_PAGE;
        }
        return CommandResult.redirect(CONTROLLER_COMMAND + showPageCommandType);
    }

}

