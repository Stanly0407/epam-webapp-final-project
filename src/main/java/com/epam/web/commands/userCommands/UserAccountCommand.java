package com.epam.web.commands.userCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.User;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;

public class UserAccountCommand implements Command {

    private static final String USER_ACCOUNT_PAGE = "/WEB-INF/view/userPages/userAccountPage.jsp";
    private static final String USER = "user";

    private final UserService userService;

    public UserAccountCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        Optional<User> optionalUser = userService.getUserInfo(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            request.setAttribute(USER, user);
        }
        return CommandResult.forward(USER_ACCOUNT_PAGE);
    }
}
