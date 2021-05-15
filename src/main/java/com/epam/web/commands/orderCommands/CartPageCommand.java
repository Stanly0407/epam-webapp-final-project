package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.BonusType;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;
import com.epam.web.service.OrderService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CartPageCommand implements Command {

    private static final String USER_CART_PAGE = "/WEB-INF/view/userPages/cartPage.jsp";
    private static final String ATTRIBUTE_ORDERED_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_ORDER_ID = "orderId";
    private static final String ATTRIBUTE_ORDER_AMOUNT = "orderAmount";
    private static final String BONUS_MESSAGE = "bonusMessage";
    private static final String BONUS_DISCOUNT_FIELD = "bonusDiscountExist";
    private static final String BONUS_DISCOUNT = "bonusDiscount";
    private static final String BONUS_FREE_TRACK_FIELD = "bonusFreeTracksExist";
    private static final String BONUS_FREE_TRACK = "bonusFreeTracks";

    private static final String BONUS_DISCOUNT_ID = "bonusDiscount";
    private static final String BONUS_FREE_TRACKS_ID = "bonusFreeTracks";

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
        if (orderId == null) {
            orderId = orderService.getCurrentCartId(userId);
            session.setAttribute(ATTRIBUTE_ORDER_ID, orderId);
        }

        List<Track> orderedTracksClear = trackService.getOrderedTracksList(userId);
        List<Track> orderedTracks = orderedTracksClear;

        Boolean activatedDiscountBonus = (Boolean) session.getAttribute("activatedDiscountBonus");
        Boolean activatedFreeTracksBonus = (Boolean) session.getAttribute("activatedFreeTracksBonus");

        Optional<Bonus> userDiscountBonus = bonusService.getUnusedUserDiscountBonus(userId);
        Optional<Bonus> userFreeTracksBonus = bonusService.getUnusedFreeTracksBonus(userId);

        if (!orderedTracksClear.isEmpty()) {
//1 Активируем поля скидок если они вообще есть
            if (userDiscountBonus.isPresent()) {
                Bonus bonusDiscount = userDiscountBonus.get();
                request.setAttribute(BONUS_DISCOUNT_FIELD, true);
                request.setAttribute(BONUS_DISCOUNT, bonusDiscount);
            }
            if (userFreeTracksBonus.isPresent()) {
                Bonus bonusFreeTracks = userFreeTracksBonus.get();
                request.setAttribute(BONUS_FREE_TRACK_FIELD, true);
                request.setAttribute(BONUS_FREE_TRACK, bonusFreeTracks);
            }
        } else if (userDiscountBonus.isPresent() || userFreeTracksBonus.isPresent()) {
            //  помимо сообщения что корзина пустая + сообщение в корзине - у вас есть бонусы
            request.setAttribute("bonusMessage", true);
        }
//2 Проерка активации скидок и применение если есть к списку
        if (activatedDiscountBonus && activatedFreeTracksBonus) {
            List<Track> temporary = orderService.applyBonusFreeTracks(orderedTracksClear, userId);
            orderedTracks = orderService.applyBonusDiscount(temporary, userId);
        } else if (activatedDiscountBonus) {
            orderedTracks = orderService.applyBonusDiscount(orderedTracksClear, userId);
            session.setAttribute("activatedFreeTracksBonus", false);
        } else if (activatedFreeTracksBonus) {
            orderedTracks = orderService.applyBonusFreeTracks(orderedTracksClear, userId);
            session.setAttribute("activatedDiscountBonus", false);
        }
        //3 устанавливаем обновленный список треков с новыми ценами если есть скидки
        List<TrackDto> orderedTracksDto = trackService.createTrackDtoList(orderedTracks, userId);
        request.setAttribute(ATTRIBUTE_ORDERED_TRACK_LIST, orderedTracksDto);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());

        //4 считаем сумму корзины для юзера
        BigDecimal orderSum = orderService.sumOfOrderedTracks(orderedTracks);
        request.setAttribute("orderSum", orderSum);

        return CommandResult.forward(USER_CART_PAGE);
    }
}
