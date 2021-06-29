package com.epam.web.commands;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

public class ShowPageCommand implements Command {

    private String page;

    public ShowPageCommand(String page) {
        this.page = page;
    }

    @Override
    public CommandResult execute(HttpServletRequest request, HttpServletResponse response) {
        return CommandResult.forward(page);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (o == null || getClass() != o.getClass()) {return false;}
        ShowPageCommand that = (ShowPageCommand) o;
        return Objects.equals(page, that.page);
    }

    @Override
    public int hashCode() {
        return Objects.hash(page);
    }
}
