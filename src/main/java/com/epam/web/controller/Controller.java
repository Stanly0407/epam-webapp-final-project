package com.epam.web.controller;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandFactory;
import com.epam.web.commands.CommandResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Controller extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(Controller.class);
    private static final String PARAMETER_COMMAND = "command";
    private static final String CURRENT_PAGE = "currentPage";
    private static final String START_PAGE = "/";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_PAGE = "/WEB-INF/view/error.jsp";
    private static final String CHANGE_LANGUAGE_COMMAND = "changeLanguage";

    private CommandFactory commandFactory = new CommandFactory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String commandType = request.getParameter(PARAMETER_COMMAND);
        Command command = commandFactory.create(commandType);
        String page;
        boolean isRedirect = false;
        try {
            CommandResult result = command.execute(request, response);
            page = result.getPage();
            isRedirect = result.isRedirect();
        } catch (Exception e) {
            request.setAttribute(ERROR_MESSAGE, e.getMessage());
            page = ERROR_PAGE;
            LOGGER.error("ERROR: " + e + " | ERROR_MESSAGE: " + e.getMessage());
        }

        createCurrentPage(request, isRedirect, commandType, page);

        if (!isRedirect) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.include(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + page);
        }
    }

    private void createCurrentPage(HttpServletRequest request, boolean isRedirect, String commandType, String page) {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        if (currentPage == null) {
            session.setAttribute(CURRENT_PAGE, START_PAGE);
        }
        if (!isRedirect) {
            if (!commandType.equals(CHANGE_LANGUAGE_COMMAND)) {
                session.setAttribute(CURRENT_PAGE, ("/controller?command=" + commandType));
            }
        } else {
            session.setAttribute(CURRENT_PAGE, page);
        }
    }

}
