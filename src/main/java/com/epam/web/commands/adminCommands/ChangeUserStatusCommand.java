package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ChangeUserStatusCommand implements Command {
    private static final String USER_LIST_COMMAND = "/controller?command=userList";
    private static final String PARAMETER_USER_STATUS = "status";
    private static final String PARAMETER_USER_ID = "id";

    private final UserService userService;

    public ChangeUserStatusCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String idString = request.getParameter(PARAMETER_USER_ID);
        String statusString = request.getParameter(PARAMETER_USER_STATUS);
        boolean status = Boolean.parseBoolean(statusString);
        Long userId = Long.valueOf(idString);
        userService.changeUserStatus(!status, userId);
        return CommandResult.forward(USER_LIST_COMMAND);
    }
}
