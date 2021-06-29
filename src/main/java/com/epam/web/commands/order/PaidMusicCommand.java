package com.epam.web.commands.order;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;
import com.epam.web.dto.TrackDto;
import com.epam.web.exceptions.ServiceException;
import com.epam.web.service.TrackService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PaidMusicCommand implements Command {

    private static final String USER_MUSIC_PAGE = "/WEB-INF/view/userPages/musicList.jsp";
    private static final String ATTRIBUTE_TRACK_LIST = "purchasedTracks";
    private static final String ATTRIBUTE_TRACK = "track";
    private static final String ATTRIBUTE_PAID_ORDER_ID = "id";
    private static final String ATTRIBUTE_USER_ID = "userId";

    private final TrackService trackService;

    public PaidMusicCommand(TrackService trackService) {
        this.trackService = trackService;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) throws ServiceException {
        HttpSession session = request.getSession();
        Long userId = (Long) session.getAttribute(ATTRIBUTE_USER_ID);
        String orderIdString = request.getParameter(ATTRIBUTE_PAID_ORDER_ID);
        Long orderId = Long.valueOf(orderIdString);
        List<TrackDto> purchasedTracks = trackService.getTracksFromPaidOrder(orderId, userId);
        request.setAttribute(ATTRIBUTE_TRACK_LIST, purchasedTracks);
        request.setAttribute(ATTRIBUTE_TRACK, new TrackDto());
        return CommandResult.forward(USER_MUSIC_PAGE);
    }
}
