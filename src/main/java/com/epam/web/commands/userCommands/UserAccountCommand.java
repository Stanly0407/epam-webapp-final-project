package com.epam.web.commands.userCommands;

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
        if (!bonuses.isEmpty()) {
            request.setAttribute(BONUS_MESSAGE, true);
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            request.setAttribute(USER, user);
        }
        return CommandResult.forward(USER_ACCOUNT_PAGE);
    }
}
