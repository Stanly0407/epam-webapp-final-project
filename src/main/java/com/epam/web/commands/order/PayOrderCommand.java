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

public class PayOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(PayOrderCommand.class);

    private static final String USER_MUSIC_PAGE = "/controller?command=userMusic";
    private static final String USER_ACCOUNT_PAGE = "/controller?command=userAccount";
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_ORDER_ID = "orderId";
    private static final String ACTIVATED_DISCOUNT = "activatedDiscountBonus";
    private static final String ACTIVATED_FREE_TRACKS = "activatedFreeTracksBonus";

    private final OrderService orderService;

    public PayOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        Long orderId = (Long) session.getAttribute(ATTRIBUTE_ORDER_ID);

        Boolean activatedDiscountBonus = (Boolean) session.getAttribute(ACTIVATED_DISCOUNT);
        Boolean activatedFreeTracksBonus = (Boolean) session.getAttribute(ACTIVATED_FREE_TRACKS);

        boolean payResult = orderService.payOrder(orderId, userId, activatedDiscountBonus, activatedFreeTracksBonus);
        LOGGER.debug("payResult " + payResult);
        if (payResult) {
            session.removeAttribute(ATTRIBUTE_ORDER_ID);
            session.removeAttribute(ACTIVATED_DISCOUNT);
            session.removeAttribute(ACTIVATED_FREE_TRACKS);
            return CommandResult.redirect(USER_MUSIC_PAGE);
        }
        return CommandResult.redirect(USER_ACCOUNT_PAGE);
    }
}
