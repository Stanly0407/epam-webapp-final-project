package com.epam.web.commands.userCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RefillBalanceCommand implements Command {

    private static final String USER_ACCOUNT_PAGE_COMMAND = "/controller?command=userAccount";

    private final UserService userService;

    public RefillBalanceCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String paymentAmount = request.getParameter("paymentAmount"); //todo pattern in jsp
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute("userId");
        userService.refillUserBalance(paymentAmount, userId);
        return CommandResult.redirect(USER_ACCOUNT_PAGE_COMMAND);
    }

}
