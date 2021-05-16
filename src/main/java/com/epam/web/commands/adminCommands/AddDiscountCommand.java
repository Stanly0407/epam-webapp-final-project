package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.BonusType;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;
import com.epam.web.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDiscountCommand implements Command {
    private static final String USER_LIST_COMMAND = "/controller?command=userList";
    private static final String WRONG_BONUS_MESSAGE = "wrongDiscountAmountMessage";
    private static final String USER_ID = "userId";
    private static final String DISCOUNT_AMOUNT = "discountAmount";

    private final BonusService bonusService;

    public AddDiscountCommand(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String idString = request.getParameter(USER_ID);
        Long userId = Long.valueOf(idString);
        String discountAmountString = request.getParameter(DISCOUNT_AMOUNT);
        if(discountAmountString != null){
            int discountAmount = Integer.parseInt(discountAmountString);
            bonusService.addBonus(userId, discountAmount, BonusType.DISCOUNT);
        } else {
            request.setAttribute(WRONG_BONUS_MESSAGE, true);
        }
        return CommandResult.forward(USER_LIST_COMMAND);
    }
}
