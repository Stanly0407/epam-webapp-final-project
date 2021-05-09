package com.epam.web.commands;

import com.epam.web.commands.adminCommands.*;
import com.epam.web.commands.orderCommands.*;
import com.epam.web.commands.trackCommands.*;
import com.epam.web.commands.userCommands.*;
import com.epam.web.dao.DaoHelperFactory;
import com.epam.web.service.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

public class CommandFactory {
    private static final Logger LOGGER = LogManager.getLogger(CommandFactory.class);
    private static final String ADMIN_MAIN_PAGE = "/WEB-INF/view/adminPages/adminMainPage.jsp";
    private static final String ARTIST_FORM_PAGE = "/WEB-INF/view/fragments/artistForm.jsp";
    private static final String PLAYLIST_FORM_PAGE = "/WEB-INF/view/fragments/playlistForm.jsp";
    private static final String REFILL_BALANCE_PAGE = "/WEB-INF/view/userPages/refillBalancePage.jsp";
    private static final String LOGIN_MAIN_PAGE = "/WEB-INF/view/login.jsp";
    private static final String SPLIT_CAMEL_CASE_PATTERN = "(?<=[a-z])(?=[A-Z])";
    private static final String LOW_LINE = "_";
    private static final int LAST_INDEX = 1;

    public Command create(String type) {
        LOGGER.debug("type " + type);
        CommandType commandType = getCommandType(type);
        switch (commandType) {
            case LOGIN:
                return new LoginCommand(new UserService(new DaoHelperFactory()), new OrderService(new DaoHelperFactory()));
            case LOGIN_PAGE:
                return new ShowPageCommand(LOGIN_MAIN_PAGE);
            case LOGOUT:
                return new LogoutCommand();
            case ALL_MUSIC:
            case NEXT_PAGE:
                return new AllMusicCommand(new TrackService(new DaoHelperFactory()), new MusicCollectionService(new DaoHelperFactory()));
            case CHANGE_LANGUAGE:
                return new ChangeLanguageCommand();
            // USER
            case USER_MAIN_PAGE:
                return new UserMainPageCommand(new TrackService(new DaoHelperFactory()), new MusicCollectionService(new DaoHelperFactory()));
            case SEARCH_MUSIC:
                return new SearchMusicCommand();
            case SEARCH_MUSIC_RESULT:
                return new SearchMusicResultCommand(new TrackService(new DaoHelperFactory()), new MusicCollectionService(new DaoHelperFactory()));
            case USER_ACCOUNT:
                return new UserAccountCommand(new UserService(new DaoHelperFactory()));
            case REFILL_BALANCE_PAGE:
                return new ShowPageCommand(REFILL_BALANCE_PAGE);
            case REFILL_BALANCE:
                return new RefillBalanceCommand(new UserService(new DaoHelperFactory()));
            case USER_MUSIC:
                return new UserMusicCommand(new TrackService(new DaoHelperFactory()));
            case COMMENTS_PAGE:
                return new TrackCommentsCommand(new TrackService(new DaoHelperFactory()), new CommentService(new DaoHelperFactory()));
            case ADD_COMMENT:
                return new AddCommentToTrackCommand(new CommentService(new DaoHelperFactory()));
            case EDIT_COMMENT:
                return new EditCommentFormCommand(new TrackService(new DaoHelperFactory()), new CommentService(new DaoHelperFactory()));
            case SAVE_EDITED_COMMENT:
                return new EditCommentCommand(new CommentService(new DaoHelperFactory()));
            case DELETE_COMMENT:
                return new DeleteCommentCommand(new CommentService(new DaoHelperFactory()));
            case ALL_ARTISTS:
                return new AllArtistsCommand(new ArtistService(new DaoHelperFactory()));
            case ARTIST_MUSIC:
                return new ArtistMusicCommand(new TrackService(new DaoHelperFactory()));
            case COLLECTION_MUSIC:
                return new CollectionMusicCommand(new TrackService(new DaoHelperFactory()));
            // ORDER
            case CART:
                return new CartPageCommand(new OrderService(new DaoHelperFactory()), new TrackService(new DaoHelperFactory()));
            case ADD_TRACK:
                return new AddTrackToCartCommand(new OrderService(new DaoHelperFactory()));
            case DELETE_TRACK:
                return new DeleteTrackFromCartCommand(new OrderService(new DaoHelperFactory()));
            case PAY_ORDER:
                return new PayOrderCommand(new OrderService(new DaoHelperFactory()));
            case PAYMENT_HISTORY:
                return new PaidOrderTracksCommand(new OrderService(new DaoHelperFactory()));
            case PURCHASED_TRACKS:
                return new PaidMusicCommand(new TrackService(new DaoHelperFactory()));
            //ADMIN
            case ADMIN_MAIN_PAGE:
                return new ShowPageCommand(ADMIN_MAIN_PAGE);
            case ADMIN_TRACK_LIST:
                return new AdminTrackListCommand(new TrackService(new DaoHelperFactory()));
            case EDIT_TRACK:
                return new EditTrackFormCommand(new TrackService(new DaoHelperFactory()));
            case TRACK_FORM:
                return new TrackFormPageCommand(new ArtistService(new DaoHelperFactory()));
            case ADD_NEW_TRACK:
                return new AddTrackCommand(new TrackService(new DaoHelperFactory()));
            case ARTIST_FORM:
                return new ShowPageCommand(ARTIST_FORM_PAGE);
            case ADD_NEW_ARTIST:
                return new AddArtistCommand(new ArtistService(new DaoHelperFactory()));
            case ALBUM_FORM:
                return new AlbumFormPageCommand(new ArtistService(new DaoHelperFactory()));
            case ADD_NEW_ALBUM:
                return new AddAlbumCommand(new MusicCollectionService(new DaoHelperFactory()));
            case PLAYLIST_FORM:
                return new ShowPageCommand(PLAYLIST_FORM_PAGE);
            case ADD_NEW_PLAYLIST:
                return new AddPlaylistCommand(new MusicCollectionService(new DaoHelperFactory()));
            case USER_LIST:
                return new UserListCommand(new UserService(new DaoHelperFactory()));
            case CHANGE_USER_STATUS:
                return new ChangeUserStatusCommand(new UserService(new DaoHelperFactory()));

            default:
                throw new IllegalArgumentException("Unknown command type = " + type);
        }
    }

    private CommandType getCommandType(String type){
        String[] commandsParts = type.split(SPLIT_CAMEL_CASE_PATTERN);
        StringBuilder commandTypeFinal = new StringBuilder();
        String command;
        for (String part : commandsParts) {
            commandTypeFinal.append(part).append(LOW_LINE);
        }
        commandTypeFinal.deleteCharAt(commandTypeFinal.length() - LAST_INDEX);
        String temporaryString = new String(commandTypeFinal);
        command = temporaryString.toUpperCase();
        return CommandType.valueOf(command);

    }

}
