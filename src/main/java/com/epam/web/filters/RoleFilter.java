package com.epam.web.filters;

import com.epam.web.commands.CommandType;
import com.epam.web.entities.Role;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RoleFilter implements Filter {
    private static final Logger LOGGER = LogManager.getLogger(RoleFilter.class);
    private static final String ERROR_PAGE = "/WEB-INF/view/error.jsp";
    private static final String ERROR_NO_ROLE = "noRole";
    private static final String ERROR_NO_PAGE = "noPage";
    private static final String PARAMETER_COMMAND = "command";
    private static final String PARAMETER_ROLE = "role";
    private static final String SPLIT_CAMEL_CASE_PATTERN = "(?<=[a-z])(?=[A-Z])";
    private static final String LOW_LINE = "_";
    private static final int LAST_INDEX = 1;
    private static final String LOGIN_MAIN_PAGE = "/WEB-INF/view/login.jsp";

    private static final Map<CommandType, List<Role>> COMMANDS_PERMISSIONS = new HashMap<>();

    private FilterConfig filterConfig;

    public RoleFilter() {
        if (COMMANDS_PERMISSIONS.isEmpty()) {
            COMMANDS_PERMISSIONS.put(CommandType.LOGIN, Arrays.asList(Role.ADMIN, Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.LOGOUT, Arrays.asList(Role.ADMIN, Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.CHANGE_LANGUAGE, Arrays.asList(Role.ADMIN, Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.ALL_MUSIC, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.NEXT_PAGE, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ALL_ALBUMS, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ALL_PLAYLISTS, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ALL_ARTISTS, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.DELETE_TRACK, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.DELETE_TRACK_PREVENTING, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.DELETE_USER_COMMENT, Arrays.asList(Role.ADMIN));

            COMMANDS_PERMISSIONS.put(CommandType.ADMIN_MAIN_PAGE, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADMIN_TRACK_LIST, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.EDIT_TRACK, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.TRACK_FORM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_EDIT_TRACK, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ARTIST_FORM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_NEW_ARTIST, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ALBUM_FORM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_NEW_ALBUM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.PLAYLIST_FORM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_NEW_PLAYLIST, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.USER_LIST, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.CHANGE_USER_STATUS, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_DISCOUNT, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_FREE_TRACKS, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.DELETE_BONUS, Arrays.asList(Role.ADMIN));

            COMMANDS_PERMISSIONS.put(CommandType.USER_MAIN_PAGE, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.USER_ACCOUNT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.REFILL_BALANCE_PAGE, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.REFILL_BALANCE, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.SEARCH_MUSIC, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.SEARCH_MUSIC_RESULT, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.USER_MUSIC, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.COMMENTS_PAGE, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.CART, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_TRACK, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.DELETE_TRACK_FROM_CART, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.PAY_ORDER, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.PAYMENT_HISTORY, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.PURCHASED_TRACKS, Arrays.asList(Role.USER));

            COMMANDS_PERMISSIONS.put(CommandType.ADD_COMMENT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.EDIT_COMMENT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.DELETE_COMMENT, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.SAVE_EDITED_COMMENT, Arrays.asList(Role.USER));

            COMMANDS_PERMISSIONS.put(CommandType.ARTIST_MUSIC, Arrays.asList(Role.USER, Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.COLLECTION_MUSIC, Arrays.asList(Role.USER, Role.ADMIN));

            COMMANDS_PERMISSIONS.put(CommandType.ACTIVATE_DISCOUNT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.DEACTIVATE_DISCOUNT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.ACTIVATE_FREE_TRACKS, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.DEACTIVATE_FREE_TRACKS, Arrays.asList(Role.USER));
        }
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
        filterConfig = config;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {


        if (servletRequest instanceof HttpServletRequest && servletResponse instanceof HttpServletResponse) {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            HttpSession session = request.getSession();
            String commandRequest = request.getParameter(PARAMETER_COMMAND);
            String command = clearCommand(commandRequest);
            try {
                CommandType commandType = CommandType.valueOf(command);
                LOGGER.debug("commandType " + commandType);
                String currentRoleInSession = (String) session.getAttribute(PARAMETER_ROLE);
                LOGGER.debug("currentRole " + currentRoleInSession);
                Role currentRole;

                if (currentRoleInSession == null) {
                    if ("LOGIN".equals(command) || "CHANGE_LANGUAGE".equals(command) || "LOGIN_PAGE".equals(command)) {
                        filterChain.doFilter(servletRequest, servletResponse);
                    } else {
                        errorForward(ERROR_NO_ROLE, LOGIN_MAIN_PAGE, servletRequest, servletResponse);
                    }
                } else {
                    currentRole = Role.valueOf(currentRoleInSession);
                    List<Role> permissions = COMMANDS_PERMISSIONS.get(commandType);
                    if (permissions == null || !permissions.contains(currentRole)) {
                        errorForward(ERROR_NO_PAGE, ERROR_PAGE, servletRequest, servletResponse);
                    } else {
                        if ("CHANGE_LANGUAGE".equals(command)) {
                            filterChain.doFilter(servletRequest, servletResponse);
                            return;
                        }
                        filterChain.doFilter(servletRequest, servletResponse);
                    }
                }
            } catch (IllegalArgumentException e) {
                errorForward(ERROR_NO_PAGE, ERROR_PAGE, servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {
    }

    private String clearCommand(String type) {
        String[] commandsParts = type.split(SPLIT_CAMEL_CASE_PATTERN);
        StringBuilder commandTypeFinal = new StringBuilder();
        for (String part : commandsParts) {
            commandTypeFinal.append(part).append(LOW_LINE);
        }
        commandTypeFinal.deleteCharAt(commandTypeFinal.length() - LAST_INDEX);
        String temporaryString = new String(commandTypeFinal);
        return temporaryString.toUpperCase();
    }

    private void errorForward(String parameter, String page, ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        ServletContext context = filterConfig.getServletContext();
        RequestDispatcher requestDispatcher = context.getRequestDispatcher(page);
        servletRequest.setAttribute(parameter, true);
        requestDispatcher.forward(servletRequest, servletResponse);
    }
}
