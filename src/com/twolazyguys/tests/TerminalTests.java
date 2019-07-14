package com.twolazyguys.tests;

import com.twolazyguys.Main;
import com.twolazyguys.events.CommandEvent;
import net.colozz.engine2.gamestates.DefaultGameState;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TerminalTests {

    @BeforeClass
    public static void oneTimeSetUp() throws InterruptedException {
        new Thread(() -> new Main()).start();
        while (Main.getGameState() instanceof DefaultGameState) Thread.sleep(10);
    }

    @AfterClass
    public static void oneTimeTearDown() {
        Main.exit();
    }

    @Test
    public void testBasicCommands() {
        CommandEvent commandEvent = new CommandEvent("help");
        Main.callEvent(commandEvent);
        assertTrue(commandEvent.getOutput().length != 0);

        commandEvent = new CommandEvent("cd");
        Main.callEvent(commandEvent);
        assertTrue(commandEvent.getOutput().length == 0);

        commandEvent = new CommandEvent("cd", new String[]{"test"});
        Main.callEvent(commandEvent);
        assertTrue(commandEvent.getOutput().length != 0);

        commandEvent = new CommandEvent("");
        Main.callEvent(commandEvent);
        assertTrue(commandEvent.getOutput().length == 0);

        commandEvent = new CommandEvent("PZAO KSsclp58");
        Main.callEvent(commandEvent);
        assertTrue(commandEvent.getOutput().length != 0);
    }


}
