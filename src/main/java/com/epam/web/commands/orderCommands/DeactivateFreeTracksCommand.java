package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DeactivateFreeTracksCommand implements Command {

    private static final String USER_CART_COMMAND = "/controller?command=cart";


    public DeactivateFreeTracksCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.setAttribute("activatedFreeTracksBonus", false);
        return CommandResult.redirect(USER_CART_COMMAND);
    }
}
