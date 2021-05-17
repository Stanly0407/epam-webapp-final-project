package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;
import com.epam.web.service.OrderService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class PayOrderCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(PayOrderCommand.class);

    private static final String USER_MUSIC_PAGE = "/controller?command=userMusic";
    private static final String USER_ACCOUNT_PAGE = "/controller?command=userAccount";
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_ORDER_ID = "orderId";

    private final OrderService orderService;

    public PayOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        Long orderId = (Long) session.getAttribute(ATTRIBUTE_ORDER_ID);

        Boolean activatedDiscountBonus = (Boolean) session.getAttribute("activatedDiscountBonus");
        Boolean activatedFreeTracksBonus = (Boolean) session.getAttribute("activatedFreeTracksBonus");

        boolean payResult = orderService.payOrder(orderId, userId, activatedDiscountBonus, activatedFreeTracksBonus);
        LOGGER.debug("payResult " + payResult);
        if (payResult) {
            session.removeAttribute(ATTRIBUTE_ORDER_ID);
            session.removeAttribute("activatedDiscountBonus");
            session.removeAttribute("activatedFreeTracksBonus");
            return CommandResult.redirect(USER_MUSIC_PAGE);
        }
        return CommandResult.redirect(USER_ACCOUNT_PAGE); // вывести страницу аккаунта с сообщением о неудачной оплате
    }
}
