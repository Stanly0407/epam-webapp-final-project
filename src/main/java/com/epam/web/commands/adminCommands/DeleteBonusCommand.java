package com.epam.web.commands.adminCommands;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class DeleteBonusCommand implements Command {
    private static final String USER_LIST_COMMAND = "/controller?command=userList";
    private static final String BONUS_ID = "id";

    private final BonusService bonusService;

    public DeleteBonusCommand(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String bonusFreeTracksString = request.getParameter(BONUS_ID);
        Long bonusFreeTracks = Long.valueOf(bonusFreeTracksString);
        bonusService.deleteUserBonus(bonusFreeTracks);
        return CommandResult.redirect(USER_LIST_COMMAND);
    }
}
