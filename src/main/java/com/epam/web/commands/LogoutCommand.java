package com.epam.web.commands;

import com.epam.web.exceptions.ServiceException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(LogoutCommand.class);

    private static final String LOGIN_PAGE = "/WEB-INF/view/login.jsp";

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        LOGGER.debug("logout user = " + request.getSession().getAttribute("name"));
        request.getSession().invalidate();
        return CommandResult.forward(LOGIN_PAGE);
    }
}

