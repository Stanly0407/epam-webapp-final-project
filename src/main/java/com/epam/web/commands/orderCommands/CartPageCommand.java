package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.BonusType;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;
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
    private static final String BONUS_MESSAGE = "bonusMessage";
    private static final String BONUS_DISCOUNT_BUTTON = "bonusDiscountExist";
    private static final String BONUS_DISCOUNT = "bonusDiscount";
    private static final String BONUS_FREE_TRACK_BUTTON = "bonusFreeTracksExist";
    private static final String BONUS_FREE_TRACK = "bonusFreeTracks";

    private final OrderService orderService;
    private final TrackService trackService;
    private final BonusService bonusService;

    public CartPageCommand(OrderService orderService, TrackService trackService, BonusService bonusService) {
        this.orderService = orderService;
        this.trackService = trackService;
        this.bonusService = bonusService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        Long orderId = (Long) session.getAttribute(ATTRIBUTE_ORDER_ID);
        List<Bonus> bonuses = bonusService.getUnusedUserBonuses(userId);
        if (orderId == null) {
            orderId = orderService.getCurrentCartId(userId);
            session.setAttribute(ATTRIBUTE_ORDER_ID, orderId);
            if(!bonuses.isEmpty()){
                request.setAttribute(BONUS_MESSAGE, true);
            }
        }
        List<TrackDto> orderedTracks = trackService.getOrderedTracks(userId);
        if (!orderedTracks.isEmpty()) {
            BigDecimal orderAmount = orderService.sumOfOrderedTracks(orderedTracks);
            request.setAttribute(ATTRIBUTE_ORDER_AMOUNT, orderAmount);
            request.setAttribute(ATTRIBUTE_TRACK_LIST, orderedTracks);
            request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
            if(!bonuses.isEmpty()){
                for (Bonus bonus : bonuses){
                    BonusType type = bonus.getBonusType();
                    if(BonusType.DISCOUNT.equals(type)){
                        request.setAttribute(BONUS_DISCOUNT_BUTTON, true);
                        request.setAttribute(BONUS_DISCOUNT, bonus);
                    } else {
                        request.setAttribute(BONUS_FREE_TRACK_BUTTON, true);
                        request.setAttribute(BONUS_FREE_TRACK, bonus);
                    }
                }
            }
        }
        return CommandResult.forward(USER_CART_PAGE);
    }
}
