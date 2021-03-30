package com.epam.web.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowPageCommand implements Command {

    private String page;

    public ShowPageCommand(String page) {
        this.page = page;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response)
    //throws ServiceException
    {
        return CommandResult.forward(page);

    }


}
