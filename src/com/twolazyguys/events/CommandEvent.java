package com.twolazyguys.events;

import net.colozz.engine2.events.Event;

public class CommandEvent extends Event {

    private String command;
    private String[] args;
    private boolean canceled = false;

    public CommandEvent(String command, String[] args) {
        this.command = command;
        this.args = args;
    }

    public String getCommand() {
        return command;
    }

    public String[] getArgs() {
        return args;
    }

    public void setArgs(String[] args) {
        this.args = args;
    }

    public boolean isCanceled() {
        return canceled;
    }

    public void setCanceled(boolean canceled) {
        this.canceled = canceled;
    }

}
