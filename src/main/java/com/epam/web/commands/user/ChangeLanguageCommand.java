package com.epam.web.commands.user;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ChangeLanguageCommand implements Command {
    private static final String LANGUAGE = "language";
    private static final String GERMAN = "de";
    private static final String RUSSIAN = "ru";
    private static final String ENGLISH_LOCAL = "en_US";
    private static final String GERMAN_LOCAL = "de_DE";
    private static final String RUSSIAN_LOCAL = "ru_RU";
    private static final String LOCAL = "local";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String ATTRIBUTE_USER = "userId";
    private static final String START_PAGE = "/";

    public ChangeLanguageCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER);
        if (currentPage == null || userId == null) {
            currentPage = START_PAGE;
        }
        String language = request.getParameter(LANGUAGE);
        switch (language) {
            case GERMAN:
                session.setAttribute(LOCAL, GERMAN_LOCAL);
                break;
            case RUSSIAN:
                session.setAttribute(LOCAL, RUSSIAN_LOCAL);
                break;
            default:
                session.setAttribute(LOCAL, ENGLISH_LOCAL);
        }
        return CommandResult.forward(currentPage);
    }

}
