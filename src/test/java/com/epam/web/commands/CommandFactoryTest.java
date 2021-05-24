package com.epam.web.commands;

import org.junit.Assert;
import org.junit.Test;

public class CommandFactoryTest {

    private final CommandFactory commandFactory = new CommandFactory();

    @Test
    public void getCommandTypeTestShouldReturnCorrectCommandType(){
        String correctType = "deleteBonus";
        CommandType expected = CommandType.DELETE_BONUS;
        CommandType actual = commandFactory.getCommandType(correctType);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getCommandTypeTestShouldReturnIncorrectCommandType(){
        String incorrectType = "incorrect";
        CommandType actual = commandFactory.getCommandType(incorrectType);
    }


}
