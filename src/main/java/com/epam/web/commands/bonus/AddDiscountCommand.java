package com.epam.web.commands.bonus;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.BonusType;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddDiscountCommand implements Command {
    private static final Logger LOGGER = LogManager.getLogger(AddDiscountCommand.class);
    private static final String USER_LIST_COMMAND = "/controller?command=userList";
    private static final String WRONG_BONUS_MESSAGE = "wrongBonusAmountMessage";
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
        LOGGER.debug("discountAmountString " + discountAmountString);
        if (bonusService.validateBonusDetails(discountAmountString)) {
            int discountAmount = Integer.parseInt(discountAmountString);
            bonusService.addBonus(userId, discountAmount, BonusType.DISCOUNT);
        } else {
            LOGGER.debug("WRONG_BONUS_MESSAGE");
            request.setAttribute(WRONG_BONUS_MESSAGE, true);
        }
        return CommandResult.forward(USER_LIST_COMMAND);
    }
}
