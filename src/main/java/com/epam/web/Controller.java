package com.epam.web;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandFactory;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
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
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        LOGGER.debug("session CURRENT_PAGE" + currentPage);
        if (currentPage == null) {
            session.setAttribute(CURRENT_PAGE, START_PAGE);
        }

        String commandType = request.getParameter(PARAMETER_COMMAND);
        Command command = commandFactory.create(commandType);
        String page = null;
        boolean isRedirect = false;
        try {
            CommandResult result = command.execute(request, response);
            page = result.getPage();
            LOGGER.debug("commandType = " + commandType);
            isRedirect = result.isRedirect();
        } catch (ServiceException e) {
            request.setAttribute("errorMessage ----", e.getMessage());
            page = "/error.jsp";
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (!isRedirect) {
            RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher(page);
            requestDispatcher.forward(request, response);
            if (!commandType.equals("changeLanguage")) {
                session.setAttribute(CURRENT_PAGE, ("/controller?command=" + commandType));
            }
            LOGGER.debug("forward_ " + page);
        } else {
            response.sendRedirect(request.getContextPath() + page);
            session.setAttribute(CURRENT_PAGE, page);
            LOGGER.debug("redirect_" + page);
        }
    }

}
