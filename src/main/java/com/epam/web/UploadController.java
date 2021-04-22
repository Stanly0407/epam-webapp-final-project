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
import java.io.IOException;

public class UploadController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(UploadController.class);
    private static final String PARAMETER_COMMAND = "command";

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
        LOGGER.debug("PARAMETER_COMMAND = " + commandType);

        Command command = commandFactory.create(commandType);
        String page = null;
        boolean isRedirect = false;
        try {
            CommandResult result = command.execute(request, response);
            page = result.getPage();
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
        } else {
            response.sendRedirect(request.getContextPath() + page);
        }
    }

}
