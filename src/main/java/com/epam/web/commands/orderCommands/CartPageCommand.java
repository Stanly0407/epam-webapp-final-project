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
import com.epam.web.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class CartPageCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(CartPageCommand.class);
    private static final String USER_CART_PAGE = "/WEB-INF/view/userPages/cartPage.jsp";
    private static final String ATTRIBUTE_ORDERED_TRACK_LIST = "trackList";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String ATTRIBUTE_ORDER_ID = "orderId";
    private static final String ORDER_AMOUNT = "orderAmount";
    private static final String BONUS_MESSAGE = "bonusMessage";
    private static final String BONUS_DISCOUNT_FIELD = "bonusDiscountExist";
    private static final String BONUS_DISCOUNT = "bonusDiscount";
    private static final String BONUS_FREE_TRACK_FIELD = "bonusFreeTracksExist";
    private static final String BONUS_FREE_TRACK = "bonusFreeTracks";
    private static final String CHECK_ACTIVATION_FREE_TRACK_BONUS_POSSIBILITY = "checkMessage";

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
        LOGGER.debug("orderedTracksClear " + orderedTracksClear);
        List<Track> orderedTracks = orderedTracksClear;

        Boolean activatedDiscountBonus = (Boolean) session.getAttribute("activatedDiscountBonus");
        LOGGER.debug("activatedDiscountBonus " + activatedDiscountBonus);
        Boolean activatedFreeTracksBonus = (Boolean) session.getAttribute("activatedFreeTracksBonus");
        LOGGER.debug("activatedFreeTracksBonus " + activatedFreeTracksBonus);

        Optional<Bonus> userDiscountBonus = bonusService.getUnusedUserDiscountBonus(userId);
        Optional<Bonus> userFreeTracksBonus = bonusService.getUnusedFreeTracksBonus(userId);
        Bonus bonusFreeTracks = null;
        if (!orderedTracksClear.isEmpty()) {
//1 Активируем поля скидок если они вообще есть
            if (userDiscountBonus.isPresent()) {
                Bonus bonusDiscount = userDiscountBonus.get();
                request.setAttribute(BONUS_DISCOUNT_FIELD, true);
                request.setAttribute(BONUS_DISCOUNT, bonusDiscount);
            }
            if (userFreeTracksBonus.isPresent()) {
                bonusFreeTracks = userFreeTracksBonus.get();
                request.setAttribute(BONUS_FREE_TRACK_FIELD, true);
                request.setAttribute(BONUS_FREE_TRACK, bonusFreeTracks);
            }
        } else if (userDiscountBonus.isPresent() || userFreeTracksBonus.isPresent()) {
            //  помимо сообщения что корзина пустая + сообщение в корзине - у вас есть бонусы
            request.setAttribute(BONUS_MESSAGE, true);
        }
//2 Проерка активации скидок и применение если есть к списку
        if(activatedDiscountBonus == null  ){
            LOGGER.debug("activatedDiscountBonus == null");
            session.setAttribute("activatedDiscountBonus", false);
            activatedDiscountBonus = false;
        }
        if(activatedFreeTracksBonus == null){
            LOGGER.debug("activatedFreeTracksBonus == null");
            session.setAttribute("activatedFreeTracksBonus", false);
            activatedFreeTracksBonus = false;
        }

        //2.1 Проверка что количество в корзине треков больше либо равно количеству бесплатных треков
        if(activatedFreeTracksBonus && bonusFreeTracks != null){
            int orderedTracksAmount = orderedTracksClear.size();
            int bonusFreeTracksAmount = bonusFreeTracks.getAmount();
            session.setAttribute("activatedFreeTracksBonus", false);
            request.setAttribute(CHECK_ACTIVATION_FREE_TRACK_BONUS_POSSIBILITY, true);
            activatedFreeTracksBonus = false;
        }
        //

        if (activatedDiscountBonus && activatedFreeTracksBonus) {
            List<Track> temporary = bonusService.applyBonusFreeTracks(orderedTracksClear, userId);
            orderedTracks = bonusService.applyBonusDiscount(temporary, userId);
            LOGGER.debug("1+2 orderedTracks " + orderedTracks);
        } else if (activatedDiscountBonus) {
            LOGGER.debug("1 before orderedTracks " + orderedTracks);
            orderedTracks = bonusService.applyBonusDiscount(orderedTracksClear, userId);
            LOGGER.debug("1 after orderedTracks " + orderedTracks);
        } else if (activatedFreeTracksBonus) {
            orderedTracks = bonusService.applyBonusFreeTracks(orderedTracksClear, userId);
            LOGGER.debug("2 orderedTracks " + orderedTracks);
        }

        //3 устанавливаем обновленный список треков с новыми ценами если есть скидки
        List<TrackDto> orderedTracksDto = trackService.createTrackDtoList(orderedTracks, userId);
        request.setAttribute(ATTRIBUTE_ORDERED_TRACK_LIST, orderedTracksDto);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());

        //4 считаем сумму корзины для юзера
        if(!orderedTracksDto.isEmpty()){
            BigDecimal orderSum = orderService.sumOfOrderedTracks(orderedTracks);
            request.setAttribute(ORDER_AMOUNT, orderSum);
        }
        return CommandResult.forward(USER_CART_PAGE);
    }
}
