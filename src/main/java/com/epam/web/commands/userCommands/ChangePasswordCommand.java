package com.epam.web.commands.userCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangePasswordCommand implements Command {

    private static final String USER_ACCOUNT_PAGE_COMMAND = "/controller?command=userMainPage";

    private final UserService userService;

    public ChangePasswordCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String newPassword = request.getParameter("password");
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        userService.changePassword(newPassword, userId);
        return CommandResult.redirect(USER_ACCOUNT_PAGE_COMMAND);
    }

}
