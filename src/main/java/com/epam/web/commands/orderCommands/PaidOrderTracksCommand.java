package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.OrderDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PaidOrderTracksCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(PaidOrderTracksCommand.class);

    private static final String USER_PAID_ORDERS_PAGE = "/WEB-INF/view/userPages/paidOrdersPage.jsp";
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_ORDERS_LIST = "orders";
    private static final String ATTRIBUTE_ORDER = "order";

    private final OrderService orderService;

    public PaidOrderTracksCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        List<OrderDto> paidOrders = orderService.getPaidOrders(userId);
        LOGGER.debug("paidOrders " + paidOrders);
        request.setAttribute(ATTRIBUTE_ORDERS_LIST, paidOrders);
        request.setAttribute(ATTRIBUTE_ORDER, new OrderDto());
        return CommandResult.forward(USER_PAID_ORDERS_PAGE);
    }

}
