package com.epam.web.commands.user;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.User;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

public class UserAccountCommand implements Command {
    private static final String USER_ACCOUNT_PAGE = "/WEB-INF/view/userPages/userAccountPage.jsp";
    private static final String USER = "user";
    private static final String ATTRIBUTE_USER_ID = "userId";
    private static final String BONUS_MESSAGE = "bonusMessage";
    private static final String ATTRIBUTE_NOT_ENOUGH_FUNDS = "notEnoughFundsMessage";

    private final UserService userService;
    private final BonusService bonusService;

    public UserAccountCommand(UserService userService, BonusService bonusService) {
        this.userService = userService;
        this.bonusService = bonusService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        Optional<User> optionalUser = userService.getUserInfo(userId);
        List<Bonus> bonuses = bonusService.getUnusedUserBonuses(userId);
        Boolean balanceInfo = (Boolean) session.getAttribute(ATTRIBUTE_NOT_ENOUGH_FUNDS);
        if (!bonuses.isEmpty()) {
            request.setAttribute(BONUS_MESSAGE, true);
        }
        if (balanceInfo != null && balanceInfo) {
            request.setAttribute(ATTRIBUTE_NOT_ENOUGH_FUNDS, true);
            session.removeAttribute(ATTRIBUTE_NOT_ENOUGH_FUNDS);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            request.setAttribute(USER, user);
        }
        return CommandResult.forward(USER_ACCOUNT_PAGE);
    }
}
