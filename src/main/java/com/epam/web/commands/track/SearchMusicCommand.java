package com.epam.web.commands.track;

import com.epam.web.commands.Command;
import com.epam.web.commands.CommandResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SearchMusicCommand implements Command {

    private static final String SEARCH_MUSIC_COMMAND = "/controller?command=searchMusicResult";
    private static final String ATTRIBUTE_SEARCH_SUBJECT = "searchSubject";
    private static final String ATTRIBUTE_SEARCH_CONDITION = "searchCondition";

    public SearchMusicCommand() {
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        String searchSubject = request.getParameter(ATTRIBUTE_SEARCH_SUBJECT);
        String searchCondition = request.getParameter(ATTRIBUTE_SEARCH_CONDITION);
        HttpSession session = request.getSession();
        session.setAttribute(ATTRIBUTE_SEARCH_SUBJECT, searchSubject);
        session.setAttribute(ATTRIBUTE_SEARCH_CONDITION, searchCondition);
        return CommandResult.redirect(SEARCH_MUSIC_COMMAND);
    }
}
