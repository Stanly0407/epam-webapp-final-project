package com.epam.web.commands.orderCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.entities.Bonus;
import com.epam.web.entities.Track;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;
import com.epam.web.service.OrderService;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class ActivateDiscountCommand implements Command {

    private static final String USER_CART_COMMAND = "/controller?command=cart";


    public ActivateDiscountCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        session.setAttribute("activatedDiscountBonus", true);
        return CommandResult.redirect(USER_CART_COMMAND);
    }
}
