package com.epam.web.commands.orderCommands;

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
    private static final String PARAMETER_BONUS_DISCOUNT_ID = "bonusDiscount";
    private static final String PARAMETER_BONUS_FREE_TRACKS_ID = "bonusFreeTracks";

    private final OrderService orderService;

    public PayOrderCommand(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long orderId = (Long) session.getAttribute(ATTRIBUTE_ORDER_ID);
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        String bonusDiscountIdParameter = request.getParameter(PARAMETER_BONUS_DISCOUNT_ID);
        String bonusFreeTracksIdParameter = request.getParameter(PARAMETER_BONUS_FREE_TRACKS_ID);
        Long bonusDiscountId = null;
        Long bonusFreeTracksId = null;
        if (bonusDiscountIdParameter != null) {
            bonusDiscountId = Long.parseLong(bonusDiscountIdParameter);
        }
        if (bonusFreeTracksIdParameter != null) {
            bonusFreeTracksId = Long.parseLong(bonusFreeTracksIdParameter);
        }


// далее получаем параметры этих бонусов. проверяем стринги на налл, если не налл то парсим в лонг, если налл,
// то не парсим, а присваеваем переменной лонг налл и в метод отправляем либо налл либо айди соответственно
        //в карт пэйдже сделать формочку с чекбаксами, т.е. будет запрос pay  как форма

        boolean payResult = orderService.payOrder(orderId, userId, bonusDiscountId, bonusFreeTracksId);
        LOGGER.debug("payResult " + payResult);
        if (payResult) {
            session.removeAttribute(ATTRIBUTE_ORDER_ID);
            return CommandResult.redirect(USER_MUSIC_PAGE);
        }
        return CommandResult.redirect(USER_ACCOUNT_PAGE); // вывести страницу аккаунта с сообщением о неудачной оплате
    }
}
