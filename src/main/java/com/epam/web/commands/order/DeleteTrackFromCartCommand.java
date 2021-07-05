package com.epam.web.commands.order;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeleteTrackFromCartCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(DeleteTrackFromCartCommand.class);
    private static final String ATTRIBUTE_ORDER_ID = "orderId";
    private static final String PARAMETER_TRACK_ID = "id";
    private static final String CURRENT_PAGE = "currentPage";

    private final OrderService orderService;

    public DeleteTrackFromCartCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        String currentPage = (String) session.getAttribute(CURRENT_PAGE);
        Long orderId = (Long) session.getAttribute(ATTRIBUTE_ORDER_ID);
        String trackIdString = request.getParameter(PARAMETER_TRACK_ID);
        LOGGER.debug("trackIdString " + trackIdString);
        Long trackId = Long.valueOf(trackIdString);
        orderService.deleteTrackFromCart(orderId, trackId);
        return CommandResult.redirect(currentPage);
    }


}
