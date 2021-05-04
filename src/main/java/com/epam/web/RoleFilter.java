package com.epam.web;

import com.epam.web.commands.Command;
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
    private static final String ERROR_PAGE = "/error.jsp";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String PARAMETER_COMMAND = "command";
    private static final String LOGIN_COMMAND = "login";
    private static final String CHANGE_LANGUAGE_COMMAND = "changeLanguage";
    private static final String PARAMETER_ROLE = "role";
    private static final Map<CommandType, List<Role>> COMMANDS_PERMISSIONS = new HashMap<>();
    private FilterConfig filterConfig;

    public RoleFilter() {
        if (COMMANDS_PERMISSIONS.isEmpty()) {
            COMMANDS_PERMISSIONS.put(CommandType.LOGIN, Arrays.asList(Role.ADMIN, Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.LOGOUT, Arrays.asList(Role.ADMIN, Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.CHANGE_LANGUAGE, Arrays.asList(Role.ADMIN, Role.USER));

            COMMANDS_PERMISSIONS.put(CommandType.ADMIN_MAIN_PAGE, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADMIN_TRACK_LIST, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.EDIT_TRACK, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.TRACK_FORM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_NEW_TRACK, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ARTIST_FORM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_NEW_ARTIST, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ALBUM_FORM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_NEW_ALBUM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.PLAYLIST_FORM, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_NEW_PLAYLIST, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.USER_LIST, Arrays.asList(Role.ADMIN));
            COMMANDS_PERMISSIONS.put(CommandType.CHANGE_USER_STATUS, Arrays.asList(Role.ADMIN));

            COMMANDS_PERMISSIONS.put(CommandType.USER_MAIN_PAGE, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.USER_ACCOUNT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.REFILL_BALANCE_PAGE, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.REFILL_BALANCE, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.SEARCH_MUSIC, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.SEARCH_MUSIC_RESULT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.USER_MUSIC, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.COMMENTS_PAGE, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.CART, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_TRACK, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.DELETE_TRACK, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.PAY_ORDER, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.PAYMENT_HISTORY, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.PURCHASED_TRACKS, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.ALL_MUSIC, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.NEXT_PAGE, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.ADD_COMMENT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.EDIT_COMMENT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.DELETE_COMMENT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.SAVE_EDITED_COMMENT, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.ALL_ARTISTS, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.ARTIST_MUSIC, Arrays.asList(Role.USER));
            COMMANDS_PERMISSIONS.put(CommandType.COLLECTION_MUSIC, Arrays.asList(Role.USER));
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

            String command = request.getParameter(PARAMETER_COMMAND);
            LOGGER.debug("filter command " + command);

            String[] commandsParts = command.split("(?<=[a-z])(?=[A-Z])");
            LOGGER.debug("commandsParts " + Arrays.toString(commandsParts));

            StringBuilder commandTypeFinal = new StringBuilder();
            String commandTTT;
            for (String part : commandsParts) {
                commandTypeFinal.append(part).append("_");
            }
            commandTypeFinal.deleteCharAt(commandTypeFinal.length()-1);
            String temporary = new String(commandTypeFinal);
            commandTTT = temporary.toUpperCase();


            LOGGER.debug("commandTTT" + commandTTT);

            CommandType commandType = CommandType.valueOf(commandTTT);
            LOGGER.debug("filter commandType " + commandType);
            String currentRoleInSession = (String) session.getAttribute(PARAMETER_ROLE);
            LOGGER.debug("filter currentRoleInSession " + currentRoleInSession);
            Role currentRole;
            if (currentRoleInSession != null) {
                currentRole = Role.valueOf(currentRoleInSession);
                List<Role> permissions = COMMANDS_PERMISSIONS.get(commandType);
                if (permissions == null || !permissions.contains(currentRole)) {
                    LOGGER.debug(" current role does not have permission to access this page / role " + currentRole);
                    ServletContext context = filterConfig.getServletContext();
                    RequestDispatcher requestDispatcher = context.getRequestDispatcher(ERROR_PAGE);
                    request.setAttribute(ERROR_MESSAGE, "You do not have permission to access this page...");
                    requestDispatcher.forward(servletRequest, servletResponse);
                } else {
                    LOGGER.debug(" first login, current role = null ? // role = " + currentRole);
                    filterChain.doFilter(servletRequest, servletResponse);
                }
            } else if (LOGIN_COMMAND.equals(command) || CHANGE_LANGUAGE_COMMAND.equals(command) ) {
                filterChain.doFilter(servletRequest, servletResponse);
            } else {
                ServletContext context = filterConfig.getServletContext();
                RequestDispatcher requestDispatcher = context.getRequestDispatcher(ERROR_PAGE);
                requestDispatcher.forward(servletRequest, servletResponse);
            }
        }
    }

    @Override
    public void destroy() {
    }
}
