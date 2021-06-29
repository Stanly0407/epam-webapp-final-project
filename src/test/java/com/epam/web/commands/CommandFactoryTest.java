package com.epam.web.commands;

import org.junit.Assert;
import org.junit.Test;

public class CommandFactoryTest {

    private final CommandFactory commandFactory = new CommandFactory();

    @Test
    public void createTestShouldReturnCommand() {
        String correctType = "playlistForm";
        Command expected = new ShowPageCommand("/WEB-INF/view/fragments/playlistForm.jsp");
        Command actual = commandFactory.create(correctType);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void createTestShouldReturnException() {
        String wrongType = "wrongType";
        Command actual = commandFactory.create(wrongType);
    }


}
