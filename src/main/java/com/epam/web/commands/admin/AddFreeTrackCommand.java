package com.epam.web.commands.admin;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.entities.BonusType;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.BonusService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddFreeTrackCommand implements Command {

    private static final String USER_LIST_COMMAND = "/controller?command=userList";
    private static final String WRONG_BONUS_MESSAGE = "wrongFreeTrackMessage";
    private static final String USER_ID = "userId";

    private static final String FREE_TRACKS_AMOUNT = "freeTracksAmount";

    private final BonusService bonusService;

    public AddFreeTrackCommand(BonusService bonusService) {
        this.bonusService = bonusService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        String idString = request.getParameter(USER_ID);
        Long userId = Long.valueOf(idString);
        String freeTracksAmountString = request.getParameter(FREE_TRACKS_AMOUNT);
        if (freeTracksAmountString != null) {
            int freeTracksAmount = Integer.parseInt(freeTracksAmountString);
            bonusService.addBonus(userId, freeTracksAmount, BonusType.FREE_TRACK);
        } else {
            request.setAttribute(WRONG_BONUS_MESSAGE, true);
        }
        return CommandResult.forward(USER_LIST_COMMAND);
    }
}
