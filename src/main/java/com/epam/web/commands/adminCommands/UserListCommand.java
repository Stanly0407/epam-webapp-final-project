package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.MusicCollection;
import com.epam.web.entities.User;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class UserListCommand implements Command {
    private static final String USER_LIST_PAGE_COMMAND = "/WEB-INF/view/adminPages/userListPage.jsp";
    private static final String ATTRIBUTE_USER_LIST = "userList";
    private static final String ATTRIBUTE_USER = "user";

    private final UserService userService;

    public UserListCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        List<User> userList = userService.getAllUsers();
        request.setAttribute(ATTRIBUTE_USER_LIST, userList);
        request.setAttribute(ATTRIBUTE_USER, new User());
        return CommandResult.forward(USER_LIST_PAGE_COMMAND);
    }
}
