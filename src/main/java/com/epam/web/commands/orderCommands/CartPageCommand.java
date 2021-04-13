package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.OrderService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;

public class CartPageCommand implements Command {

    private static final String USER_CART_PAGE = "/WEB-INF/view/userPages/cartPage.jsp";
    private static final String ATTRIBUTE_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_ORDER_ID = "orderId";
    private static final String ATTRIBUTE_ORDER_AMOUNT = "orderAmount";

    private final OrderService orderService;
    private final TrackService trackService;

    public CartPageCommand(OrderService orderService, TrackService trackService) {
        this.orderService = orderService;
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        Long orderId = (Long) session.getAttribute(ATTRIBUTE_ORDER_ID);
        if (orderId == null) {
            orderId = orderService.getCurrentCartId(userId);
            session.setAttribute(ATTRIBUTE_ORDER_ID, orderId);
        }
        List<TrackDto> orderedTracks = trackService.getOrderedTracks(userId);
        if (!orderedTracks.isEmpty()) {
            BigDecimal orderAmount = orderService.sumOfOrderedTracks(orderedTracks);
            request.setAttribute(ATTRIBUTE_ORDER_AMOUNT, orderAmount);
        }
        request.setAttribute(ATTRIBUTE_TRACK_LIST, orderedTracks); // если пусто, то выводить сообщение (в jsp это )
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());


        return CommandResult.forward(USER_CART_PAGE);
    }
}
