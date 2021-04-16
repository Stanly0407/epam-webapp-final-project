package com.epam.web.commands;

import com.epam.web.commands.orderCommands.*;
import com.epam.web.commands.trackCommands.*;
import com.epam.web.commands.userCommands.TopUpBalanceCommand;
import com.epam.web.commands.userCommands.UserAccountCommand;
import com.epam.web.commands.userCommands.UserMainPageCommand;
import com.epam.web.commands.userCommands.UserMusicCommand;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.service.MusicCollectionService;
import com.epam.web.service.OrderService;
import com.epam.web.service.TrackService;
import com.epam.web.service.UserService;

public class CommandFactory {

    // COMMON
    private static final String LOGIN_COMMAND = "login";
    private static final String LOGOUT_COMMAND = "logout";
    // ADMIN
    private static final String SHOW_ADMIN_MAIN_PAGE_COMMAND = "adminMainPage";
    private static final String SHOW_ADMIN_TRACK_LIST_PAGE_COMMAND = "adminTrackList";
    private static final String SAVE_EDIT_TRACK_COMMAND = "editSaveTrack";
    private static final String EDIT_TRACK_FORM_PAGE_COMMAND = "editTrack";
    private static final String ADMIN_MAIN_PAGE = "/WEB-INF/view/adminPages/adminMainPage.jsp";
    // USER
    private static final String SHOW_USER_MAIN_PAGE_COMMAND = "userMainPage";
    private static final String USER_ACCOUNT_COMMAND = "userAccount";
    private static final String TOP_UP_BALANCE_PAGE_COMMAND = "topUpBalancePage";
    private static final String TOP_UP_BALANCE_PAGE = "/WEB-INF/view/userPages/topUpBalancePage.jsp";
    private static final String TOP_UP_BALANCE_COMMAND = "topUpBalance";
    private static final String SEARCH_MUSIC_COMMAND = "searchMusic";
    private static final String SHOW_SEARCH_MUSIC_RESULT_COMMAND = "searchMusicResult";
    private static final String USER_MUSIC_LIST_COMMAND = "userMusic";
    //ORDER
    private static final String SHOW_CART_PAGE_COMMAND = "cart";
    private static final String ADD_TRACK_TO_CART_COMMAND = "addTrack";
    private static final String DELETE_TRACK_FROM_CART_COMMAND = "deleteTrack";
    private static final String PAY_ORDER_COMMAND = "payOrder";
    private static final String PAID_ORDERS_LIST_COMMAND = "paymentHistory";
    private static final String PURCHASED_ORDER_TRACKS_LIST_COMMAND = "purchasedTracks";
    private static final String SHOW_ALL_MUSIC_COMMAND = "allMusic";

    public Command create(String type) {
        switch (type) {
            case LOGIN_COMMAND:
                return new LoginCommand(new UserService(new DaoHelperFactory()), new OrderService(new DaoHelperFactory()));
            case LOGOUT_COMMAND:
                return new LogoutCommand();
            case SHOW_ALL_MUSIC_COMMAND:
                return new AllMusicCommand(new TrackService(new DaoHelperFactory()), new MusicCollectionService(new DaoHelperFactory()));
            // USER
            case SHOW_USER_MAIN_PAGE_COMMAND:
                return new UserMainPageCommand(new TrackService(new DaoHelperFactory()), new MusicCollectionService(new DaoHelperFactory()));
            case SEARCH_MUSIC_COMMAND:
                return new SearchMusicCommand();
            case SHOW_SEARCH_MUSIC_RESULT_COMMAND:
                return new SearchMusicResultCommand(new TrackService(new DaoHelperFactory()), new MusicCollectionService(new DaoHelperFactory()));
            case USER_ACCOUNT_COMMAND:
                return new UserAccountCommand(new UserService(new DaoHelperFactory()));
            case TOP_UP_BALANCE_PAGE_COMMAND:
                return new ShowPageCommand(TOP_UP_BALANCE_PAGE);
            case TOP_UP_BALANCE_COMMAND:
                return new TopUpBalanceCommand(new UserService(new DaoHelperFactory()));
            case USER_MUSIC_LIST_COMMAND:
                return new UserMusicCommand(new TrackService(new DaoHelperFactory()));
            // ORDER
            case SHOW_CART_PAGE_COMMAND:
                return new CartPageCommand(new OrderService(new DaoHelperFactory()), new TrackService(new DaoHelperFactory()));
            case ADD_TRACK_TO_CART_COMMAND:
                return new AddTrackToCartCommand(new OrderService(new DaoHelperFactory()));
            case DELETE_TRACK_FROM_CART_COMMAND:
                return new DeleteTrackFromCartCommand(new OrderService(new DaoHelperFactory()));
            case PAY_ORDER_COMMAND:
                return new PayOrderCommand(new OrderService(new DaoHelperFactory()));
            case PAID_ORDERS_LIST_COMMAND:
                return new PaidOrderTracksCommand(new OrderService(new DaoHelperFactory()));
            case PURCHASED_ORDER_TRACKS_LIST_COMMAND:
                return new PaidMusicCommand(new TrackService(new DaoHelperFactory()));
            //ADMIN
            case SHOW_ADMIN_MAIN_PAGE_COMMAND:
                return new ShowPageCommand(ADMIN_MAIN_PAGE);
            case SHOW_ADMIN_TRACK_LIST_PAGE_COMMAND:
                return new AdminTrackListCommand(new TrackService(new DaoHelperFactory()));
            case EDIT_TRACK_FORM_PAGE_COMMAND:
                return new EditTrackFormCommand(new TrackService(new DaoHelperFactory()));
            case SAVE_EDIT_TRACK_COMMAND:
                return new EditTrackCommand(new TrackService(new DaoHelperFactory()));
            default:
                throw new IllegalArgumentException("Unknown command type = " + type);
        }
    }
}
