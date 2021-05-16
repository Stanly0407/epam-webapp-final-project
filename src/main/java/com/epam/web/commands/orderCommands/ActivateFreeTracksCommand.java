package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ActivateFreeTracksCommand implements Command {

    private static final String USER_CART_COMMAND = "/controller?command=cart";

    public ActivateFreeTracksCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.setAttribute("activatedFreeTracksBonus", true);
        return CommandResult.redirect(USER_CART_COMMAND);
    }
}
