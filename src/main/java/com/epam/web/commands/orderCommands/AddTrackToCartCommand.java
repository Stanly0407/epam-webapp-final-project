package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.OrderService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class AddTrackToCartCommand implements Command {
    private static final String USER_CART_PAGE = "/controller?command=cart";
    private static final String PARAMETER_TRACK_ID = "id";
    private static final String ATTRIBUTE_USER_ID = "userId";

    private final OrderService orderService;

    public AddTrackToCartCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        String trackIdString = request.getParameter(PARAMETER_TRACK_ID);
        Long trackId = Long.valueOf(trackIdString);
        orderService.addTrackToCart(userId, trackId);
        return CommandResult.redirect(USER_CART_PAGE);
    }
}
