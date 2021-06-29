package com.epam.web.commands;

import com.epam.web.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * An interface {@code Command} defines a processing request method for from the client
 * and returns concrete processing result for response to the client
 *
 * @author Sviatlana Shelestava
 * @see com.epam.web.commands.CommandFactory
 * @see com.epam.web.commands.CommandType
 * @since 1.0
 */
public interface Command {

    /**
     * Handles specific client requests using the business logic of this application.
     *
     * @param request  provide HTTP-request information for processing
     * @param response to assist a servlet in sending a response to the client.
     * @return an object of <code>CommandResult</code> containing a page and information about how
     *         the result is transferred to the client (forward or redirect);
     * @throws ServiceException If a validation error, incorrect request, or other error in the business logic
     *         of this application occurs during the processing of a client's request.
     */
    CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException;
}
