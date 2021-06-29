package com.epam.web.commands.bonus;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeactivateDiscountCommand implements Command {
    private static final String USER_CART_COMMAND = "/controller?command=cart";
    private static final String ACTIVATE_BONUS = "activatedDiscountBonus";

    public DeactivateDiscountCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.setAttribute(ACTIVATE_BONUS, false);
        return CommandResult.redirect(USER_CART_COMMAND);
    }
}
